package controllers;

import models.*;
import play.data.*;
import play.mvc.*;

/**
 * Controls the login application
 * Redirects on the pages when needed
 * When the user registers, he gets redirected to page with ads
 * If the user is already registered, then he gets redirected to LOG IN page
 * @author eminamuratovic
 *
 */
public class UserLoginApplication extends Controller {
	static Form<User> loginUser = new Form<User>(User.class);

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
		//if the user gets registered, he gets redirected to his home page
		public static Result register() {
			
			DynamicForm form = Form.form().bindFromRequest();

			String username = form.data().get("usernamesignup");
			String password = form.data().get("passwordsignup");
			if(User.create(username, password)) {
				return redirect("/home");
			}
				
			return redirect("/nouser");
			
		}
		
		//goes to page where the user can be registered
		public static Result toRegister(){
			return ok(toregister.render());
		}
		
		//home page of the user
		//he can see ads that someone else had added
		//he has an option to add his own ad
		public static Result home() {
			return ok(home.render());
		}
}
