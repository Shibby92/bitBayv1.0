package controllers;


import java.util.ArrayList;
import java.util.List;
import java.net.MalformedURLException;
import java.text.*;
import java.util.Date;

import helpers.*;
import models.*;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.*;
import views.*;
import views.html.*;


public class UserController extends Controller {

	static Form<User> userForm = new Form<User>(User.class);

	/**
	 * goes to page where admin can update user
	 * @param id int id of the user
	 * @return result
	 */
	@Security.Authenticated(AdminFilter.class)
	public static Result toUpdateUser(int id) {
		Logger.info("User update page opened");
		String email = session().get("email");
		return ok(listofuserspage.render(email, User.find(id), FAQ.all()));

	}

	/**
	 * gets data from updated user
	 * redirect to page where it lists all users
	 * @param id int id of the user who is beeing updated
	 * @return result
	 * @throws MalformedURLException
	 */
	@Security.Authenticated(AdminFilter.class)
	public static Result updateUser(int id) throws MalformedURLException {

		DynamicForm form = Form.form().bindFromRequest();
		User updateUser = User.find(id);
		updateUser.email = form.get("email");
		updateUser.admin = Boolean.parseBoolean(form.get("admin"));
		if (!User.find(id).email.equals(updateUser.email)) {

			User.editEmailVerification(id);
		}

		updateUser.update();
		Logger.info("User with id: " + id + " has been updated");

		flash("success", "You have successfuly updated user!");
		return redirect("/profile");
	}

	/**
	 * deletes user and redirect to list of all users
	 * @param id int id of the user
	 * @return result
	 */
	@Security.Authenticated(AdminFilter.class)
	public static Result deleteUser(int id) {

		User.delete(id);
		Logger.warn("User with id: " + id + " has been deleted");
		flash("success", "You have successfuly deleted user!");
		return redirect("/profile");
	}

	/**
	 * redirects to page with additional info
	 * @return result
	 */
	@Security.Authenticated(UserFilter.class)
	public static Result toAdditionalInfo() {
		String email = session().get("email");
		Logger.info(session().get("email") + " has opened additional info page");
		return ok(additionalinfo.render(email, FAQ.all()));
	}

		/**
		 * adds additional info to user profile
		 * @return result
		 * @throws ParseException
		 */
	@Security.Authenticated(UserFilter.class)
	public static Result additionalInfo() throws ParseException {

		DynamicForm form = Form.form().bindFromRequest();
		String email = session().get("email");
		String username = form.get("username");
		Date current = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-dd-mm");
		String birth_date_string = userForm.bindFromRequest()
				.field("birth_date").value();
		Date birth_date = format.parse(birth_date_string);
		Logger.debug(birth_date.toString());
		if (!birth_date.before(current)) {
			Logger.error("User " + session().get("email")
					+ "has entered invalid date");
			flash("error", "Enter valid date!");
			return ok(additionalinfo.render(email, FAQ.all()));
		}
		String city = form.get("city");
		String shipping_address = form.get("shipping_address");
		String user_address = form.get("user_address");
		String gender = form.get("gender");
		if (!gender.toLowerCase().contains("m")
				&& !gender.toLowerCase().contains("f")) {
			Logger.error("User " + session().get("email")
					+ "has entered invalid gender");
			flash("error", "Enter valid gender!");
			return ok(additionalinfo.render(email, FAQ.all()));
		}

		if (User.AdditionalInfo(email, username, birth_date, shipping_address,
				user_address, gender, city)) {

			User u = User.find(email);
			u.hasAdditionalInfo = true;
			u.update();
			Logger.info("User " + session().get("email")
					+ "has added his additional info");
			return redirect("/homepage");
		}

		flash("warning", "Username already exists!");
		Logger.error("User " + session().get("email")
				+ "has entered invalid username");
		return ok(additionalinfo.render(email, FAQ.all()));

	}
	
	/**
	 * page for adding additional info
	 * @return result
	 */
	@Security.Authenticated(UserFilter.class)
	public static Result toEditInfo() {
		Logger.info("User " + session().get("email")
				+ "has opened his additional info");
		String email = session().get("email");
		return ok(editadditionalinfo.render(email,
				User.find(session().get("email")), FAQ.all()));

	}

