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
	 * @return result
	 */
	@Security.Authenticated(AdminFilter.class)
	public static Result addCategory() {
		DynamicForm form = Form.form().bindFromRequest();
		String name = form.data().get("name");
		Category.create(name);
		Logger.info(name + " category added in database");
		return redirect("/");
	}

	/**
	 * opens page for adding new category to database
	 * @return
	 */
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
	@Security.Authenticated(AdminFilter.class)
	public static Result deleteCategory(int id) {
		Logger.warn(Category.find(id).name + " category is deleted");
		Category.delete(id);
		flash("success","You have successfully deleted category!");
		return redirect("/profile");

	}
	
	/**
	 * opens the page where categories are listed
	 * @return result
	 */
	public static Result categoryPage() {
		Logger.info("Category page opened");
		String email = session().get("email");
		return ok(categorypage.render(email, Category.list(), FAQ.all()));
	}
	
	/**
	 * updates a category
	 * @param id int id of the category
	 * @return result
	 */
	@Security.Authenticated(AdminFilter.class)
	public static Result update(int id) {
		try {
			Category updateCategory = Category.find(id);
			String name = updateCategory.name;
			updateCategory.name = categoryForm.bindFromRequest().field("name")
					.value();
			Category.update(updateCategory);
			Logger.info(name + " category name has been updated as " + updateCategory.name);
			flash("success","You have successfully updated category!");
			return redirect("/profile");
		} catch (Exception e) {
			Logger.error("Error in update category " + e.getMessage());
			flash("error", "There has been a mistake in updating category!");
			return redirect("/homepage");
		}
	}
	
	/**
	 * opens page where user can update a category
	 * @param id int id of the category
	 * @return results
	 */
	@Security.Authenticated(AdminFilter.class)
	public static Result updateCategory(int id) {
		Logger.info("Update category page opened");
		String email = session().get("email");
		return ok(updatecategory.render(email, Category.find(id), FAQ.all()));
	}

}
