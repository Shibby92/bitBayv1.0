package controllers;

import helpers.*;

import java.net.*;
import java.util.*;

import models.*;
import play.*;
import play.data.*;
import play.data.validation.Constraints.*;
import play.mvc.*;

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

		List<Product> allProducts = Product.productList();
		List<Product> startList = new ArrayList<Product>();
		Logger.info("Broj produkata prije ajax poziva: "+allProducts.size());

		/*while(allProducts.size()%6!=0){
		 for (Iterator<Product> productsIterator = allProducts.iterator(); productsIterator.hasNext();) {
		      Product p = productsIterator.next();
		      if (p.sold=true) {
		        productsIterator.remove();
		      }
		      }
		}*/
		//Logger.info("Broj produkata nakon ajax poziva: "+allProducts.size());

		for (int i = 0; i < 6; i++) {
			if(i<allProducts.size())
			startList.add(allProducts.get(i));
		}

		return ok(homePage.render(email, Category.list(), startList, FAQ.all()));

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
				flash("error", play.i18n.Messages.get("ULAFlash1"));
				return redirect("/login");
			}

		}

		flash("error", play.i18n.Messages.get("ULAFlash2"));
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
			flash("error", play.i18n.Messages.get("ULAFlash3"));
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
			flash("validate", play.i18n.Messages.get("ULAFlash4"));
			return redirect("/login");
		} else {
			Logger.error("User has entered existing email: " + email);
			flash("error", play.i18n.Messages.get("ULAFlash5"));
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
				play.i18n.Messages.get("ULAFlash6"));
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
