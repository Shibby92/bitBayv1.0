package helpers;

import models.User;
import play.mvc.Result;
import play.mvc.Security;
import play.mvc.Http.Context;

public class UserFilter extends Security.Authenticator {

	@Override
	public String getUsername(Context ctx) {
		if(!ctx.session().containsKey("username"))
			return null;
		String username = ctx.session().get("username");
		User u = User.find(username);
		if (u != null)
			return u.username;
		return null;
	}
	
	@Override
	public Result onUnauthorized(Context ctx) {
		return redirect("/login");
	}
}
