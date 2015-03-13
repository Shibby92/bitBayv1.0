import helpers.HashHelper;
import models.*;
import play.Application;
import play.GlobalSettings;


public class Global extends GlobalSettings{
	
	public void onStart(Application app) {
		User u = new User("Admin",HashHelper.createPassword("admin"), true);
		User.create(u);
		u.id = 0;
	}

}
