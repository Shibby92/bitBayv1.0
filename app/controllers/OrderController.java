package controllers;

import models.*;
import play.db.ebean.Model.Finder;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class OrderController extends Controller {
	
	public static Finder <Integer,Notification> find=new Finder<Integer,Notification>(Integer.class,Notification.class);

	public static Result orderPage(int id) {
		String email = session().get("email");
		return ok(orderpage.render(email, User.findUser.byId(id).orderList,
				FAQ.all()));
	}

	public static Result soldOrderPage(int id) {
		String email = session().get("email");
		return ok(soldorderpage.render(email,
				User.getUncheckedNotifications(id), FAQ.all()));
	}

	public static Result soldOrderChecked(int id) {
		Orders order = Orders.find.byId(id);
		User seller= User.find(session().get("email"));
		Notification notification =find.where().eq("order_id", order.id).eq("seller_id", seller.id).findUnique();
		notification.isUnchecked=false;
		notification.update();
		return redirect("/homepage");
	}
	
	
}
