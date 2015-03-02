package controllers;

import play.mvc.*;

/**
 * Controls the application
 * @author eminamuratovic
 *
 */
public class Application extends Controller {

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
	
	


}
