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
			String name = session().get("username");
			return ok(homePage.render(name));
			}

		//tries to log user to page
		//if the user can log, he gets redirected to index page
		//if the user is not in database, he gets redirected to register page
		public static Result login() {
		
			session("username", "test1");
			DynamicForm form = Form.form().bindFromRequest();

			String username = form.get("username");
			String password = form.get("password");
			
			if (User.existsUsername(username)) {
				if(User.checkLogin(username, password)){
					session("username", username);
					return redirect("/homepage");
				}
					else{
						session("username", "test3");
						return redirect("/login");
					}
					
			}session("username", "test4");
			return ok(toregister.render(loginUser));
		}

		//tries to register user
		//if there is already user with the same username he gets redirected to login page
		//if the user gets registered, he gets redirected to his home page
		public static Result register() {
			
			User u= loginUser.bindFromRequest().get();
			if(User.create(u.username, u.password)) {
				return redirect("/login");
			}
				
			return ok(toregister.render(loginUser));

			
		}
		
		//goes to page where the user can be registered
		public static Result toRegister(){
		
			return ok(toregister.render(loginUser));
		}
		
		//home page of the user
		//he can see ads that someone else had added
		//he has an option to add his own ad
		
		public static Result toLogin() {			
			return ok(logintest.render());
		}
		
	
}
