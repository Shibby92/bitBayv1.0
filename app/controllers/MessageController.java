package controllers;


import helpers.*;

import java.util.*;

import models.*;
import play.*;
import play.data.*;
import play.libs.F.*;
import play.libs.ws.*;
import play.mvc.*;

import com.fasterxml.jackson.databind.JsonNode;

import controllers.UserLoginApplication.Contact;
import views.html.*;

public class MessageController extends Controller {
	
	/**
	 * Deletes message from his mail.
	 *
	 * @param id int id of the message
	 * @return the result
	 */
	@Security.Authenticated(UserFilter.class)
	public static Result deleteMessage(int id) {
		Message.delete(id);
		Logger.warn("Message with id: " + id + " has been deleted");
		flash("success", "Message deleted!");
		return redirect("/profile");

	}
	
	
	/**
	 * Opens page where user replies to another user.
	 *
	 * @param id int id of the sender
	 * @return the result
	 */
	@Security.Authenticated(UserFilter.class)
	public static Result replyToMessagePage(int id) {
		String email = session().get("email");
		Logger.info("User with email: " + session().get("email")
				+ " has opened reply contact page");

		return ok(reply.render(email, FAQ.all(), User.find(id)));
	}
	
	/**
	 * Opens message from another user.
	 *
	 * @param id int id of the message
	 * @return the result
	 */
	@Security.Authenticated(UserFilter.class)
	public static Result openMessage(int id) {
		String email = session().get("email");
		Logger.info("User with email: " + session().get("email")
				+ " has opened message from: " + Message.find(id).sender.email);
		return ok(message.render(email, FAQ.all(), Message.find(id)));
	}
	
	/**
	 * Gets the data from the reply page.
	 *
	 * @param id int id of the sender
	 * @return the result
	 */
	@Security.Authenticated(UserFilter.class)
	public static Promise<Result> reply(final int id) {
		final String userEmail = session().get("email");
		// need this to get the google recapctha value
		final DynamicForm temp = DynamicForm.form().bindFromRequest();

		/*
		 * send a request to google recaptcha api with the value of our secret
		 * code and the value of the recaptcha submitted by the form
		 */
		Promise<Result> holder = play.libs.ws.WS
				.url("https://www.google.com/recaptcha/api/siteverify")
				.setContentType("application/x-www-form-urlencoded")
				.post(String.format(
						"secret=%s&response=%s",
						// get the API key from the config file
						Play.application().configuration()
								.getString("recaptchaKey"),
						temp.get("g-recaptcha-response")))
				.map(new Function<WSResponse, Result>() {
					// once we get the response this method is loaded
					public Result apply(WSResponse response) {
						// get the response as JSON
						JsonNode json = response.asJson();
						System.out.println(json);
						System.out.println(temp.get("g-recaptcha-response"));
						Form<Contact> submit = Form.form(Contact.class)
								.bindFromRequest();

						// check if value of success is true
						if (json.findValue("success").asBoolean() == true
								&& !submit.hasErrors()) {

							final String message = temp.get("message");

							ContactHelper.send(userEmail, User.find(id).email,
									message);
							ContactHelper.sendToPage(userEmail,
									User.find(id).email, message, "Contact US message");

							flash("success", "Message sent!");

							Logger.info("User with email: "
									+ session().get("email") + " replied to : "
									+ Product.find(id).owner.email);
							return redirect("/profile/" + User.find(session().get("email")).id);
						} else {

							Logger.info("User with email: "
									+ session().get("email")
									+ " did not confirm its humanity");
							flash("error",
									"You have to confirm that you are not a robot!");
							return ok(reply.render(userEmail, FAQ.all(),
									User.find(id)));

						}
					}
				});
		// return the promisse
		return holder;
	}

	
	/**
	 * User can contact seller.
	 * He sends mail to sellers account and to sellers mail on the page.
	 *
	 * @param id int id of the product
	 * @return the result
	 */
	@Security.Authenticated(UserFilter.class)
	public static Result contactSellerPage(int id) {
		String email = session().get("email");
		Logger.info("User with email: " + session().get("email")
				+ " has opened contact us page");

		return ok(contactseller.render(email, FAQ.all(), Product.find(id), ""));

	}
	
