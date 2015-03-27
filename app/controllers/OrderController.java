package controllers;

import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class OrderController extends Controller {
	
	public static Result orderPage(int id){
		return ok(orderpage.render(User.findUser.byId(id).orderList));
	}

}
