package controllers;

import helpers.*;
import models.User;
import play.data.Form;
import play.mvc.*;
import views.*;
import views.html.listofusers;

public class UserController extends Controller {
	
	static Form<User> userForm = new Form<User>(User.class);

	@Security.Authenticated(AdminFilter.class)
	public static Result toUpdateUser(int id) {
		return TODO;
		//return ok(listofuserspage.render(User.find(id)));
	}	
	@Security.Authenticated(AdminFilter.class)
	public static Result toUpdate() {
		
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
	public static Result deleteUser(int id){
		User.delete(id);
		return redirect("/listofusers");
	}
	
	@Security.Authenticated(AdminFilter.class)
	public static Result updateUser(int id){
		User updateUser= User.find(id);
		updateUser.email=userForm.bindFromRequest().get().email;
		User.update(updateUser);
		return redirect("/listofusers");
	}

}
