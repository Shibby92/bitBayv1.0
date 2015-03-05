package controllers;

import models.*;
import play.data.*;
import play.mvc.*;
import views.html.*;

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
		public static Result homePage() {

			DynamicForm form = Form.form().bindFromRequest();
			String username = form.data().get("username");
			if(username == null) 
				username = "Public user";
			return ok(homePage.render(username));
			
			

		}

		//tries to log user to page
		//if the user can log, he gets redirected to index page
		//if the user is not in database, he gets redirected to register page
		public static Result login() {
		
			DynamicForm form = Form.form().bindFromRequest();

			String username = form.data().get("username");
			String password = form.data().get("passwordsignup");
			
			if (User.existsUsername(username)) {
				if(User.checkLogin(username, password)){
					session("username", username);
					return redirect("/homepage");
				}
					else
					return redirect("/login");
			}
			return redirect("/toregister");
		}

		//tries to register user
		//if there is already user with the same username he gets redirected to login page
		//if the user gets registered, he gets redirected to his home page
		public static Result register() {
			
			DynamicForm form = Form.form().bindFromRequest();

			String username = form.data().get("usernamesignup");
			String password = User.hashPw(form.data().get("passwordsignup"));
			if(User.create(username, password)) {
				return redirect("/homepage");
			}
				
			return redirect("/toregister");
		
		}
		
		//goes to page where the user can be registered
		public static Result toRegister(){
		
			return ok(toregister.render());
		}
		
		//home page of the user
		//he can see ads that someone else had added
		//he has an option to add his own ad
		public static Result home() {
			return TODO;

			//return ok(home.render());
		}
		
		public static Result toLogin() {
			return ok(logintest.render());
		}
		
	
}
