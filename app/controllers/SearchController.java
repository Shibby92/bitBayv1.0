package controllers;

import java.util.List;

import models.Category;
import models.FAQ;
import models.Product;
import models.User;
import play.data.Form;
import play.mvc.Result;
import views.*;
import views.html.*;
import java.net.MalformedURLException;
import java.text.*;
import java.util.Date;
import java.util.Locale;

import org.springframework.format.datetime.DateFormatter;

import helpers.*;
import models.*;
import play.Logger;
import play.data.DynamicForm;

import play.mvc.*;


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
