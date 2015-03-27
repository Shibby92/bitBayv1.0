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
		DynamicForm form = Form.form().bindFromRequest();
		String name = form.data().get("name");
		Category.create(name);
		Logger.info(name + " category added in database");
		return redirect("/");
	}

	@Security.Authenticated(AdminFilter.class)
	public static Result addNewCategory() {
		Logger.info("Opened page for adding category");
		String email = session().get("email");
		return ok(addcategorypage.render(email, categoryForm, FAQ.all()));
	}


	/**
	 * method that should delete category and redirect to other products/uses
	 * delete method from Category class
	 * @param id int id of the category
	 * @return result
	 */
	public static Result deleteCategory(int id) {
		
		Category.delete(id);
		Logger.warn(Category.find(id).name + " category is deleted");
		return redirect("/categorypage");

	}
	
	/**
	 * opens the page where categories are listed
	 * @return result
	 */
	public static Result categoryPage(){
		Logger.info("Category page opened");
		return ok(categorypage.render(Category.list()));
	}
	
	/**
	 * updates a category
	 * @param id int id of the category
	 * @return result
	 */
	public static Result update(int id){
		
		Category updateCategory= Category.find(id);
		updateCategory.name=categoryForm.bindFromRequest().field("name").value();
		Category.update(updateCategory);
		Logger.info(updateCategory.name + " category is updated");
		return redirect("/categorypage");
	}
	
	/**
	 * opens page where user can update a category
	 * @param id int id of the category
	 * @return results
	 */
	public static Result updateCategory(int id){
	Logger.info("Update category page opened");
		String email = session().get("email");
		return ok(updatecategory.render(email, Category.find(id), FAQ.all()));
	}
	

}
