package controllers;

import helpers.AdminFilter;
import models.Category;
import models.FAQ;
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
		String email = session().get("email");
		Logger.info("page opened");
		return ok(addcategorypage.render(email, categoryForm, FAQ.all()));
	}


	/**
	 * method that should delete category and redirect to other products/uses
	 * delete method from Category class
	 * @param id int id of the category
	 * @return result
	 */
	public static Result deleteCategory(int id) {
		Logger.warn("category is deleted");
		Category.delete(id);
		return redirect("/categorypage");

	}
	
	/**
	 * opens the page where categories are listed
	 * @return result
	 */
	public static Result categoryPage(){
		Logger.info("page opened");
		return ok(categorypage.render(Category.list()));
	}
	
	/**
	 * updates a category
	 * @param id int id of the category
	 * @return result
	 */
	public static Result update(int id){
		Logger.info("category is updated");
		Category updateCategory= Category.find(id);
		updateCategory.name=categoryForm.bindFromRequest().field("name").value();
		Category.update(updateCategory);
		return redirect("/categorypage");
	}
	
	/**
	 * opens page where user can update a category
	 * @param id int id of the category
	 * @return results
	 */
	public static Result updateCategory(int id){
		String email = session().get("email");
		Logger.info("update category page opened");
		return ok(updatecategory.render(email, Category.find(id), FAQ.all()));
	}
	

}
