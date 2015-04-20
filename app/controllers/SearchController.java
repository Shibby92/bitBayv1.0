package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

import models.*;
import play.data.DynamicForm;
import play.data.Form;
import play.db.ebean.Model.Finder;
import play.mvc.*;
import views.*;
import views.html.*;
import play.Logger;

public class SearchController extends Controller {

	/**
	 * class for filtered search
	 * @author mustafaademovic
	 */
	public static class FilteredSearch {
		String category;
		String product;
		String description;
		String minValue;
		String maxValue;

		public FilteredSearch(String category, String product,
				String description, String minValue, String maxValue) {

			this.category = category;
			this.product = product;
			this.description = description;
			this.minValue = minValue;
			this.maxValue = maxValue;
		}

		public FilteredSearch() {

		}

	}

	static Form<Product> newProduct = new Form<Product>(Product.class);
	static Form<FilteredSearch> filteredSearch = new Form<FilteredSearch>(
			FilteredSearch.class);
	static Finder<Integer, Product> findProduct = new Finder<Integer, Product>(
			Integer.class, Product.class);
	
	/**
	 * searches products in database
	 * @param q String entered word in search engine
	 * @return result
	 */
	public static Result search(String q) {

		List<Product> products = Product.find.where()
				.ilike("name", "%" + q + "%").findList();
		String email = session().get("email");
		return ok(showsearchresults.render(email, products, FAQ.all()));

	}

	/**
	 * searches users in database
	 * @param q String entered word in search engine
	 * @return result
	 */
	public static Result searchUsers(String q) {
		List<User> users = User.findUser.where().ilike("email", "%" + q + "%")
				.findList();
		String email = session().get("email");

		return ok(searchusers.render(email, users, FAQ.all()));

	}

	/**
	 * opens page for advanced search
	 * @return result
	 */
	public static Result advancedSearchPage() {

		String email = session().get("email");
		List<Product> filteredProducts = Product.productList();
		return ok(advancedsearch.render(email, filteredProducts,
				Category.list(), FAQ.all()));

	}

	/**
	 * searches every entered data in advanced search page
	 * @param ids String ids of the products
	 * @return result
	 */
	public static Result advancedSearch(String ids) {

		if (ids.length() < 1) {
			flash("error", "List is empty.");
			return redirect("/advancedsearch");
		}

		DynamicForm df = Form.form().bindFromRequest();
		String email = session().get("email");

		String[] productsIDs = ids.split(",");
		Logger.debug(ids);
		List<Product> products = new ArrayList<Product>();
		for (String id : productsIDs) {
			long currentID = Long.valueOf(id);
			int current = (int) currentID;
			Product currentProduct = Product.find.byId(current);
			products.add(currentProduct);
		}

		List<Product> productList = new ArrayList<Product>();
		double priceMin = 0;
		double priceMax = 999999;
		String descr;
		Category category;

		String prod = df.data().get("product");
		String min = df.data().get("minValue");
		String max = df.data().get("maxValue");
		descr = filteredSearch.bindFromRequest().get().description;
		String categoryName = df.data().get("category");
		category = Category.findByName(categoryName);

		if (prod == null) {
			prod = "";
		}

		if (min.isEmpty()) {
			priceMin = Double.MIN_VALUE;
			min = String.valueOf(priceMin);

		} else {
			priceMin = Double.valueOf(min);
			Logger.debug("Min value " + priceMin);
		}

		if (max.isEmpty()) {
			priceMax = Double.MAX_VALUE;
			max = String.valueOf(priceMax);

		} else {
			priceMax = Double.valueOf(max);
			Logger.debug("Max value " + priceMax);
		}
		if (descr == null) {
			descr = "";
		}

		productList = Product.find.where()
				.ilike("category_id", "" + category.id)
				.ilike("name", "%" + prod + "%")
				.ilike("description", "%" + descr + "%").findList();
		List<Product> filteredProducts = new ArrayList<Product>();

		for (Product product : productList) {
			if (product.price > priceMin && product.price < priceMax) {
				filteredProducts.add(product);
			}
		}
		Logger.debug("Size of list" + productList.size());

		return ok(advancedsearch.render(email, filteredProducts,
				Category.list(), FAQ.all()));

	}

	

}
