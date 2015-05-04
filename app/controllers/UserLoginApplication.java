package controllers;

import helpers.*;

import java.net.*;
import java.text.*;
import java.util.*;

import models.*;
import models.Notification;
import play.*;
import play.data.*;
import play.data.validation.Constraints.*;
import play.libs.F.*;
import play.libs.ws.*;
import play.mvc.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.paypal.api.payments.*;
import com.paypal.base.rest.*;

import views.html.*;

// TODO: Auto-generated Javadoc
/**
 * The Class UserLoginApplication
 */
public class UserLoginApplication extends Controller {
	
	/** The login user. */
	static Form<User> loginUser = new Form<User>(User.class);
	
	/** The contact form. */
	static Form<Contact> contactForm = new Form<Contact>(Contact.class);

	/**
	 * Opens main homepage.
	 *
	 * @return the result
	 */
	public static Result homePage() {
		String email = session().get("email");

		if (session().get("email") == null)
			Logger.info("Homepage has been opened by guest");
		else
			Logger.info("Homepage has been opened by user with email: "
					+ session().get("email"));

		return ok(homePage.render(email, Category.list(),
				Product.productList(), FAQ.all()));

	}

	/**
	 * Tries to log user to page.If the user can log he gets redirected to homepage.
	 * If the user is not in database, he gets redirected to register page.
	 *
	 * @return the result
	 */
	public static Result login() {
		DynamicForm form = Form.form().bindFromRequest();

		String email = form.get("email");
		String password = form.get("password");

		if (User.existsEmail(email)) {
			if (User.checkLogin(email, password)) {

				session("email", email);

				Logger.info("User with email: " + email + " has logged in.");

				if (User.find(email).hasAdditionalInfo)

					return redirect("/homepage");

				return redirect("/additionalinfo");
			} else {
				Logger.warn("User with email: " + email
						+ " has entered wrong password");
				flash("error", "You have entered wrong password!");
				return redirect("/login");
			}

		}

		flash("error", "Email does not exist!");
		Logger.error("User has entered wrong email");
		return redirect("/toregister");
	}

	/**
	 * Tries to register user. 
	 * If there is already user with the same username he gets redirected to login page.
	 * If the user gets registered, he gets a verification email on his email address.
	 *
	 * @throws MalformedURLException the malformed url exception
	 * @return the result
	 */
	@SuppressWarnings("static-access")
	public static Result register() throws MalformedURLException {

		DynamicForm form = loginUser.form().bindFromRequest();
		String email = form.get("email");
		String password = form.get("password");
		String passconfirm = form.get("confirm_pass");
		if (!password.equals(passconfirm)) {
			Logger.error("User has entered unmatching passwords");
			flash("error", "Passwords are not the same!");
			return ok(toregister.render(loginUser, email, FAQ.all()));
		}
		String confirmation = UUID.randomUUID().toString();
		User u = new User(email, password, confirmation);
		if (User.create(email, password, confirmation)) {
			String urlS = "http://localhost:9000" + "/" + "confirm/"
					+ confirmation;
			URL url = new URL(urlS);
			MailHelper.send(email, url.toString());
			Logger.info("User with email: " + email + " has registered");
			flash("validate", "Please check your email");
			return redirect("/login");
		} else {
			Logger.error("User has entered existing email: " + email);
			flash("error", "There is already a user with that email!");
			return ok(toregister.render(loginUser, email, FAQ.all()));
		}

	}

	/**
	 * Opens page for registration.
	 *
	 * @return the result
	 */
	public static Result toRegister() {

		String email = session().get("email");
		Logger.info("Page for registration has been opened");

		return ok(toregister.render(loginUser, email, FAQ.all()));
	}


	
	/**
	 * Opens page where user can login.
	 *
	 * @return the result
	 */
	public static Result toLogin() {

		String email = session().get("email");
		Logger.info("Opened page for login");
		return ok(logintest.render(email, FAQ.all()));
	}

	/**
	 * Logs out user from the website.
	 *
	 * @return the result
	 */
	public static Result logOut() {
		Logger.warn("User with email: " + session().get("email")
				+ " has logged out");

		session().clear();
		flash("success",
				"You have been successfully logged out! Come back any time!");
		return redirect("/");
	}

	/**
	 * The Class Contact.
	 */
	public static class Contact {
		
		/** The email. */
		@Required
		@Email
		public String email;
		
		/** The message. */
		@Required
		public String message;

		/**
		 * Instantiates a new contact.
		 */
		public Contact() {
			this.message = "";
		}

		/**
		 * Instantiates a new contact.
		 *
		 * @param email String the email
		 * @param message String the message
		 */
		public Contact(String email, String message) {
			this.email = email;
			this.message = message;
		}

	}
}
