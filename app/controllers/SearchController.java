package controllers;

import java.util.*;

import models.*;
import play.Logger;
import play.data.*;
import play.db.ebean.Model.Finder;
import play.mvc.*;
import views.html.*;

// TODO: Auto-generated Javadoc
/**
 * The Class SearchController.
 */
public class SearchController extends Controller {

	/**
	 * The Class for filtered search.
	 */
	public static class FilteredSearch {
		
		/** The category. */
		String category;
		
		/** The product. */
		String product;
		
		/** The description. */
		String description;
		
		/** The min value. */
		String minValue;
		
		/** The max value. */
		String maxValue;

		/**
		 * Instantiates a new filtered search.
		 *
		 * @param category String the category
		 * @param product String the product
		 * @param description String the description
		 * @param minValue String the min value
		 * @param maxValue String the max value
		 */
		public FilteredSearch(String category, String product,
				String description, String minValue, String maxValue) {

			this.category = category;
			this.product = product;
			this.description = description;
			this.minValue = minValue;
			this.maxValue = maxValue;
		}

		/**
		 * Instantiates a new filtered search.
		 */
		public FilteredSearch() {

		}

	}

	/** The new product. */
	static Form<Product> newProduct = new Form<Product>(Product.class);
	
	/** The filtered search. */
	static Form<FilteredSearch> filteredSearch = new Form<FilteredSearch>(
			FilteredSearch.class);
	
	/** The find product. */
	static Finder<Integer, Product> findProduct = new Finder<Integer, Product>(
			Integer.class, Product.class);
	
	/**
	 * Searches products in database.
	 *
	 * @param q String entered word in search engine
	 * @return the result
	 */
	public static Result search(String q) {

		List<Product> products = Product.find.where()
				.ilike("name", "%" + q + "%").findList();
		String email = session().get("email");
		return ok(showsearchresults.render(email, products, FAQ.all()));

	}

	/**
	 * Searches users in database.
	 *
	 * @param q String entered word in search engine
	 * @return result
	 */
	public static Result searchUsers(String q) {
		List<User> users = User.find.where().ilike("email", "%" + q + "%")
				.findList();
		String email = session().get("email");

		return ok(searchusers.render(email, users, FAQ.all()));

	}

	/**
	 * Opens page for advanced search.
	 *
	 * @return the result
	 */
	public static Result advancedSearchPage() {

		String email = session().get("email");
		List<Product> filteredProducts = Product.productList();
		return ok(advancedsearch.render(email, filteredProducts,
				Category.list(), FAQ.all()));

	}

	/**
	 * Searches every entered data in advanced search page.
	 *
	 * @param ids String ids of the products
	 * @return the result
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

		if (category == null) {
			productList = Product.find.where().ilike("name", "%" + prod + "%")
					.ilike("description", "%" + descr + "%").findList();
		} else {

			productList = Product.find.where()
					.ilike("category_id", "" + category.id)
					.ilike("name", "%" + prod + "%")
					.ilike("description", "%" + descr + "%").findList();
		}
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
