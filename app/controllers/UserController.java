package controllers;

import helpers.*;
import models.User;
import play.mvc.*;
import views.*;
import views.html.listofusers;

public class UserController extends Controller {

	@Security.Authenticated(AdminFilter.class)
	public static Result list() {
		
		return ok(listofusers.render(User.all()));
	}	
	public static Result toList() {
		return redirect("/listofusers");
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
		//return TODO;
		return redirect("/admin");
	}
	
	public static Result adminRender() {
		return TODO;
		//return ok(admin.render());
	}

}
