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
	static Form <Contact> contactForm= new Form<Contact>(Contact.class);
	
	// main page
	// login page
	public static Result homePage() {
		String email = session().get("email");
		if(session().get("email") == null)
			Logger.info("Homepage has been opened by guest");
		else
			Logger.info("Homepage has been opened by user with email: " + session().get("email"));
		
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
				Logger.info("User with email: "+ email + " has logged in.");
				
				if(User.find(email).hasAdditionalInfo)
					return redirect("/homepage");

				return redirect("/additionalinfo");
			} else {
				return redirect("/login");
			}

		}

		flash("error", "Email does not exist!");
		Logger.error("User has entered wrong email");
		return ok(toregister.render(loginUser, email, FAQ.all()));
	}

	// tries to register user
	// if there is already user with the same username he gets redirected to
	// login page
	// if the user gets registered, he gets a verification email on his email address
	@SuppressWarnings("static-access")
	public static Result register() throws MalformedURLException {
		
		DynamicForm form = loginUser.form().bindFromRequest();
			//User u = loginUser.bindFromRequest().get();
			
			String email = form.get("email");
			String password = form.get("password");
			String passconfirm = form.get("confirm_pass");
			if(!password.equals(passconfirm)) {
				Logger.error("User has entered unmatching passwords");
				flash("error","Passwords are not the same!");
				return ok(toregister.render(loginUser, email, FAQ.all()));
			}
			String confirmation = UUID.randomUUID().toString();
			User u = new User(email, password, confirmation);
			if (User.create(email, password, confirmation)) {
			String urlS = "http://localhost:9000" + "/" + "confirm/" + confirmation;
			URL url = new URL(urlS);
			MailHelper.send(email, url.toString()); 
			Logger.info("User with email: " + email + " has registered");
			flash("validate", "Please check your email");
			
			return redirect("/login");
		}else {
			Logger.error("User has entered invalid email");
			flash("error", "There is already a user with that email!");
			return ok(toregister.render(loginUser, email, FAQ.all()));
		}

	}
	


	// goes to page where the user can be registered
	public static Result toRegister() {
		String email = session().get("email");
		Logger.info("Page for registration has been opened");

		return ok(toregister.render(loginUser, email, FAQ.all()));
	}
	
	

	/**
	 * We return whatever the promise returns, so the return value is changed from Result to Promise<Result>
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
							if(session().get("email") == null)
								Logger.info("Guest has sent message to admin");
							else
								Logger.info("User with email: " + session().get("email") + " has sent message to admin");
							return redirect("/contactpage");
						} else {

							if(session().get("email") == null)
								Logger.info("Guest did not confirm its humanity");
							else
								Logger.info("User with email: " + session().get("email") + " did not confirm its humanity");
							flash("error", "You have to confirm that you are not a robot!");

							return ok(contact.render(userEmail, FAQ.all() ));

						}
					}
				});
		//return the promisse
		return holder;
	}


	public static Result toLogin() {
		String email = session().get("email");
		Logger.info("Opened page for login");
		
		return ok(logintest.render(email, FAQ.all()));
	}
	
	
	public static Result logOut(){
		Logger.warn("User with email: " + session().get("email") + " has logged out");
		session().clear();
		return redirect("/");
		}
	public static Result contactPage(){
		String email = session().get("email");
		if(session().get("email") == null)
			Logger.info("Guest has opened contact us page");
		else
			Logger.info("User with email: " + session().get("email") + " has opened contact us page");
		return ok(contact.render(email, FAQ.all()));
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
