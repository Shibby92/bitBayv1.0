package controllers;

import models.User;
import play.mvc.Controller;
import play.i18n.Messages;
import play.mvc.Result;

public class Verification extends Controller{
	
	public static Result verificateEmail(String confirmation) {
		User u = User.findByConfirmation(confirmation);
		
		 if(confirmation == null) {
			 flash("error", Messages.get("error"));
	            return redirect("/");
		 }
		 if (User.confirm(u)) {
	            flash("success", Messages.get("Successfully validated"));
	            return redirect("/login");
	        } else {
	            flash("errorLink", Messages.get("error.confirm"));
	            return redirect("/");
	        }
		 
	}

}
