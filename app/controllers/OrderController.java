package controllers;

import helpers.UserFilter;
import models.*;
import play.Logger;
import play.db.ebean.Model.Finder;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.*;

/**
 * Controller for order functionalities
 * 
 * @author harisarifovic
 *
 */

public class OrderController extends Controller {

	public static Finder<Integer, Notification> find = new Finder<Integer, Notification>(
			Integer.class, Notification.class);

	/**
	 * Opens the orderpage
	 * 
	 * @param id
	 *            ID of the user
	 * @return orderpage
	 */
	@Security.Authenticated(UserFilter.class)
	public static Result orderPage(int id) {
		String email = session().get("email");
		Logger.info("User " + session().get("email") + " has opened order page");
		return ok(orderpage.render(email, User.findUser.byId(id).orderList,
				FAQ.all()));
	}

	/**
	 * Shows orders that has been sold
	 * 
	 * @param id
	 *            ID of the user
	 * @return soldorderpage
	 */
	@Security.Authenticated(UserFilter.class)
	public static Result soldOrderPage(int id) {
		String email = session().get("email");
		Logger.info("User " + email + " has opened sold order page");
		return ok(soldorderpage.render(email,
				User.getUncheckedNotifications(id), FAQ.all()));
	}

	/**
	 * After the user checked the notification, it disappears in this method
	 * 
	 * @param id
	 *            ID of the user
	 * @return homepage but wuth no more notifiations
	 */
	@Security.Authenticated(UserFilter.class)
	public static Result soldOrderChecked(int id) {
		Orders order = Orders.find.byId(id);
		User seller = User.find(session().get("email"));
		Notification notification = find.where().eq("order_id", order.id)
				.eq("seller_id", seller.id).findUnique();
		notification.isUnchecked = false;
		notification.update();
		Logger.info("User" + session().get("email")
				+ " has checked his notification");
		return redirect("/homepage");
	}

}
