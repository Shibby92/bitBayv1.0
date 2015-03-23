package controllers;

import helpers.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.databind.JsonNode;

import models.*;
import play.Logger;
import play.Play;
import play.data.*;
import play.data.validation.Constraints.Email;
import play.data.validation.Constraints.Required;
import play.libs.F.Function;
import play.libs.F.Promise;
import play.libs.ws.WS;
import play.libs.ws.WSResponse;
import play.mvc.*;
import views.html.*;


/**
 * Controls the login application Redirects on the pages when needed When the
 * user registers, he gets redirected to page with ads If the user is already
 * registered, then he gets redirected to LOG IN page
 * 
 * @author eminamuratovic
 *
 */
public class UserLoginApplication extends Controller {
	static Form<User> loginUser = new Form<User>(User.class);
	
	// main page
	// login page
	public static Result homePage() {
		String email = session().get("email");
		
		return ok(homePage.render(email,Category.list(),Product.productList(), FAQ.all()));
	}

	// tries to log user to page
	// if the user can log, he gets redirected to index page
	// if the user is not in database, he gets redirected to register page
	public static Result login() {
		DynamicForm form = Form.form().bindFromRequest();

		String email = form.get("email");
		String password = form.get("password");

		if (User.existsEmail(email)) {
			if (User.checkLogin(email, password)) {
				session("email", email);

				if(User.find(email).hasAdditionalInfo)
					return redirect("/homepage");

				return redirect("/additionalinfo");
			} else {
				return redirect("/login");
			}

		}

		flash("error", "Email does not exist!");
		return ok(toregister.render(loginUser));
	}

	// tries to register user
	// if there is already user with the same username he gets redirected to
	// login page
	// if the user gets registered, he gets a verification email on his email address
	@SuppressWarnings("static-access")
	public static Result register() throws MalformedURLException {
		Logger.info("create user");
		DynamicForm form = loginUser.form().bindFromRequest();
			//User u = loginUser.bindFromRequest().get();
			Logger.info("user created");
			String email = form.get("email");
			String password = form.get("password");
			String passconfirm = form.get("confirm_pass");
			if(!password.equals(passconfirm)) {
				flash("error","Passwords are not the same!");
				return ok(toregister.render(loginUser));
			}
			String confirmation = UUID.randomUUID().toString();
			User u = new User(email, password, confirmation);
			if (User.create(email, password, confirmation)) {
			String urlS = "http://localhost:9000" + "/" + "confirm/" + confirmation;
			URL url = new URL(urlS);
			MailHelper.send(email, url.toString()); 
			if (u.verification == true) {
				return redirect("/homepage");
			}
			flash("validate", "Please check your email");
			return redirect("/login");
		}else {
			flash("error", "There is already a user with that username!");
			return ok(toregister.render(loginUser));
		}

	}
	


	// goes to page where the user can be registered
	public static Result toRegister() {
		Logger.info("toregister page rendered");

		return ok(toregister.render(loginUser));
	}
	
	static Form <Contact> contactForm= new Form<Contact>(Contact.class);
	
//	public static Result contact(){
//		DynamicForm form=contactForm.form().bindFromRequest();
//		String email= form.get("email");
//		String message= form.get("message");
//		List<User> admins=User.admins();
//		for(User admin : admins){
//			ContactHelper.send(email, admin.email, message);
//		}
//		return TODO;		
//	}
	/**
	 * We return whatever the promise returns, so the return value is changed from Result to Promise<Result>
	 * 
	 * @return the contact page with a message indicating if the email has been sent.
	 */
	public static Promise<Result> contact() {
		 String userEmail = session().get("email");
		//need this to get the google recapctha value
		 DynamicForm temp = DynamicForm.form().bindFromRequest();
		
		/* send a request to google recaptcha api with the value of our secret code and the value
		 * of the recaptcha submitted by the form */
		Promise<Result> holder = WS
				.url("https://www.google.com/recaptcha/api/siteverify")
				.setContentType("application/x-www-form-urlencoded")
				.post(String.format("secret=%s&response=%s",
						//get the API key from the config file
						Play.application().configuration().getString("recaptchaKey"),
						temp.get("g-recaptcha-response")))
				.map(new Function<WSResponse, Result>() {
					//once we get the response this method is loaded
					public Result apply(WSResponse response) {
						//get the response as JSON
						JsonNode json = response.asJson();
						System.out.println(json);
						System.out.println(temp.get("g-recaptcha-response"));
						Form<Contact> submit = Form.form(Contact.class)
								.bindFromRequest();
						
						//check if value of success is true
						if (json.findValue("success").asBoolean() == true
								&& !submit.hasErrors()) {
							String email= temp.get("email");
							String message= temp.get("message");
							List<User> admins=User.admins();
							for(User admin : admins){
								ContactHelper.send(email, admin.email, message);
							}
							flash("success", "Message sent!");
							return redirect("/contactpage");
						} else {
							flash("error", "There has been a problem!");
							return ok(contact.render(userEmail));

						}
					}
				});
		//return the promisse
		return holder;
	}

	// home page of the user
	// he can see ads that someone else had added
	// he has an option to add his own ad

	public static Result toLogin() {
		Logger.info("logintest rendered");
		
		return ok(logintest.render());
	}
	public static Result logOut(){
		Logger.warn("user logged out");
		session().clear();
		return redirect("/");
		}
	public static Result contactPage(){
		String email = session().get("email");
		return ok(contact.render(email));
	}
	
	
	//avoiding model creation for contact form
	public static class Contact{
		@Required
		@Email
		public String email;
		@Required
		public String message;

		public Contact(){
		}

		public Contact(String email, String message){
			this.email=email;
			this.message=message;
		}

	}
}
