package controllers;

import java.util.List;

import models.FAQ;
import models.Product;
import play.mvc.*;
import views.*;
import views.html.*;


public class SearchController extends Controller{

	public static Result search(String q) {
		List<Product> products = Product.find.where()
				.ilike("name", "%" + q + "%").findList();
		String email = session().get("email");
		if (products.size() > 0) {
			return ok(showsearchresults.render(email, products, FAQ.all()));
		} else {
			return redirect("/homepage");
		}

	}
}
