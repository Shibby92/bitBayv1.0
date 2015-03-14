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
		
		User u2 = new User("mustafa@gmail.com",HashHelper.createPassword("123456"), false, true);
		User.create(u2);
		u2.id = 0;
		
		Category.create("Fashion");
		Category.create("Phones");
		Category.create("Houses");
		
	}

}
