import java.util.ArrayList;
import java.util.List;

import helpers.HashHelper;
import models.*;
import play.Application;
import play.GlobalSettings;
import play.db.ebean.Model.Finder;

/**
 * At the opening of the website there is already created:
 * 6 users
 * 8 categories
 * 10 products(connected to their parent category)
 * 3 FAQs
 * @author eminamuratovic
 *
 */
public class Global extends GlobalSettings {

	public void onStart(Application app) {

		if (User.find("admin@gmail.com") == null) {
			
			
			User u = new User("admin@gmail.com",
					HashHelper.createPassword("admin"), true, true);
			u.user_address = "Admin Street 229 ";
			User.create(u);
			u.username = "Admin";
			u.hasAdditionalInfo = true;
			u.update();

			User u2 = new User("mustafa.ademovic93@gmail.com",
					HashHelper.createPassword("123456"), false, true);
			User.create(u2);
			u2.username = "Mustafa";
			u2.user_address = "Admin Street 229 ";
			u2.hasAdditionalInfo = true;
			u2.update();

			User u3 = new User("emina.muratovic@bitcamp.ba",
					HashHelper.createPassword("emina"), false, true);
			User.create(u3);
			u3.username = "Emina";
			u3.user_address = "Admin Street 229 ";
			u3.hasAdditionalInfo = true;
			u3.update();

			User u4 = new User("haris.arifovic@bitcamp.ba",
					HashHelper.createPassword("haris"), false, true);
			u4.user_address="Lozionicka 2";
			User.create(u4);
			u4.username="Haris";
			u4.hasAdditionalInfo=true;
			u4.update();

			User u5 = new User("hikmet.durgutovic@bitcamp.ba",
					HashHelper.createPassword("hikmet"), false, true);
			User.create(u5);

			User u6 = new User("nermin.vucinic@bitcamp.ba",
					HashHelper.createPassword("nermin"), false, true);
			User.create(u6);

		}
		
		if(Message.find(1) == null) {
			
			Message.create("Dragi svi "
					+ "u ime predavaca Arifa je na Google Classroom "
					+ "(Predavanja) postavljen kod. "
					+ "Molim da ovo primite k znanju. "
					+ "Lijep pozdrav", User.find(2), User.find(1));
			Message.create("Dragi svi "
					+ "u ime predavaca Arifa je na Google Classroom "
					+ "(Predavanja) postavljen kod. "
					+ "Molim da ovo primite k znanju. "
					+ "Lijep pozdrav", User.find(3), User.find(1));
			Message.create("Dragi svi "
					+ "u ime predavaca Arifa je na Google Classroom "
					+ "(Predavanja) postavljen kod. "
					+ "Molim da ovo primite k znanju. "
					+ "Lijep pozdrav", User.find(4), User.find(1));
			Message.create("Dragi svi "
					+ "u ime predavaca Arifa je na Google Classroom "
					+ "(Predavanja) postavljen kod. "
					+ "Molim da ovo primite k znanju. "
					+ "Lijep pozdrav", User.find(5), User.find(1));
			Message.create("Dragi svi "
					+ "u ime predavaca Arifa je na Google Classroom "
					+ "(Predavanja) postavljen kod. "
					+ "Molim da ovo primite k znanju. "
					+ "Lijep pozdrav", User.find(3), User.find(2));
			Message.create("Dragi svi "
					+ "u ime predavaca Arifa je na Google Classroom "
					+ "(Predavanja) postavljen kod. "
					+ "Molim da ovo primite k znanju. "
					+ "Lijep pozdrav", User.find(4), User.find(2));
			Message.create("Dragi svi "
					+ "u ime predavaca Arifa je na Google Classroom "
					+ "(Predavanja) postavljen kod. "
					+ "Molim da ovo primite k znanju. "
					+ "Lijep pozdrav", User.find(1), User.find(3));
			Message.create("Dragi svi "
					+ "u ime predavaca Arifa je na Google Classroom "
					+ "(Predavanja) postavljen kod. "
					+ "Molim da ovo primite k znanju. "
					+ "Lijep pozdrav", User.find(2), User.find(3));
			
		}

		if (Category.find(1) == null) {
			String categoryArray[] = { "Cars", "Fashion", "Mobile phones",
					"Computers", "Houses", "Shoes", "Biznis", "Animals" };

			for (int i = 0; i < categoryArray.length; i++) {
				Category.create(categoryArray[i]);
			}

		}

		if (Product.find(1) == null) {

			Product.create("Eminin proizvod", 2000, 1, User.find(3), "Neki opis", 3);
			Product pro = Product.find(1);
			Image img3 = new Image();
			img3.image = "images/bitbaySlika1.jpg";
			img3.product = pro;
			

			List<Image> imggg = new ArrayList<Image>();
			imggg.add(img3);
			Image.saveImg(img3);
			pro.category_id = 3;
			pro.images = imggg;

			
			
			Product.create("Mazda m5", 25000, 1, User.find(2),
					"condition:	used, " + "year: 2010, " + "make: Mazda, "
							+ "mileage: 49,831, " + "transmission: manual", 1);

			Product prod = Product.find(2);
			Image img4 = new Image();
			img4.image = "images/6800.jpg";
			img4.product = prod;

			List<Image> imgggg = new ArrayList<Image>();
			imgggg.add(img3);
			Image.saveImg(img4);
			prod.category_id = 3;
			prod.images = imgggg;

			
			
			Product.create("Galaxy S6", 2000, 15, User.find(3),
					"Detailed: item info, " + "Product: Identifiers, "
							+ "Brand: Samsung, " + "Carrier: Unlocked,"
							+ " Family Line: Samsung Galaxy S6,"
							+ " Type: Smartphone, " + "Digital Camera: Yes,"
							+ " Email Access: Yes,"
							+ " Battery Capacity: 2550 mAh,"
							+ " Display Technology:	Quad HD Super AMOLED", 3);

			Product produ = Product.find(3);
			Image img5 = new Image();
			img5.image = "images/samsung.jpg";
			img5.product = produ;

			List<Image> imggggg = new ArrayList<Image>();
			imggggg.add(img5);
			Image.saveImg(img5);
			produ.category_id = 3;
			produ.images = imggggg;

			
			
			Product.create(
					"House Stup",
					880000,
					1,
					User.find(3),
					"Imported Italian marble floors in the foyer, in the 2 1/2 "
					+ "baths and the 1st floor laundry room,Designed in the atmosphere "
					+ "of a Las Vegas Suite. Very ornate.  "
					+ "Three bedrooms upstairs. Ssiral staircase. ", 5);

			Product produc = Product.find(4);
			Image img6 = new Image();
			img6.image = "images/bitbaySlika1.jpg";
			img6.product = produc;

			List<Image> imgggggg = new ArrayList<Image>();
			imgggggg.add(img6);
			Image.saveImg(img6);
			produc.category_id = 3;
			produc.images = imgggggg;

			
			
			Product.create("Mercedes 270", 45750, 2, User.find(4),
					"year: 2015, " + "make: Mercedes, " + "condition:	used, "
							+ "mileage: 21,841, " + "transmission: manual", 1);

			Product product = Product.find(5);
			Image img7 = new Image();
			img7.image = "images/mercedes.jpg";
			img7.product = product;

			List<Image> imggggggg = new ArrayList<Image>();
			imggggggg.add(img7);
			Image.saveImg(img7);
			product.category_id = 3;
			product.images = imggggggg;

			
			
			Product.create("Dog", 120, 3, User.find(6),
					"Young dog, 3 monts, only money!!!", 8);

			Product product1 = Product.find(6);
			Image img8 = new Image();
			img8.image = "images/dog.jpg";
			img8.product = product1;

			List<Image> imgggggggg = new ArrayList<Image>();
			imgggggggg.add(img8);
			Image.saveImg(img8);
			product1.category_id = 3;
			product1.images = imgggggggg;

			
			
			Product.create("BMW 320", 15000, 1, User.find(4), "year: 2003, "
					+ "mileage: 129,274, " + "transmission: automatic"
					+ "make: Bmw, ", 1);

			Product product2 = Product.find(7);
			Image img9 = new Image();
			img9.image = "images/bmw.jpg";
			img9.product = product2;

			List<Image> imggggggggg = new ArrayList<Image>();
			imggggggggg.add(img9);
			Image.saveImg(img9);
			product2.category_id = 3;
			product2.images = imggggggggg;

			
			
			Product.create("Iphone 6", 1530, 15, User.find(5), "Brand: Apple,"
					+ "Storage Capacity: 64 GB, " + " Camera:	8.0MP,"
					+ " Model: iPhone 6 Plus," + "Digital Camera: Yes,"
					+ " Email Access: Yes," + " Battery Capacity: 2000 mAh,"
					+ " Operating System:iOS", 3);

			Product product3 = Product.find(8);
			Image img10 = new Image();
			img10.image = "images/iphone.jpg";
			img10.product = product3;

			List<Image> imgggggggggg = new ArrayList<Image>();
			imgggggggggg.add(img10);
			Image.saveImg(img10);
			product3.category_id = 3;
			product3.images = imgggggggggg;

			
			
			Product.create("Cat", 120, 1, User.find(6),
					"Young cat, 2 monts, if you want good pet this is the best choice!!!", 8);

			Product product4 = Product.find(9);
			Image img11 = new Image();
			img11.image = "images/cat.jpg";
			img11.product = product4;

			List<Image> imggggggggggg = new ArrayList<Image>();
			imggggggggggg.add(img11);
			Image.saveImg(img11);
			product4.category_id = 3;
			product4.images = imggggggggggg;

			
			
			Product.create("House", 100000, 3, User.find(2),
					"This house has got four bedrooms, a living-room, a dining-room, a kitchen", 5);

			Product product5 = Product.find(10);
			Image img12 = new Image();
			img12.image = "images/bitbaySlika1.jpg";
			img12.product = product5;

			List<Image> imgggggggggggg = new ArrayList<Image>();
			imgggggggggggg.add(img12);
			Image.saveImg(img12);
			product5.category_id = 3;
			product5.images = imgggggggggggg;

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
		if(Comment.find(1)==null){
			Comment.createComment("Hope to deal with you again. Thank you.",User.find(2),Product.find(1));
			Comment.createComment("Quick response and fast delivery. Perfect! THANKS!!",User.find(4),Product.find(1));
			Comment.createComment("Thank you for an easy, pleasant transaction. Excellent product. A++++++.",User.find(4),Product.find(2));
			Comment.createComment("Welcome your next purchase!Nice product!",User.find(3),Product.find(2));
			Comment.createComment("Good product, prompt delivery, valued seller, highly recommended.",User.find(6),Product.find(3));
			Comment.createComment("SUPER GREAT PRODUCT A+++ ANY ISSUE LET ME KNOW EMERGENT SOLUTION FOR YOU :).",User.find(2),Product.find(3));
			Comment.createComment("Thank you for an easy, pleasant transaction. Excellent product. A++++++.",User.find(4),Product.find(4));
			Comment.createComment("Good product, prompt delivery, valued seller, highly recommended.",User.find(6),Product.find(4));
			Comment.createComment("Quick response and fast delivery. Perfect! THANKS!!",User.find(3),Product.find(5));
			Comment.createComment("Good product, prompt delivery, valued seller, highly recommended.",User.find(2),Product.find(5));
			Comment.createComment("Great communication. A pleasure to do business with.",User.find(3),Product.find(6));
			Comment.createComment("Thank you for an easy, pleasant transaction. Excellent product A++++++",User.find(4),Product.find(6));
			Comment.createComment("Good product, prompt delivery, valued seller, highly recommended.",User.find(2),Product.find(7));
			Comment.createComment("Thank you a great product Call back anytime A+++++++",User.find(3),Product.find(7));
			Comment.createComment("Thank you a great product Call back anytime A+++++++",User.find(4),Product.find(8));
			Comment.createComment("Hope to deal with you again. Thank you.",User.find(3),Product.find(8));
			Comment.createComment("Thank you a great product Call back anytime A+++++++",User.find(2),Product.find(9));
			Comment.createComment("Hope to deal with you again. Thank you.",User.find(3),Product.find(9));
			Comment.createComment("Thank you a great product Call back anytime A+++++++",User.find(4),Product.find(10));
			Comment.createComment("Hope to deal with you again. Thank you.",User.find(6),Product.find(10));
		}

	}
}
