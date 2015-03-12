package controllers;

import helpers.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import models.*;
import play.data.*;
import play.mvc.*;
import views.html.*;

/**
 * Controls the ad application Redirects on the pages when needed
 * 
 * @author eminamuratovic
 *
 */
public class ProductApplication extends Controller {
	static Form<User> loginUser = new Form<User>(User.class);

	// user picks new category for his product
	@Security.Authenticated(UserFilter.class)
	public static Result pickCategory() {

		DynamicForm form = Form.form().bindFromRequest();

		String category = form.data().get("category");

		// if there is no category by that name it creates redirect to previous
		// page
		return redirect("/addproduct/" + Category.categoryId(category));
	}

	// adds additional info to product
	// creates new product
	// returns user to his home page

	@Security.Authenticated(UserFilter.class)
	public static Result addAdditionalInfo(int id) {
		DynamicForm form = Form.form().bindFromRequest();
		String name = form.data().get("name");
		User owner = new User(session().get("username"), form.get("password"));
		int category_id = id;
		DateFormat format = new SimpleDateFormat("MMMM d, yyyy");
		Date created = new Date();
		int quantity = 0;// Integer.parseInt(form.data().get("quantity"));
		double price = Double.parseDouble(form.data().get("price"));
		String description = form.data().get("description");
		String image_url = "";// form.data().get("image url");
		Product.create(name, category_id, owner, created, quantity, price,
				description, image_url);
		return redirect("/homepage");
	}

	public static Result category(String name) {
		return ok(category.render(name));
	}

	public static Result toPickCategory() {
		return ok(addproductcategory.render());
	}

	public static Result toInfo(int id) {
		return ok(addproduct.render(id));
	}
}
