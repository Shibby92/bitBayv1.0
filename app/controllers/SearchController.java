package controllers;

import java.util.List;


import models.FAQ;
import models.Product;
import models.User;
import models.Product;
import models.*;
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
			return redirect("/showsearchresults");
		}

	}
	public static Result searchUsers(String q) {
		List<User> users = User.findUser.where()
				.ilike("email", "%" + q + "%").findList();
		String email = session().get("email");
		
			return ok(searchusers.render(email, users, FAQ.all()));
		
	}
}
