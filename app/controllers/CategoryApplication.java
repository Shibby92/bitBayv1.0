package controllers;

import helpers.AdminFilter;
import models.Category;
import play.Logger;
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
		Logger.info("addCategory done!");
		DynamicForm form = Form.form().bindFromRequest();
		String name = form.data().get("name");
		Category.create(name);
		return redirect("/");
	}

	@Security.Authenticated(AdminFilter.class)
	public static Result addNewCategory() {
		Logger.info("page opened");
		return ok(addcategorypage.render(categoryForm));
	}

	// method that should delete category and redirect to other products/uses
	// delete method from Category class
	public static Result deleteCategory(int id) {
		Logger.warn("category is deleted");
		Category.delete(id);
		return redirect("/categorypage");

	}
	public static Result categoryPage(){
		Logger.info("page opened");
		return ok(categorypage.render(Category.list()));
	}
	
	public static Result update(int id){
		Logger.info("category is updated");
		Category updateCategory= Category.find(id);
		updateCategory.name=categoryForm.bindFromRequest().field("name").value();
		Category.update(updateCategory);
		return redirect("/categorypage");
	}
	
	public static Result updateCategory(int id){
		Logger.info("update category page opened");
		return ok(updatecategory.render(Category.find(id)));
	}
	

}
