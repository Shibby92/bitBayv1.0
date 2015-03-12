package controllers;

import helpers.*;
import models.User;
import play.mvc.*;
import views.*;

public class UserController extends Controller {

	@Security.Authenticated(AdminFilter.class)
	public static Result list() {
		return TODO;
		//return ok(listofusers.render(User.all()));
	}
	
	@Security.Authenticated(AdminFilter.class)
	public static Result delete(String username){
		return TODO;
	}
	
	@Security.Authenticated(AdminFilter.class)
	public static Result update(String username) {
		return TODO;
	}
	
	@Security.Authenticated(UserFilter.class)
	public static Result edit(String username) {
		return TODO;
	}
	@Security.Authenticated(AdminFilter.class)
	public static Result admin() {
		return TODO;
//		return ok(admin.render());
	}

}
