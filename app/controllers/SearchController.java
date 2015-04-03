package controllers;

import java.util.List;
import java.util.stream.Collectors;




import models.*;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.*;
import views.*;
import views.html.*;


public class SearchController extends Controller{

	public static Result search(String q) {
		List<Product> products = Product.find.where()
				.ilike("name", "%" + q + "%").findList();
		String email = session().get("email");
			return ok(showsearchresults.render(email, products, FAQ.all()));
	
	}
	public static Result searchUsers(String q) {
		List<User> users = User.findUser.where()
				.ilike("email", "%" + q + "%").findList();
		String email = session().get("email");
		
			return ok(searchusers.render(email, users, FAQ.all()));
		

	}
	public static Result advancedSearch() {
		
		String email = session().get("email");
			return ok(advancedsearch.render(email,Category.list(),FAQ.all()));
		

	}
	
	public static Result advancedSearchpage() {
		
		DynamicForm form = Form.form().bindFromRequest();
		
		Category category = Category.findCategory.where().eq(form.data().get("category"), "name").findUnique();
		String productName = form.data().get("name");
		String description = form.data().get("description");
		int minValue = Integer.parseInt(form.data().get("minPrice"));
		int maxValue = Integer.parseInt(form.data().get("maxPrice"));

		List<Product> products = Product.find.where()
				.ilike("category_id", String.valueOf(category.id)).findList();
		
		if(productName != null) {
		products=products.stream().filter(product -> product.name.contains(productName)).collect(Collectors.toList());
		}
		
		if(description != null) {
			products=products.stream().filter(product -> product.description.contains(description)).collect(Collectors.toList());
		}
		if((minValue != 0) && (maxValue != 0) && (minValue < maxValue)) {
			products=products.stream().filter(product -> ((product.price > minValue) && (product.price > maxValue))).collect(Collectors.toList());
		}
		String email = session().get("email");
			return ok(advancedsearchpage.render(email, products, FAQ.all()));
	

	}
	
}
