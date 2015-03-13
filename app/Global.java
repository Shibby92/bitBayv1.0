import helpers.HashHelper;
import models.*;
import play.Application;
import play.GlobalSettings;
import play.db.ebean.Model.Finder;


public class Global extends GlobalSettings{
	
	
	public void onStart(Application app) {
		
		User u = new User("admin@gmail.com",HashHelper.createPassword("admin"), true, true);
		User.create(u);
		u.id = 0;
	}

}
