package helpers;

import models.User;
import play.mvc.Http.Context;
import play.mvc.Security;


public class Session extends Security.Authenticator {

	public static User getCurrentUser(Context ctx) {
		if (!ctx.session().containsKey("email")) {
			return null;
		}
		User activeUser = User.find(ctx.session().get("email"));
		return activeUser;
	}

	public static User getCurrent(Context ctx) {
		User u = getCurrentUser(ctx);
		if (u != null) {
			return u;
		}
		return null;
	}
	
}
