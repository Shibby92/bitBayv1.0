package controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import models.*;
import play.data.*;
import play.mvc.*;
import views.html.*;

/**
 * Controls the ad application
 * Redirects on the pages when needed
 * @author eminamuratovic
 *
 */
public class ProductApplication extends Controller {
	static Form<User> loginUser = new Form<User>(User.class);

	
	//user picks new category for his product
		public static Result pickCategory() {
			DynamicForm form = Form.form().bindFromRequest();
			
			String category = form.data().get("category");//kad mustafa postavi  id category
			//checks if the category is already in database
			//if there is a category it redirects to addproduct page
			//it forwards id of the category
			if(Category.categoryExists(category)) {
				return redirect("/addproduct/" + Category.categoryId(category)); 
			}
			
			//if there is no category by that name it creates redirect to previous page
			return redirect("/addcategory");	
		}
		
		//adds additional info to product
		//creates new product
		//returns user to his home page
		public static Result addAdditionalInfo(int id) {
			DynamicForm form = Form.form().bindFromRequest();
			Date created = null;

			String name = form.data().get("name");
			int owner_id = loginUser.bindFromRequest().get().id;
			int category_id = id;
			DateFormat format = new SimpleDateFormat("MMMM d, yyyy");
			try {
				created = format.parse(form.data().get("created"));
			} catch (ParseException e) {
				System.err.println("Date does not have proper form!");
			}
			int quantity = Integer.parseInt(form.data().get("quantity"));
			float price = Float.parseFloat(form.data().get("price"));
			String description = form.data().get("description");
			String image_url = form.data().get("image url");
			Product.create(name, category_id, owner_id, created, quantity, price, description, image_url);
			return redirect("/home");
		}
		
		
}