	/**
	 * gets data from additional info 
	 * adds it to user profile and database
	 * @return result
	 * @throws ParseException
	 */
	@Security.Authenticated(UserFilter.class)
	public static Result editAdditionalInfo() throws ParseException {
		DynamicForm form = Form.form().bindFromRequest();
		User u = User.find(session().get("email"));
		String email = session().get("email");
		if (!User.existsUsername(form.get("username"))
				|| form.get("username").equals(u.username)) {
			if (!User.existsUsername(form.get("username")))
				u.username = form.get("username");
		} else {
			Logger.error("User " + session().get("email")
					+ "has entered invalid username");
			flash("error", "Username already exists!");
			return ok(editadditionalinfo.render(email, u, FAQ.all()));
		}
		Date current = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-dd-mm");
		if (!userForm.bindFromRequest().field("birth_date").value().equals("")) {
			String birth_date_string = userForm.bindFromRequest()
					.field("birth_date").value();
			u.birth_date = format.parse(birth_date_string);

			if (!u.birth_date.before(current)) {
				Logger.error("User " + session().get("email")
						+ "has entered invalid date");
				flash("error", "Enter valid date!");
				return ok(editadditionalinfo.render(email, u, FAQ.all()));
			}
		}
		u.city = form.get("city");
		u.shipping_address = form.get("shipping_address");
		u.user_address = form.get("user_address");
		if (!form.get("gender").equals("")) {
			u.gender = form.get("gender");
			if (!u.gender.toLowerCase().contains("m")
					&& !u.gender.toLowerCase().contains("f")) {
				Logger.error("User " + session().get("email")
						+ "has entered invalid gender");
				flash("error", "Enter valid gender!");
				return ok(editadditionalinfo.render(email, u, FAQ.all()));
			}
		}

		u.update();
		Logger.info("User " + session().get("email")
				+ "has updated his additional info");
		flash("success", "Additional info updated successfuly!");
		return redirect("/homepage");

	}
	
	/**
	 * opens page to user profile
	 * @return result
	 */
	@Security.Authenticated(UserFilter.class)
	public static Result profile() {
		Logger.info("User " + session().get("email")
				+ " has opened his profile page");
		String email = session().get("email");
		
		User u = User.find(email);
		List<Orders> soldProducts= new ArrayList<Orders>();
		for(Notification notification: u.notification){
				soldProducts.add(Orders.find(notification.orderId));
		}
		List<Report> reports = Report.all();
		List<Report> uniques = new ArrayList<Report>();
		for(Report report: reports) {
			boolean contains = false;
			for(Report r: uniques){
				if(report.reportedProduct.id == r.reportedProduct.id){
					contains = true;
				}
			}
			if(!contains)
				uniques.add(report);
						
		}
		Logger.debug(""+uniques.size());
		return ok(profile.render(email, User.all(), Category.list(),
				Product.productList(),
				Product.myProducts(User.find(session().get("email")).id),
				FAQ.all(), Message.all(User.find(session().get("email"))), soldProducts, uniques));

	}
	
	/**
	 * opens page to other users profile
	 * @return result
	 */
	public static Result userProfile(int id) {
		User user = User.find(id);
		Logger.info("User " + session().get("email")
				+ " has opened " + User.find(id).email + " profile page");
		String email = session().get("email");
		return ok(userprofile.render(user, email,
				Product.myProducts(user.id),
				FAQ.all()));
	}
	
	
	
	/**
	 * opens page for rating user
	 * @param id int id of the user
	 * @return result
	 */
	@Security.Authenticated(UserFilter.class)
	public static Result ratingpage(int id) {
		String email = session().get("email");
		User u = User.find(id);
		Logger.info("User with email: " + session().get("email")
				+ " has opened rate page");
		return ok(rating.render(email, u));

	}
	
	/**
	 * gets data from rating user
	 * adds rating to user
	 * saves it in database
	 * @param id int id of the user
	 * @return result
	 */
	@Security.Authenticated(UserFilter.class)
	public static Result rating(int id) {
		final DynamicForm temp = DynamicForm.form().bindFromRequest();
		User u = User.find(id);
		int rate = Integer.parseInt(temp.get("rate"));
		u.numberOfRatings++;
		u.rating += rate;
		u.rating = (double) (u.rating / u.numberOfRatings);
		u.update();
		Logger.info("User with email: " + session().get("email")
				+ " has rated user with id: " + id);
		flash("success", "You have successfuly rated user!");
		return redirect("/userprofile/" + id);
	}




}
