package controllers;

import models.*;
import helpers.*;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.*;
import play.Logger;
import play.Play;

/**
 * FAQController for different FAQ's functionalities
 * 
 * @author harisarifovic
 *
 */
public class FAQController extends Controller {

	/**
	 * makes a page with listed FAQs
	 * 
	 * @return result
	 */
	public static Result allFAQs() {
		Logger.info("Opened FAQs page");
		String email = session().get("email");
		return ok(faq.render(email, FAQ.all()));

	}

	/**
	 * makes a page add new FAQ
	 * 
	 * @return result
	 */
	@Security.Authenticated(AdminFilter.class)
	public static Result toAddNewFAQ() {
		Logger.info("Opened page for adding new FAQ");
		String email = session().get("email");
		return ok(newfaq.render(email));

	}

	/**
	 * gets question and answer from the add new FAQ page
	 * 
	 * @return result
	 */
	@Security.Authenticated(AdminFilter.class)
	public static Result addNewFAQ() {
		String email = session().get("email");
		DynamicForm form = Form.form().bindFromRequest();

		try {
			String question = form.get("question");
			String answer = form.get("answer");
			FAQ.createFAQ(question, answer);
			Logger.info("New FAQ added with question: " + question);
			flash("success",
					Play.application().configuration()
							.getString("FAQControllerFlash1"));
			return ok(newfaq.render(email));
		} catch (Exception e) {
			Logger.error("Error in addNewFAQ");
			flash("error",
					Play.application().configuration()
							.getString("FAQControllerFlash2"));
			return redirect("/homepage");
		}
	}

	/**
	 * makes a page where you update FAQ
	 * 
	 * @param id
	 *            int the id of the FAQ
	 * @return result
	 */
	@Security.Authenticated(AdminFilter.class)
	public static Result toUpdateFAQ(int id) {
		String email = session().get("email");
		FAQ q = FAQ.find(id);
		Logger.info("Opened page for FAQ update");
		return ok(updatefaq.render(email, q, FAQ.all()));
	}

	/**
	 * gets the data from update from FAQ saves it in database
	 * 
	 * @param id
	 * @return result
	 */
	@Security.Authenticated(AdminFilter.class)
	public static Result updateFAQ(int id) {
		String email = session().get("email");
		DynamicForm form = Form.form().bindFromRequest();
		FAQ f = FAQ.find(id);
		FAQ oldFAQ = f;
		try {
			f.answer = form.get("answer");
			f.question = form.get("question");
			f.update();
			flash("success",
					Play.application().configuration()
							.getString("FAQControllerFlash3"));
			if (!oldFAQ.question.equals(f.question)
					&& oldFAQ.answer.equals(f.answer))
				Logger.info("FAQ with id: " + id + " updated with question: "
						+ f.question);
			else if (!oldFAQ.question.equals(f.question)
					&& !oldFAQ.answer.equals(f.answer))
				Logger.info("FAQ with id: " + id + " updated with question: "
						+ f.question + " and answer: " + f.answer);
			else if (oldFAQ.question.equals(f.question)
					&& !oldFAQ.answer.equals(f.answer))
				Logger.info("FAQ with id: " + id + " updated with answer: "
						+ f.answer);
			else
				Logger.info("FAQ with id: " + id + " hasn't been changed");
			return ok(updatefaq.render(email, f, FAQ.all()));
		} catch (Exception e) {
			Logger.error("Error in updating FAQs");
			flash("error",
					Play.application().configuration()
							.getString("FAQControllerFlash4"));
			return redirect("/homepage");
		}
	}

	/**
	 * deletes FAQ returns to all FAQs
	 * 
	 * @param id
	 *            int id of the FAQ
	 * @return result
	 */
	@Security.Authenticated(AdminFilter.class)
	public static Result deleteFAQ(int id) {
		String email = session().get("email");
		try {
			Logger.warn("FAQ with id: " + id + " has been deleted");
			FAQ.delete(id);
			flash("success",
					Play.application().configuration()
							.getString("FAQControllerFlash5"));
			return ok(faq.render(email, FAQ.all()));
		} catch (Exception e) {
			Logger.error("Error in delete FAQ");
			flash("error",
					Play.application().configuration()
							.getString("FAQControllerFlash6"));
			return redirect("/homepage");
		}
	}

}