	/**
	 * Gets the data from the contact seller page.
	 *
	 * @param id int id of the product
	 * @return the result
	 */
	@Security.Authenticated(UserFilter.class)
	public static Promise<Result> contactSeller(final int id) {
		final String userEmail = session().get("email");

		// need this to get the google recapctha value
		final DynamicForm temp = DynamicForm.form().bindFromRequest();
		final String message = temp.get("message");

		/*
		 * send a request to google recaptcha api with the value of our secret
		 * code and the value of the recaptcha submitted by the form
		 */
		Promise<Result> holder = WS
				.url("https://www.google.com/recaptcha/api/siteverify")
				.setContentType("application/x-www-form-urlencoded")
				.post(String.format(
						"secret=%s&response=%s",
						// get the API key from the config file
						Play.application().configuration()
								.getString("recaptchaKey"),
						temp.get("g-recaptcha-response")))
				.map(new Function<WSResponse, Result>() {
					// once we get the response this method is loaded
					public Result apply(WSResponse response) {
						// get the response as JSON
						JsonNode json = response.asJson();
						System.out.println(json);
						System.out.println(temp.get("g-recaptcha-response"));
						Form<Contact> submit = Form.form(Contact.class)
								.bindFromRequest();

						// check if value of success is true
						if (json.findValue("success").asBoolean() == true
								&& !submit.hasErrors()) {

							final String email = temp.get("email");

							ContactHelper.send(email,
									Product.find(id).owner.email, message);
							ContactHelper.sendToPage(email,
									Product.find(id).owner.email, message,
									"Message from buyer");

							flash("success", "Message sent!");

							Logger.info("User with email: "
									+ session().get("email")
									+ " has sent message to seller: "
									+ Product.find(id).owner.email);
							return redirect("/contactsellerpage/" + id);
						} else {

							Logger.info("User with email: "
									+ session().get("email")
									+ " did not confirm its humanity");
							flash("error",
									"You have to confirm that you are not a robot!");
							return ok(contactseller.render(userEmail,
									FAQ.all(), Product.find(id), message));

						}
					}
				});
		// return the promisse
		return holder;
	}
	

	/**
	 * Opens report.
	 *
	 * @param id the id
	 * @return the result
	 */
	@Security.Authenticated(AdminFilter.class)
	public static Result openReport(int id) {
		Logger.info("User " + session().get("email") + " has opened report");
		List<Report> all = Report.findByProduct(Product.find(id));
		return ok(report.render(session("email"), all));
	}
	
	/**
	 * Deletes report.
	 *
	 * @param id int the id of the report
	 * @return the result
	 */
	@Security.Authenticated(UserFilter.class)
	public static Result deleteReport(int id) {

		Logger.warn("Report with product id: " + Report.find(id).reportedProduct.id + " has been deleted");
		Report.delete(id);
		flash("success", "Report deleted!");
		return redirect("/profile");

	}
	
	/**
	 * Opens page for contact us.
	 *
	 * @return the result
	 */
	public static Result contactPage() {
		String email = session().get("email");
		if (session().get("email") == null)
			Logger.info("Guest has opened contact us page");
		else
			Logger.info("User with email: " + session().get("email")
					+ " has opened contact us page");
		return ok(contact.render(email, FAQ.all(), ""));

	}
	
	/**
	 * Gets data from contact us page
	 *
	 * @return the contact page with a message indicating if the email has been
	 *         sent.
	 */
	public static Promise<Result> contact() {
		final String userEmail = session().get("email");
		// need this to get the google recapctha value
		final DynamicForm temp = DynamicForm.form().bindFromRequest();
		final String message = temp.get("message");

		/*
		 * send a request to google recaptcha api with the value of our secret
		 * code and the value of the recaptcha submitted by the form
		 */
		Promise<Result> holder = WS
				.url("https://www.google.com/recaptcha/api/siteverify")
				.setContentType("application/x-www-form-urlencoded")
				.post(String.format(
						"secret=%s&response=%s",
						// get the API key from the config file
						Play.application().configuration()
								.getString("recaptchaKey"),
						temp.get("g-recaptcha-response")))
				.map(new Function<WSResponse, Result>() {
					// once we get the response this method is loaded
					public Result apply(WSResponse response) {
						// get the response as JSON
						JsonNode json = response.asJson();
						System.out.println(json);
						System.out.println(temp.get("g-recaptcha-response"));
						Form<Contact> submit = Form.form(Contact.class)
								.bindFromRequest();

						// check if value of success is true
						if (json.findValue("success").asBoolean() == true
								&& !submit.hasErrors()) {

							final String email = temp.get("email");

							List<User> admins = User.admins();
							for (User admin : admins) {
								ContactHelper.send(email, admin.email, message);
								ContactHelper.sendToPage(email, admin.email,
										message, "Contact Us message!");
							}
							flash("success", "Message sent!");
							if (session().get("email") == null)
								Logger.info("Guest has sent message to admin");
							else
								Logger.info("User with email: "
										+ session().get("email")
										+ " has sent message to admin");
							return redirect("/contactpage");
						} else {
							if (session().get("email") == null)
								Logger.info("Guest did not confirm its humanity");
							else
								Logger.info("User with email: "
										+ session().get("email")
										+ " did not confirm its humanity");
							flash("error",
									"You have to confirm that you are not a robot!");
							return ok(contact.render(userEmail, FAQ.all(),
									message));

						}
					}
				});
		// return the promisse
		return holder;
	}

	
	


}
