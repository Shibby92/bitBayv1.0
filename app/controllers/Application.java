package controllers;

import models.LoginUser;
import models.User;
import play.*;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.*;
import views.html.*;

public class Application extends Controller {
	static Form<User> loginUser = new Form<User>(User.class);

	public static Result index() {
		String username = session("username");
		if (username != null) {
			return ok("Hi " + username.toString() + "! Welcome to bitBay!");
		} else {
			return redirect("/nouser");
		}
	}
	
	public static Result noUser() {
		return ok(nouser.render());
	}

	public static Result recognize() {
	
		DynamicForm form = Form.form().bindFromRequest();

		String username = form.data().get("username");
		String password = form.data().get("password");
		
		if (User.existsUsername(username)) {
			session("username", username);
			return redirect("/");
		}
		
		return redirect("/nouser");
	}

	public static Result addUser() {
		String username = loginUser.bindFromRequest().get().username;
		String password = loginUser.bindFromRequest().get().password;
		User.create(username, password);
		return redirect("/sreg");
	}

	public static Result register() {
		
		DynamicForm form = Form.form().bindFromRequest();

		String username = form.data().get("usernamesignup");
		String password = form.data().get("passwordsignup");
		User.create(username, password);
		
		String message = "Registration successful!";
		return ok(user.render(message));
	}

	public static Result uReg() {
		String message = "Registration unsuccessful!";
		return ok(user.render(message));
	}

}
