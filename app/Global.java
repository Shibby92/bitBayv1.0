import helpers.HashHelper;
import models.*;
import play.Application;
import play.GlobalSettings;
import play.db.ebean.Model.Finder;


public class Global extends GlobalSettings{
	
	
	public void onStart(Application app) {
		Finder<String, User> find = new Finder<String, User>(String.class, User.class);
		
		if(!find.equals("admin@gmail.com")){
		User u = new User("admin@gmail.com",HashHelper.createPassword("admin"), true, true);
		User.create(u);
		u.id = 0;
		
		User u2 = new User("mustafa@gmail.com",HashHelper.createPassword("123456"), false, true);
		User.create(u2);
		u.id = 1;
		}

		Category.create("Fashion");
		Product.create("House", 100000, "This house has got four bedrooms, a living-room, a dining-room, a kitchen, a bathroom and a separate toilet." , 1);
		
		
	}

}
