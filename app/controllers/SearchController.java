package controllers;

import java.util.List;
import java.util.stream.Collectors;

import models.*;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.*;
import views.*;
import views.html.*;

public class SearchController extends Controller {

	public static Result search(String q) {
		List<Product> products = Product.find.where()
				.ilike("name", "%" + q + "%").findList();
		String email = session().get("email");
		return ok(showsearchresults.render(email, products, FAQ.all()));

	}

	public static Result searchUsers(String q) {
		List<User> users = User.findUser.where().ilike("email", "%" + q + "%")
				.findList();
		String email = session().get("email");

		return ok(searchusers.render(email, users, FAQ.all()));

	}

	public static Result advancedSearch() {

		String email = session().get("email");
		return ok(advancedsearch.render(email, Category.list(), FAQ.all()));

	}

	public static Result advancedSearchpage() {

		DynamicForm form = Form.form().bindFromRequest();

		/*
		 * String productName = form.data().get("name"); String description =
		 * form.data().get("description"); String minValuee =
		 * form.data().get("minPrice"); String maxValuee =
		 * form.data().get("maxPrice"); double minValue =
		 * Double.parseDouble(minValuee); double maxValue =
		 * Double.parseDouble(maxValuee);
		 * 
		 * List<Product> products = Product.find.all(); /*Product.find.where()
		 * .ilike("category_id", String.valueOf(category.id)).findList();
		 * 
		 * if(productName != null) { products=products.stream().filter(product
		 * -> product.name.contains(productName)).collect(Collectors.toList());
		 * }
		 * 
		 * if(description != null) { products=products.stream().filter(product
		 * ->
		 * product.description.contains(description)).collect(Collectors.toList
		 * ()); } if((minValue != 0) && (maxValue != 0) && (minValue <
		 * maxValue)) { products=products.stream().filter(product ->
		 * ((product.price > minValue) && (product.price >
		 * maxValue))).collect(Collectors.toList()); } String email =
		 * session().get("email"); return ok(advancedsearchpage.render(email,
		 * products, FAQ.all()));
		 */
		String email = session().get("email");
		Form<Search> filteredSearch = new Form<Search>(Search.class);
		String category = filteredSearch.bindFromRequest().get().category;
		String product = filteredSearch.bindFromRequest().get().product;

		double priceMin = filteredSearch.bindFromRequest().get().minValue;

		double priceMax = filteredSearch.bindFromRequest().get().maxValue;

		String description = filteredSearch.bindFromRequest().get().description;

		List<Product> products = Product.find.where(
				"(Category LIKE '" + category + "') AND (Product LIKE '"
						+ product + "') AND (Ddescription LIKE '" + description
						+ "') AND ((price>=" + priceMin + " AND price<="
						+ priceMax + "))").findList();

		return ok(advancedsearchpage.render(email, products,  FAQ.all()));

	}

	public class Search {
		String category;
		String product;
		String description;
		double minValue;
		double maxValue;
		
	

	public Search() {
		super();
	}
		
	}

}
