package controllers;

import java.net.MalformedURLException;
import java.text.*;
import java.util.Date;
import java.util.Locale;

import org.springframework.format.datetime.DateFormatter;

import helpers.*;
import models.*;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.*;
import views.*;
import views.html.listofusers;
import views.html.listofuserspage;
import views.html.additionalinfo;

public class UserController extends Controller {
	
	static Form<User> userForm = new Form<User>(User.class);

	//goes to page where admin can update user
	@Security.Authenticated(AdminFilter.class)
	public static Result toUpdateUser(int id) {
		return ok(listofuserspage.render(User.find(id)));
	}	
	
	//goes to page where it lists all of registered users
	@Security.Authenticated(AdminFilter.class)
	public static Result toUpdate() {
		
		return ok(listofusers.render(User.all()));
	}	
	
	//gets data from updated user
	//redirect to page where it lists all users
	@Security.Authenticated(AdminFilter.class)
	public static Result updateUser(int id) throws MalformedURLException{
		DynamicForm form =  Form.form().bindFromRequest();
		User updateUser= User.find(id);

		updateUser.email=form.get("email");
		updateUser.admin = Boolean.parseBoolean(form.get("admin"));
		if(!User.find(id).email.equals(updateUser.email)) {
			
				User.editEmailVerification(id);
		}
		//User.update(updateUser);

		return redirect("/listofusers");
	}
	
	//redirect to page where it lists all students
	@Security.Authenticated(AdminFilter.class)
	public static Result toList() {
		return redirect("/listofusers");
	}
	
	//deletes user and redirect to list of all users
	@Security.Authenticated(AdminFilter.class)
	public static Result deleteUser(int id){
		User.delete(id);
		return redirect("/listofusers");
	}
	
	//redirects to page with additional info
	@Security.Authenticated(UserFilter.class)
	public static Result toAdditionalInfo() {
		return ok(additionalinfo.render());
	}
	
	@Security.Authenticated(UserFilter.class)
	public static Result additionalInfo() throws ParseException {
		DynamicForm form =  Form.form().bindFromRequest();
		String email = session().get("email");
		String username = form.get("username");
		Date current = new Date();
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		Date birth_date = format.parse(form.get("birth_date"));
		if(!birth_date.before(current)) {
			flash("error", "Enter valid date!");
			return ok(additionalinfo.render());
		}
		String city = form.get("city");
		String shipping_address = form.get("shipping_address");
		String user_address = form.get("user_address");
		String gender = form.get("gender");
		if(!gender.toLowerCase().contains("m") && !gender.toLowerCase().contains("f")) {
			flash("error", "Enter valid gender!");
			return ok(additionalinfo.render());
		}
			
		
		if(User.AdditionalInfo(email, username, birth_date, shipping_address, user_address, gender, city)) {
			return redirect("/homepage");
		}
		
		flash("warning", "Username already exists!");
		return ok(additionalinfo.render());
			
		
	}
	
	

}
