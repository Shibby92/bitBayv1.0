package controllers;

import helpers.*;
import models.*;
import play.*;
import play.data.*;
import play.mvc.*;
import views.html.*;

// TODO: Auto-generated Javadoc
/**
 * The Class CategoryApplication.
 */
public class CategoryApplication extends Controller {

	/** The category form. */
	static Form<Category> categoryForm = new Form<Category>(Category.class);

	/**
	 * Adds the category.
	 *
	 * @return the result
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
	 * Opens page for adding new category.
	 *
	 * @return the result
	 */
	@Security.Authenticated(AdminFilter.class)
	public static Result addNewCategory() {
		Logger.info(Play.application().configuration()
				.getString("categoryApplicationLogger2"));
		String email = session().get("email");
		return ok(addcategorypage.render(email, categoryForm, FAQ.all()));

	}

	/**
	 * Deletes category.
	 *
	 * @param id int the id of the category
	 * @return the result
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
	 * Category page.
	 *
	 * @return the result
	 */
	public static Result categoryPage() {
		Logger.info(Play.application().configuration()
				.getString("categoryApplicationLogger4"));
		String email = session().get("email");
		return ok(categorypage.render(email, Category.list(), FAQ.all()));
	}


	/**
	 * Updates category.
	 *
	 * @param id int the id of the category
	 * @return the result
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
	 * Opens page for updating category.
	 *
	 * @param id the id
	 * @return the result
	 */
	@Security.Authenticated(AdminFilter.class)
	public static Result updateCategory(int id) {
		Logger.info(Play.application().configuration()
				.getString("categoryApplicationLogger7"));
		String email = session().get("email");
		return ok(updatecategory.render(email, Category.find(id), FAQ.all()));
	}
	
	/**
	 * Opens a page with all of the products from one category.
	 * @param name String name of the category
	 * @return result
	 */
	public static Result category(String name) {
		String email = session().get("email");
		Logger.info("Category page list opened");
		return ok(category.render(email, name, Product.listByCategory(name),
				FAQ.all(), Category.list()));
	}

}
