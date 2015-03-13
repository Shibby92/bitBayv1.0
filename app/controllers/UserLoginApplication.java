package controllers;

import helpers.MailHelper;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import models.*;
import play.data.*;
import play.db.ebean.Model.Finder;
import play.mvc.*;
import views.html.*;
import play.i18n.Messages;

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
		
		return ok(homePage.render(email,Category.list(),Product.productList()));
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
				return redirect("/homepage");
			} else {
				

				return redirect("/login");
			}

		}

		return ok(toregister.render(loginUser));
	}

	// tries to register user
	// if there is already user with the same username he gets redirected to
	// login page
	// if the user gets registered, he gets a verification email on his email address
	@SuppressWarnings("static-access")
	public static Result register() throws MalformedURLException {
		DynamicForm form = loginUser.form().bindFromRequest();
		if (form.get("email").equals(""))
			return ok(toregister.render(loginUser));
		else {
			//User u = loginUser.bindFromRequest().get();
			String email = form.get("email");
			String password = form.get("password");
			String confirmation = UUID.randomUUID().toString();
			User u = new User(email, password, confirmation);
			if (User.create(email, password, confirmation)) {
			String urlS = "http://localhost:9000" + "/" + "confirm/" + confirmation;
			URL url = new URL(urlS);
			MailHelper.send(email, url.toString()); 
			if (u.verification == true) {
				return redirect("/homepage");
			}
			flash("validate", Messages.get("Please check your email"));
			return redirect("/login");
		}else
				return ok(toregister.render(loginUser));
		}

	}

	// goes to page where the user can be registered
	public static Result toRegister() {

		return ok(toregister.render(loginUser));
	}

	// home page of the user
	// he can see ads that someone else had added
	// he has an option to add his own ad

	public static Result toLogin() {
		return ok(logintest.render());
	}
	public static Result logOut(){
		session().clear();
		return redirect("/");
		}
}
