package controllers;

import helpers.*;
import models.*;
import play.Logger;
import play.data.*;
import play.mvc.*;
import views.html.*;

// TODO: Auto-generated Javadoc
/**
 * The Class FAQController.
 */
public class FAQController extends Controller {
	
	/**
	 * Opens page with listed FAQs.
	 *
	 * @return the result
	 */
	public static Result allFAQs() {
		Logger.info("Opened FAQs page");
		String email = session().get("email");
		return ok(faq.render(email, FAQ.all()));

	}
	
	/**
	 * Opens page for adding new FAQ.
	 *
	 * @return the result
	 */
	@Security.Authenticated(AdminFilter.class)
	public static Result toAddNewFAQ() {
		Logger.info("Opened page for adding new FAQ");
		String email = session().get("email");
		return ok(newfaq.render(email));

	}
	
	/**
	 * Adds new FAQ to database.
	 *
	 * @return the result
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
			flash("success", "New question added!");
			return ok(newfaq.render(email));
		} catch (Exception e) {
			Logger.error("Error in addNewFAQ");
			flash("error", "There has been an error in adding FAQ!");
			return redirect("/homepage");
		}
	}
	
	/**
	 * Opens page for updating FAQs.
	 *
	 * @param id int the id of the FAQ
	 * @return the result
	 */
	@Security.Authenticated(AdminFilter.class)
	public static Result toUpdateFAQ(int id) {
		String email = session().get("email");
		FAQ q = FAQ.find(id);
		Logger.info("Opened page for FAQ update");
		return ok(updatefaq.render(email, q, FAQ.all()));
	}

	/**
	 * Updates FAQ.
	 *
	 * @param id the id of the FAQ
	 * @return the result
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
			flash("success", "Successful update!");
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
			flash("error", "There has been an error in updating FAQ!");
			return redirect("/homepage");
		}
	}
	
	/**
	 * Deletes FAQ.
	 *
	 * @param id int id of the FAQ
	 * @return the result
	 */
	@Security.Authenticated(AdminFilter.class)
	public static Result deleteFAQ(int id) {
		String email = session().get("email");
		try {
			Logger.warn("FAQ with id: " + id + " has been deleted");
			FAQ.delete(id);
			flash("success", "Question deleted!");
			return ok(faq.render(email, FAQ.all()));
		} catch (Exception e) {
			Logger.error("Error in delete FAQ");
			flash("error", "There has been an error in deleting FAQ!");
			return redirect("/homepage");
		}
	}
	

}