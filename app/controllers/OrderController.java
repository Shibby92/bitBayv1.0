package controllers;

import models.FAQ;
import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class OrderController extends Controller {
	
	public static Result orderPage(int id){
		String email = session().get("email");
		return ok(orderpage.render(email,User.findUser.byId(id).orderList,FAQ.all()));
	}

}
