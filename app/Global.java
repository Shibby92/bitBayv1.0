import helpers.HashHelper;
import models.*;
import play.Application;
import play.GlobalSettings;
import play.db.ebean.Model.Finder;


public class Global extends GlobalSettings{
	
	
	public void onStart(Application app) {
		
		if(User.find("admin@gmail.com") == null) {
		User u = new User("admin@gmail.com",HashHelper.createPassword("admin"), true, true);
		User.create(u);
		u.id = 0;
		
		User u2 = new User("mustafa@gmail.com",HashHelper.createPassword("123456"), false, true);
		User.create(u2);
		u.id = 1;
		}

		if(Category.find(1) == null) {
			String categoryArray[] = {"Cars", "Fashion", "Mobile phones", 
					"Computers","Houses", "Shoes", "Biznis",
					"Animals"};
			
			for (int i = 0; i < categoryArray.length; i++) {
				Category.create(categoryArray[i]);
			}
			
		}
		
		if(Product.find(1) == null) {
			Product.create("Versaci shoes", 1200, "good shoes", 1);
		}
		
		
		
	}

}
