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

			User u2 = new User("mustafa.ademovic93@gmail.com",
					HashHelper.createPassword("123456"), false, true);
			User.create(u2);
			
			User u3 = new User("emina.muratovic@bitcamp.ba",
					HashHelper.createPassword("emina"), false, true);
			User.create(u3);
			
			User u4 = new User("haris.arifovic@bitcamp.ba", 
					HashHelper.createPassword("haris"), false, true);
			User.create(u4);
			
			User u5= new User("hikmet.durgutovic@bitcamp.ba", 
					HashHelper.createPassword("hikmet"), false, true);
			User.create(u5);
			
			User u6 = new User("nermin.vucinic@bitcamp.ba", 
					HashHelper.createPassword("nermin"), false, true);
			User.create(u6);

		}

		if (Category.find(1) == null) {
			String categoryArray[] = { "Cars", "Fashion", "Mobile phones",
					"Computers", "Houses", "Shoes", "Biznis", "Animals" };

			for (int i = 0; i < categoryArray.length; i++) {
				Category.create(categoryArray[i]);
			}

		}


		if (Product.find(1) == null) {
			
		

			Product.create(
					"Mazda m5",
					25000,1, User.find(2),
					"condition:	used, "
					 +"year: 2010, "
					 + "make: Mazda, "
					 + "mileage: 49,831, "
					 + "transmission: manual",
					1,"images/6800.jpg");
			
			Product.create("Galaxy S6"
					, 2000,15, User.find(3), 
					"Detailed: item info, "
					+ "Product: Identifiers, "
					+ "Brand: Samsung, "
					+ "Carrier: Unlocked,"
					+ " Family Line: Samsung Galaxy S6,"
					+ " Type: Smartphone, "
					+ "Digital Camera: Yes,"
					+ " Email Access: Yes,"
					+ " Battery Capacity: 2550 mAh,"
					+ " Display Technology:	Quad HD Super AMOLED" , 
					3, "images/samsung.jpg");
			
			Product.create(
					"House Stup",
					880000,1,User.find(3),
					"Imported Italian marble floors in the foyer, in the 2 1/2 baths and the 1st floor laundry room,Designed in the atmosphere of a Las Vegas Suite. Very ornate.  Three bedrooms upstairs. Ssiral staircase. ",
					5,"images/bitbaySlika2.jpg");
			
			Product.create(
					"Mercedes 270",
					45750,2,User.find(4),
					 "year: 2015, "
					 + "make: Mercedes, "
					 +"condition:	used, "
					 + "mileage: 21,841, "
					 + "transmission: manual",
					1,"images/mercedes.jpg");
			
			Product.create(
					"Dog",
					120,3,User.find(6),
					"Young dog, 3 monts, only money!!!",8,"images/dog.jpg");
			
			Product.create(
					"BMW 320",
					15000,1, User.find(4),
					 "year: 2003, "
					 + "mileage: 129,274, "
					 + "transmission: automatic"
					 + "make: Bmw, ",
					1,"images/bmw.jpg");
			
			
			Product.create("Iphone 6"
					, 1530,15,  User.find(5), 
					 "Brand: Apple,"
					+ "Storage Capacity: 64 GB, "
					+ " Camera:	8.0MP,"
					+ " Model: iPhone 6 Plus,"
					+ "Digital Camera: Yes,"
					+ " Email Access: Yes,"
					+ " Battery Capacity: 2000 mAh,"
					+ " Operating System:iOS" , 
					3, "images/iphone.jpg");
			
			Product.create(
					"Cat",
					120,1, User.find(6),
					"Young cat, 2 monts, if you want good pet this is the best choice!!!",8,"images/cat.jpg");
			
			Product.create(
					"House",
					100000,3, User.find(2),
					"This house has got four bedrooms, a living-room, a dining-room, a kitchen",
					5,"images/bitbaySlika1.jpg");
			
			
		



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
