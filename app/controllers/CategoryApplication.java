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

	// method that should delete category and redirect to other products/uses
	// delete method from Category class
	public static Result deleteCategory(int id) {
		Category.delete(id);
		return redirect("/categorypage");

	}
	public static Result categoryPage(){
		return ok(categorypage.render(Category.list()));
	}
	
	public static Result update(int id){
		
		Category updateCategory= Category.find(id);
		updateCategory.name=categoryForm.bindFromRequest().field("name").value();
		Category.update(updateCategory);
		return redirect("/categorypage");
	}
	
	public static Result updateCategory(int id){
		return ok(updatecategory.render(Category.find(id)));
	}
	

}
