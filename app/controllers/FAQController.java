package controllers;

import models.*;
import helpers.*;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.*;
import views.html.*;
import play.Logger;

public class FAQController extends Controller {
	
	/**
	 * makes a page with listed FAQs
	 * @return result
	 */
	public static Result allFAQs() {
		Logger.info("Opened FAQs page");
		return ok(faq.render(FAQ.all()));
	}
	
	/**
	 * makes a page add new FAQ
	 * @return result
	 */
	@Security.Authenticated(AdminFilter.class)
	public static Result toAddNewFAQ() {
		Logger.info("Opened page for adding new FAQ");
		return ok(newfaq.render());
	}
	
	/**
	 * gets question and answer from the add new FAQ page
	 * @return result
	 */
	@Security.Authenticated(AdminFilter.class)
	public static Result addNewFAQ() {
		DynamicForm form = Form.form().bindFromRequest();
		
		String question = form.get("question");
		String answer = form.get("answer");
		FAQ.createFAQ(question, answer);	
		Logger.info("New FAQ added with question: " + question);
		flash("success","New question added!");
		return ok(newfaq.render());
	}
	
	/**
	 * makes a page where you update FAQ
	 * @param id int the id of the FAQ
	 * @return result
	 */
	@Security.Authenticated(AdminFilter.class)
	public static Result toUpdateFAQ(int id) {
		
		FAQ q = FAQ.find(id);
		Logger.info("Opened page for FAQ update");
		return ok(updatefaq.render(q));
	}
	
	/**
	 * gets the data from update from FAQ
	 * saves it in database
	 * @param id
	 * @return result
	 */
	@Security.Authenticated(AdminFilter.class)
	public static Result updateFAQ(int id) {
		DynamicForm form = Form.form().bindFromRequest();
		FAQ f = FAQ.find(id);
		FAQ oldFAQ = f;
		f.answer = form.get("answer");
		f.question = form.get("question");
		f.update();
		flash("success","Successful update!");
		if(!oldFAQ.question.equals(f.question) && oldFAQ.answer.equals(f.answer))
			Logger.info("FAQ with id: " + id + " updated with question: " + f.question);
		else if(!oldFAQ.question.equals(f.question) && !oldFAQ.answer.equals(f.answer))
			Logger.info("FAQ with id: " + id + " updated with question: " + f.question + " and answer: " + f.answer);
		else if(oldFAQ.question.equals(f.question) && !oldFAQ.answer.equals(f.answer))
			Logger.info("FAQ with id: " + id + " updated with answer: " + f.answer);
		else
			Logger.info("FAQ with id: " + id + " hasn't been changed");
		return ok(updatefaq.render(f));
	}
	
	/**
	 * deletes FAQ
	 * returns to all FAQs
	 * @param id
	 * @return result
	 */
	@Security.Authenticated(AdminFilter.class)
	public static Result deleteFAQ(int id) {
		FAQ.delete(id);
		Logger.warn("FAQ with id: " + id + " has been deleted");
		flash("success", "Question deleted!");
		return ok(faq.render(FAQ.all()));
	}
	
	

}
