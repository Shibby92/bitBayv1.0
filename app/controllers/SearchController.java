package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import models.*;
import play.data.DynamicForm;
import play.data.Form;
import play.db.ebean.Model.Finder;
import play.mvc.*;
import views.*;
import views.html.*;
import play.Logger;

public class SearchController extends Controller {

	public static class FilteredSearch {
		String category;
		String product;
		String description;
		String minValue;
		String maxValue;

		public FilteredSearch(String category, String product, String description,
				String minValue, String maxValue) {

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
	static Form<FilteredSearch> filteredSearch=new Form<FilteredSearch>(FilteredSearch.class);
	static Finder<Integer, Product> findProduct = new Finder<Integer, Product>(Integer.class, Product.class);
	
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

	
	public static Result advancedSearchPage() {
		
		String email = session().get("email");
		List<Product> filteredProducts = Product.productList();
		return ok(advancedsearch.render(email, filteredProducts, Category.list(), FAQ.all()));

	}

	public static Result advancedSearch(String ids) {

		DynamicForm df = Form.form().bindFromRequest();
		String email = session().get("email");
			
			String[] productsIDs = ids.split(",");
			Logger.debug(ids);
			List<Product>products= new ArrayList<Product>();
			for(String id: productsIDs){
				long currentID = Long.valueOf(id);
				int current=(int)currentID;
				Product currentProduct = Product.find.byId(current);			
				products.add(currentProduct);
			}
			
			List<Product>productList=new ArrayList<Product>();
		    double priceMin=0;
		    double priceMax=9999999;
			String descr;
			Category category;
		
			String prod =df.data().get("product");
			String min= df.data().get("minValue");
			String max=df.data().get("maxValue");
			descr=filteredSearch.bindFromRequest().get().description;
			String categoryName= df.data().get("category");
			category = Category.findByName(categoryName);
			
			if(prod == null){
				prod = "";
			}
			if(min == null){
				priceMin = Double.MIN_VALUE;
			}else{
				priceMin=Double.valueOf(min);
				Logger.debug("Min value " +priceMin);
			}
			
			if( max == null){
				priceMax = Double.MAX_VALUE;
			}else{
				priceMax=Double.valueOf(max);
				Logger.debug("Max value "+priceMax);
			}
			if(descr==null){
				descr = "";				
			}

			productList = Product.find.where().ilike("category_id", ""+category.id).ilike("name", "%" +prod+ "%").ilike("description","%"+descr +"%").findList();
			List<Product> filteredProducts =new ArrayList<Product>();
			
			for(Product product: productList){
				if(product.price > priceMin && product.price < priceMax){
					filteredProducts.add(product);
				}
			}
			Logger.debug("Siye of list" +productList.size());

			
		return ok(advancedsearch.render(email, filteredProducts, Category.list(), FAQ.all()));

	}


	

}
