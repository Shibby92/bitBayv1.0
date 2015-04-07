import java.util.ArrayList;
import java.util.List;

import helpers.HashHelper;
import models.*;
import play.Application;
import play.GlobalSettings;
import play.db.ebean.Model.Finder;

public class Global extends GlobalSettings {

	public void onStart(Application app) {

		if (User.find("admin@gmail.com") == null) {
			User u = new User("admin@gmail.com",
					HashHelper.createPassword("admin"), true, true);
			User.create(u);
			u.username = "Admin";
			u.user_address = "Admin Street 229 ";
			u.hasAdditionalInfo = true;
			u.update();

			User u2 = new User("mustafa@gmail.com",
					HashHelper.createPassword("123456"), false, true);
			User.create(u2);
			
			User u3 = new User("emina.muratovic@bitcamp.ba", HashHelper.createPassword("emina"), false, true);
			u.username = "Emina";
			User.create(u3);

		}

		if (Category.find(1) == null) {
			String categoryArray[] = { "Cars", "Fashion", "Mobile phones",
					"Computers", "Houses", "Shoes", "Biznis", "Animals" };

			for (int i = 0; i < categoryArray.length; i++) {
				Category.create(categoryArray[i]);
			}

		}


		if (Product.find(1) == null) {
			
			Product.create("House",
					100000,User.find(2),
					"This house has got four bedrooms, a living-room, a dining-room, a kitchen");
			Product p =  Product.find(1);
			
			Image img1 = new Image();
			img1.image = "images/logo.png";
			img1.product = p;
	

			List<Image> img = new ArrayList<Image>();
			img.add(img1);
			Image.saveImg(img1);
			p.images = img;
			p.category_id = 1;

			
			Product.create(
					"Mazda",
					25000,User.find(2),
					"Auto mazda mx-5 skoro novo!!!");
			Product pr = Product.find(2);
			
			Image img2 = new Image();
			img2.image = "images/logo.png";
			img2.product = pr;
		

			List<Image> imgg = new ArrayList<Image>();
			imgg.add(img2);
			Image.saveImg(img2);
			p.category_id = 2;
			p.images = imgg;
			
			
			Product.create("Eminin proizvod", 2000, User.find(3), "Neki opis");
			Product pro = Product.find(3);
			Image img3 = new Image();
			img3.image = "eminamuratovic/Downloads/10846602_1024193347607270_1598213656_n.jpg";
			img3.product = pro;
		

			List<Image> imggg = new ArrayList<Image>();
			imggg.add(img3);
			Image.saveImg(img3);
			pro.category_id = 3;
			pro.images = imggg;


		}
	

			if (!FAQ.find("I can't get items shipped until Monday and I'm afraid of hurting my top-rated seller qualification. How should I go about this?")) {
				FAQ.createFAQ(
						"I can't get items shipped until Monday and I'm afraid of hurting my top-rated seller qualification. How should I go about this?",
						"You may, if you can, decide to reward their patience by refunding the shipping cost (let them know that in the apology email).");
				FAQ.createFAQ(
						"I have 30 something bids on my vintage camper and about 6 of them are from people with 0 feedback. Is this scammers? I have sold on ebay for years and never saw this before.",
						"People with 0 feedback are not necessarily scammers.  We all had 0 feedback once.  ");
				FAQ.createFAQ(
						"Additional free listing this month (new listings only) Why didn't I receive this?",
						"Promo offers are by invitation only; no party crashing allowed.  Unless there is an einstein among us, no one can figure out the criteria used by EBay to get these promos.");
			}

		
	
	
	
}
	}
