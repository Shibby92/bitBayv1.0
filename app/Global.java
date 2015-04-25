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

		if (User.find("mehnnbitbay@gmail.com") == null) {
			
			
			User u = new User("mehnnbitbay@gmail.com",
					HashHelper.createPassword("bitbayadmin"), true, true);
			u.user_address = "Admin Street 229 ";
			User.create(u);
			u.username = "Admin";
			u.hasAdditionalInfo = true;
			u.update();

			User u2 = new User("mustafa.ademovic93@gmail.com",
					HashHelper.createPassword("123456"), false, true);
			u2.user_address = "Admin Street 229 ";
			User.create(u2);
			u2.username = "Mustafa";
			u2.hasAdditionalInfo = true;
			u2.update();

			User u3 = new User("emina.muratovic@bitcamp.ba",
					HashHelper.createPassword("emina"), false, true);
			u3.user_address = "Admin Street 229 ";
			User.create(u3);
			u3.username = "Emina";
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
			u5.username="Hikmet";
			u5.hasAdditionalInfo=true;
			u5.update();

			User u6 = new User("nermin.vucinic@bitcamp.ba",
					HashHelper.createPassword("nermin"), false, true);
			User.create(u6);
			u6.user_address="Trebinjska 113";
			u6.username="Nermin";
			u6.hasAdditionalInfo=true;
			u6.update();

		}
		
		if(Message.find(1) == null) {
			
			Message.create("Dragi svi "
					+ "u ime predavaca Arifa je na Google Classroom "
					+ "(Predavanja) postavljen kod. "
					+ "Molim da ovo primite k znanju. "
					+ "Lijep pozdrav", User.find(2), User.find(1), "Contact Us message!");
			Message.create("Dragi svi "
					+ "u ime predavaca Arifa je na Google Classroom "
					+ "(Predavanja) postavljen kod. "
					+ "Molim da ovo primite k znanju. "
					+ "Lijep pozdrav", User.find(3), User.find(1), "Contact Us message!");
			Message.create("Dragi svi "
					+ "u ime predavaca Arifa je na Google Classroom "
					+ "(Predavanja) postavljen kod. "
					+ "Molim da ovo primite k znanju. "
					+ "Lijep pozdrav", User.find(4), User.find(1), "Contact Us message!");
			Message.create("Dragi svi "
					+ "u ime predavaca Arifa je na Google Classroom "
					+ "(Predavanja) postavljen kod. "
					+ "Molim da ovo primite k znanju. "
					+ "Lijep pozdrav", User.find(5), User.find(1), "Contact Us message!");
			Message.create("Dragi svi "
					+ "u ime predavaca Arifa je na Google Classroom "
					+ "(Predavanja) postavljen kod. "
					+ "Molim da ovo primite k znanju. "
					+ "Lijep pozdrav", User.find(3), User.find(2), "Contact Us message!");
			Message.create("Dragi svi "
					+ "u ime predavaca Arifa je na Google Classroom "
					+ "(Predavanja) postavljen kod. "
					+ "Molim da ovo primite k znanju. "
					+ "Lijep pozdrav", User.find(4), User.find(2), "Contact Us message!");
			Message.create("Dragi svi "
					+ "u ime predavaca Arifa je na Google Classroom "
					+ "(Predavanja) postavljen kod. "
					+ "Molim da ovo primite k znanju. "
					+ "Lijep pozdrav", User.find(1), User.find(3), "Contact Us message!");
			Message.create("Dragi svi "
					+ "u ime predavaca Arifa je na Google Classroom "
					+ "(Predavanja) postavljen kod. "
					+ "Molim da ovo primite k znanju. "
					+ "Lijep pozdrav", User.find(2), User.find(3), "Contact Us message!");
			
			
		}

		if (Category.find(1) == null) {
			String categoryArray[] = { "Cars", "Fashion", "Mobile phones",
					"Computers", "Houses", "Shoes", "Biznis", "Animals" };

			for (int i = 0; i < categoryArray.length; i++) {
				Category.create(categoryArray[i]);
			}

		}

		if (Product.find(1) == null) {

			Product.create("Eminin proizvod", 2000, 1, User.find(3), "Neki opis", 5);
			Product pro = Product.find(1);
			Image img3 = new Image();
			img3.image = "images/bitbaySlika1.jpg";
			img3.product = pro;
			Tag.create(pro, Category.find(pro.category_id).name);
			Tag.create(pro, pro.name);
			List<Image> imggg = new ArrayList<Image>();
			imggg.add(img3);
			Image.saveImg(img3);
			pro.images = imggg;
			pro.update();

			
			
			Product.create("Mazda m5", 25000, 1, User.find(2),
					"condition:	used, " + "year: 2010, " + "make: Mazda, "
							+ "mileage: 49,831, " + "transmission: manual", 1);

			Product prod = Product.find(2);
			Image img4 = new Image();
			img4.image = "images/6800.jpg";
			img4.product = prod;
			Tag.create(prod, Category.find(prod.category_id).name);
			Tag.create(prod, prod.name);
			List<Image> imgggg = new ArrayList<Image>();
			imgggg.add(img3);
			Image.saveImg(img4);
			prod.category_id = 3;
			prod.images = imgggg;

			
			
			Product.create("Galaxy S6", 2000.00, 15, User.find(3),
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
			Tag.create(produ, Category.find(produ.category_id).name);
			Tag.create(produ, produ.name);
			Tag.create(produ,"Samsung");
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
			Tag.create(produc, Category.find(produc.category_id).name);
			Tag.create(produc, produc.name);
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
			Tag.create(product, Category.find(product.category_id).name);
			Tag.create(product, product.name);
			List<Image> imggggggg = new ArrayList<Image>();
			imggggggg.add(img7);
			Image.saveImg(img7);
			product.category_id = 3;
			product.images = imggggggg;

			
			
			Product.create("Dog", 120, 3, User.find(6),
					"Young dog, 3 months, only money!!!", 8);

			Product product1 = Product.find(6);
			Image img8 = new Image();
			img8.image = "images/dog.jpg";
			img8.product = product1;
			Tag.create(product1, Category.find(product1.category_id).name);
			Tag.create(product1, product1.name);
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
			Tag.create(product2, Category.find(product2.category_id).name);
			Tag.create(product2, product2.name);
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
			Tag.create(product3, Category.find(product3.category_id).name);
			Tag.create(product3, product3.name);
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
			Tag.create(product4, Category.find(product4.category_id).name);
			Tag.create(product4, product4.name);
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
			Tag.create(product5, Category.find(product5.category_id).name);
			Tag.create(product5, product5.name);
			List<Image> imgggggggggggg = new ArrayList<Image>();
			imgggggggggggg.add(img12);
			Image.saveImg(img12);
			product5.category_id = 3;
			product5.images = imgggggggggggg;
			
			Product.create("Samsung galaxy S6 cover",9.99,50,User.find(3),"Silicon cover for Samsung galaxy S6",3);
			Product product6 =Product.find(11);
			Image img13= new Image();
			img13.image="images/samsungCover.jpg";
			img13.product=product6;
			Tag.create(product6, Category.find(product6.category_id).name);
			Tag.create(product6, product6.name);
			Tag.create(product6, "Samsung");
			List<Image> image1= new ArrayList<Image>();
			image1.add(img13);
			Image.saveImg(img13);
			
			//Product id 12
			Product.create("Headphones for Samsung galaxy S6 ",19.99,100,User.find(3),"Very stylish headphones for Samsung galaxy S6",3);
			Product product7 =Product.find(12);
			Image img14= new Image();
			img14.image="images/samsungHeadphones.jpg";
			img14.product=product7;
			Tag.create(product7, Category.find(product7.category_id).name);
			Tag.create(product7, product7.name);
			Tag.create(product7, "Samsung");
			List<Image> image2= new ArrayList<Image>();
			image2.add(img14);
			Image.saveImg(img14);
			
			Product.create("Dock for Samsung galaxy S6 ",29.99,10,User.find(3),"Beatiful all-in-one dock for the new Samsung galaxy S6",3);
			Product product8 =Product.find(13);
			Image img15= new Image();
			img15.image="images/samsungDock.jpg";
			img15.product=product8;
			Tag.create(product8, Category.find(product8.category_id).name);
			Tag.create(product8, product8.name);
			Tag.create(product8, "Samsung");
			List<Image> image3= new ArrayList<Image>();
			image3.add(img15);
			Image.saveImg(img15);
			
			Product.create("Dog chew rope", 5.55, 15, User.find(6),"A toy for your best friend!", 8);
			Product product9 = Product.find(14);
			Image img16 = new Image();
			img16.image = "images/dogChewRope.jpg";
			img16.product = product9;
			Tag.create(product9, Category.find(product9.category_id).name);
			Tag.create(product9, product9.name);
			Tag.create(product9,"Dog");
			List<Image> image4 = new ArrayList<Image>();
			image4.add(img16);
			Image.saveImg(img16);
			
			Product.create("Dog collar", 9.69, 20, User.find(6),"New red plaid stylish dog collar.", 8);
			Product product10 = Product.find(15);
			Image img17 = new Image();
			img17.image = "images/dogCollar.jpg";
			img17.product = product10;
			Tag.create(product10, Category.find(product10.category_id).name);
			Tag.create(product10, product10.name);
			Tag.create(product10,"Dog");
			List<Image> image5 = new ArrayList<Image>();
			image5.add(img17);
			Image.saveImg(img17);
			
			Product.create("Treatball", 21.29, 5, User.find(6),"Mystery green rubber treatball for your dog!", 8);
			Product product11 = Product.find(16);
			Image img18 = new Image();
			img18.image = "images/dogTreatball.jpg";
			img18.product = product11;
			Tag.create(product11, Category.find(product11.category_id).name);
			Tag.create(product11, product11.name);
			Tag.create(product11,"Dog");
			List<Image> image6 = new ArrayList<Image>();
			image6.add(img18);
			Image.saveImg(img18);
			

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
		if(Orders.find(1)==null){
			Cart c1=Cart.find(6);
			Cart.addProduct(Product.find(12), c1);
			Cart.addProduct(Product.find(3), c1);
			Orders.create(new Orders(c1,User.find(6),"test"));
		}
		
		if(Blog.findBlogById(1) == null) {
			Blog.createBlog("BitBay started", 
					"A new website for online shopping has started to work today. We will be at your service 24/7 and provide top service"
					+ "to all our users. Our primary conceirn is safety for our users so they can buy, sell and trade with out any worry"
					+ "for security."
					+ "We really hope you will find our website fun and intuitive to use."
					+ "Kind Regards BitBay team.", 
					"images/logo.png");
		}

	}
}
