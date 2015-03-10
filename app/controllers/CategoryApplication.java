package controllers;

import helpers.AdminFilter;
import models.Category;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.*;

public class CategoryApplication extends Controller {

	static Form<Category> categoryForm = new Form<Category>(Category.class);

	/**
	 * Adding a new category in the db
	 */	
	@Security.Authenticated(AdminFilter.class)
	public static Result addCategory() {
		DynamicForm form = Form.form().bindFromRequest();
		String name = form.data().get("name");
		Category.create(name);
		return redirect("/");
	}
	
	@Security.Authenticated(AdminFilter.class)
	public static Result addNewCategory() {
		return ok(addcategorypage.render(categoryForm));
	}
}
