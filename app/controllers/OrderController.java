package controllers;

import helpers.UserFilter;
import models.*;
import play.Logger;
import play.db.ebean.Model.Finder;
import play.mvc.*;
import views.html.*;

// TODO: Auto-generated Javadoc
/**
 * The Class OrderController.
 */
public class OrderController extends Controller {
	
	/** The find. */
	public static Finder <Integer,Notification> find=new Finder<Integer,Notification>(Integer.class,Notification.class);

	/**
	 * Opens order page.
	 *
	 * @param id int the id of the user
	 * @return the result
	 */
	@Security.Authenticated(UserFilter.class)
	public static Result orderPage(int id) {
		String email = session().get("email");
		Logger.info("User " + session().get("email") + " has opened order page");
		return ok(orderpage.render(email, User.find.byId(id).orderList,
				FAQ.all()));
	}

	/**
	 * Opens sold order page.
	 *
	 * @param id int the id of the user 
	 * @return the result
	 */
	@Security.Authenticated(UserFilter.class)
	public static Result soldOrderPage(int id) {
		String email = session().get("email");
		Logger.info("User " + email + " has opened sold order page");
		return ok(soldorderpage.render(email,
				User.getUncheckedNotifications(id), FAQ.all()));
	}

	/**
	 * Sold order checked by user.
	 *
	 * @param id int the id of the order
	 * @return the result
	 */
	@Security.Authenticated(UserFilter.class)
	public static Result soldOrderChecked(int id) {
		Orders order = Orders.findOrder.byId(id);
		User seller= User.find(session().get("email"));
		Notification notification =find.where().eq("order_id", order.id).eq("seller_id", seller.id).findUnique();
		notification.isUnchecked=false;
		notification.update();
		Logger.info("User" + session().get("email") + " has checked his notification");
		return redirect("/homepage");
	}
	
	
}