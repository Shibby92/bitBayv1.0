package controllers;

import models.FAQ;
import models.Orders;
import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class OrderController extends Controller {

	public static Result orderPage(int id) {
		String email = session().get("email");
		return ok(orderpage.render(email, User.findUser.byId(id).orderList,
				FAQ.all()));
	}

	public static Result soldOrderPage(int id) {
		String email = session().get("email");
		return ok(soldorderpage.render(email,
				User.findUser.byId(id).soldOrders, FAQ.all()));
	}

	public static Result soldOrderChecked(int id) {
		Orders order = Orders.find.byId(id);
		order.notification = false;
		order.update();
		return redirect("/homepage");
	}
	
}
