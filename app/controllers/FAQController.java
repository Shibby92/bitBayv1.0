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

public class FAQController extends Controller {
	
	/**
	 * makes a page with listed FAQs
	 * @return result
	 */
	public static Result allFAQs() {
		String email = session().get("email");
		return ok(faq.render(email, FAQ.all()));
	}
	
	/**
	 * makes a page add new FAQ
	 * @return result
	 */
	@Security.Authenticated(AdminFilter.class)
	public static Result toAddNewFAQ() {
		String email = session().get("email");
		return ok(newfaq.render(email));
	}
	
	/**
	 * gets question and answer from the add new FAQ page
	 * @return result
	 */
	@Security.Authenticated(AdminFilter.class)
	public static Result addNewFAQ() {
		String email = session().get("email");
		DynamicForm form = Form.form().bindFromRequest();
		
		String question = form.get("question");
		String answer = form.get("answer");
		FAQ.createFAQ(question, answer);	
		
		flash("success","New question added!");
		return ok(newfaq.render(email));
	}
	
	/**
	 * makes a page where you update FAQ
	 * @param id int the id of the FAQ
	 * @return result
	 */
	@Security.Authenticated(AdminFilter.class)
	public static Result toUpdateFAQ(int id) {
		
		FAQ q = FAQ.find(id);
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
		f.answer = form.get("answer");
		f.question = form.get("question");
		f.update();
		flash("success","Successful update!");
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
		String email = session().get("email");
		FAQ.delete(id);
		flash("success", "Question deleted!");
		return ok(faq.render(email, FAQ.all()));
	}
	
	

}
