package controllers;

import helpers.AdminFilter;
import models.Category;
import models.FAQ;
import play.Logger;
import play.Play;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.*;

/**
 * Controller for categories
 * 
 * @author harisarifovic
 *
 */
public class CategoryApplication extends Controller {

	static Form<Category> categoryForm = new Form<Category>(Category.class);

	/**
	 * Adding a new category in the database
	 * 
	 * @return result
	 */
	@Security.Authenticated(AdminFilter.class)
	public static Result addCategory() {
		DynamicForm form = Form.form().bindFromRequest();
		String name = form.data().get("name");
		Category.create(name);
		Logger.info(name
				+ Play.application().configuration()
						.getString("categoryApplicationLogger1"));
		return redirect("/");
	}

	/**
	 * opens page for adding new category to database
	 * 
	 * @return
	 */
	@Security.Authenticated(AdminFilter.class)
	public static Result addNewCategory() {
		Logger.info(Play.application().configuration()
				.getString("categoryApplicationLogger2"));
		String email = session().get("email");
		return ok(addcategorypage.render(email, categoryForm, FAQ.all()));

	}

	/**
	 * method that should delete category and redirect to other products/uses
	 * delete method from Category class
	 * 
	 * @param id
	 *            id of the category
	 * @return result
	 */
	@Security.Authenticated(AdminFilter.class)
	public static Result deleteCategory(int id) {
		Logger.warn(Category.find(id).name
				+ Play.application().configuration()
						.getString("categoryApplicationLogger3"));
		Category.delete(id);
		flash("success",
				Play.application().configuration()
						.getString("categoryApplicationFlash1"));
		return redirect("/profile");

	}

	/**
	 * opens the page where categories are listed
	 * 
	 * @return result
	 */
	public static Result categoryPage() {
		Logger.info(Play.application().configuration()
				.getString("categoryApplicationLogger4"));
		String email = session().get("email");
		return ok(categorypage.render(email, Category.list(), FAQ.all()));
	}

	/**
	 * updates a category
	 * 
	 * @param id
	 *            id of the category
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
			Logger.info(name
					+ Play.application().configuration()
							.getString("categoryApplicationLogger5")
					+ updateCategory.name);
			flash("success",
					Play.application().configuration()
							.getString("categoryApplicationFlash2"));
			return redirect("/profile");
		} catch (Exception e) {
			Logger.error(Play.application().configuration()
					.getString("categoryApplicationLogger6")
					+ e.getMessage());
			flash("error",
					Play.application().configuration()
							.getString("categoryApplicationFlash3"));
			return redirect("/profile");
		}
	}

	/**
	 * opens page where user can update a category
	 * 
	 * @param id
	 *            id of the category
	 * @return results
	 */
	@Security.Authenticated(AdminFilter.class)
	public static Result updateCategory(int id) {
		Logger.info(Play.application().configuration()
				.getString("categoryApplicationLogger7"));
		String email = session().get("email");
		return ok(updatecategory.render(email, Category.find(id), FAQ.all()));
	}

}
