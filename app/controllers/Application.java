package controllers;

import models.LoginUser;
import models.User;
import play.*;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.*;
import views.html.*;

/**
 * Controlles the application
 * Redirects on the pages when needed
 * When the user registers, he gets redirected to page: "Successful registration"
 * If the user is already registered, then he gets redirected to LOG IN page
 * @author eminamuratovic
 *
 */
public class Application extends Controller {
	static Form<User> loginUser = new Form<User>(User.class);

	//index page
	//if the user get registered, then this page prints: "Hi user! Welcome to bitBay!"
	//if there is a user with the same username, than he gets redirected to login page
	public static Result index() {
		String username = session("username");
		if (username != null) {
			return ok("Hi " + username.toString() + "! Welcome to bitBay!");
		} else {
			return redirect("/nouser");
		}
	}
	
	//main page
	//login page
	public static Result noUser() {
		return ok(nouser.render());
	}

	//tries to log user to page
	//if the user can log, he gets redirected to index page
	//if the user is not in database, he gets redirected to register page
	public static Result login() {
	
		DynamicForm form = Form.form().bindFromRequest();

		String username = form.data().get("username");
		String password = form.data().get("password");
		
		if (User.existsUsername(username)) {
			session("username", username);
			return redirect("/");
		}
		
		return redirect("/nouser");
	}

	//tries to register user
	//if there is already user with the same username he gets redirected to login page
	//if the user gets registered, he gets redirected to page: "Successful registration"
	public static Result register() {
		
		DynamicForm form = Form.form().bindFromRequest();

		String username = form.data().get("usernamesignup");
		String password = form.data().get("passwordsignup");
		if(User.create(username, password)) {
			return ok(user.render("Successful registration"));
		}
			
		return redirect("/nouser");
		
	}
	public static Result toRegister(){
		return ok(toregister.render());
	}


}
