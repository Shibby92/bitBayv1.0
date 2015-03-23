package controllers;

import java.util.List;
import models.Product;
import play.mvc.Result;
import play.mvc.*;
import views.*;
import views.html.*;


public class SearchController extends Controller{

	public static Result search(String q) {
		List<Product> products = Product.find.where()
				.ilike("name", "%" + q + "%").findList();
		if (products.size() > 0) {
			return ok(showsearchresults.render(products));
		} else {
			return redirect("/homepage");
		}

	}
}
