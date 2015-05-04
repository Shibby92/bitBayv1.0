import helpers.HashHelper;

import java.util.ArrayList;
import java.util.List;

import models.Blog;
import models.Category;
import models.Comment;
import models.FAQ;
import models.Image;
import models.Message;
import models.Product;
import models.Report;
import models.Tag;
import models.User;
import play.Application;
import play.GlobalSettings;

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
			
			User u7 = new User("blogger@bitcamp.ba", 
					HashHelper.createPassword("blogger"), false, true);
			User.create(u7);
			u7.user_address = "Bloggers St. 72";
			u7.username = "Blogger";
			u7.hasAdditionalInfo = true;
			u7.blogger = true;
			u7.update();

		}
		
		if(Message.find(1) == null) {
			
			Message.create("Dragi svi "
					+ "u ime predavaca Arifa je na Google Classroom "
					+ "(Predavanja) postavljen kod. "
					+ "Molim da ovo primite k znanju. "
					+ "Lijep pozdrav", User.find(2), User.find(1), "Contact Seller message!");
			Message.create("Dragi svi "
					+ "u ime predavaca Arifa je na Google Classroom "
					+ "(Predavanja) postavljen kod. "
					+ "Molim da ovo primite k znanju. "
					+ "Lijep pozdrav", User.find(3), User.find(1), "Contact Seller message!");
			Message.create("Dragi svi "
					+ "u ime predavaca Arifa je na Google Classroom "
					+ "(Predavanja) postavljen kod. "
					+ "Molim da ovo primite k znanju. "
					+ "Lijep pozdrav", User.find(4), User.find(1), "Contact Seller message!");
			Message.create("Dragi svi "
					+ "u ime predavaca Arifa je na Google Classroom "
					+ "(Predavanja) postavljen kod. "
					+ "Molim da ovo primite k znanju. "
					+ "Lijep pozdrav", User.find(5), User.find(1), "Contact Seller message!");
			Message.create("Dragi svi "
					+ "u ime predavaca Arifa je na Google Classroom "
					+ "(Predavanja) postavljen kod. "
					+ "Molim da ovo primite k znanju. "
					+ "Lijep pozdrav", User.find(3), User.find(2), "Contact Seller message!");
			Message.create("Dragi svi "
					+ "u ime predavaca Arifa je na Google Classroom "
					+ "(Predavanja) postavljen kod. "
					+ "Molim da ovo primite k znanju. "
					+ "Lijep pozdrav", User.find(4), User.find(2), "Contact Seller message!");
			Message.create("Dragi svi "
					+ "u ime predavaca Arifa je na Google Classroom "
					+ "(Predavanja) postavljen kod. "
					+ "Molim da ovo primite k znanju. "
					+ "Lijep pozdrav", User.find(1), User.find(3), "Contact Seller message!");
			Message.create("Dragi svi "
					+ "u ime predavaca Arifa je na Google Classroom "
					+ "(Predavanja) postavljen kod. "
					+ "Molim da ovo primite k znanju. "
					+ "Lijep pozdrav", User.find(2), User.find(3), "Contact Seller message!");
			
			
		}

		if (Category.find(1) == null) {
			String categoryArray[] = { "Motors", "Fashion", "Smartphones",
					"Electronics", "Houses", "Collectibles", "Business", "Animals"
					, "Antiques", "Sports", "Music", "Garden", "Toys", "Books", "Health & Beauty" };

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
			
			
			Product.create("NEW Authentic GUCCI", 552.49, 7, User.find(3), "New with tags: A brand-new, "
					+ "unused, and unworn item (including handmade items) in the original "
					+ "packaging ", 2);
			Product product12 = Product.find(17);
			Image gucci = new Image();
			gucci.image = "images/GucciBag.JPG";
			gucci.product = product12;
			Image gucci1 = new Image();
			gucci1.image = "images/guccibag2.jpg";
			gucci1.product = product12;
			Image gucci2 = new Image();
			gucci2.image = "images/guccibag3.jpg";
			gucci2.product = product12;
			Image gucci3 = new Image();
			gucci3.image = "images/guccibag4.jpg";
			gucci3.product = product12;
			Image gucci4 = new Image();
			gucci4.image = "images/guccibag5.jpg";
			gucci4.product = product12;
			Tag.create(product12, Category.find(product12.category_id).name);
			Tag.create(product12, product12.name);
			Tag.create(product12,"Dog");
			List<Image> image7 = new ArrayList<Image>();
			image7.add(gucci);
			Image.saveImg(gucci);
			image7.add(gucci1);
			Image.saveImg(gucci1);
			image7.add(gucci2);
			Image.saveImg(gucci2);
			image7.add(gucci3);
			Image.saveImg(gucci3);
			image7.add(gucci4);
			Image.saveImg(gucci4);
			
			
			Product.create("Raymond Stainless Watch", 399.00, 3, User.find(4), "This watch is new display model. "
					+ "Does not come with box and papers. ", 2);
			Product product13 = Product.find(18);
			Image raymond = new Image();
			raymond.image = "images/Raymondwatch.JPG";
			raymond.product = product13;
			Image raymond1 = new Image();
			raymond1.image = "images/Raymondwatch1.JPG";
			raymond1.product = product13;
			Image raymond2 = new Image();
			raymond2.image = "images/Raymondwatch2.JPG";
			raymond2.product = product13;
			Image raymond3 = new Image();
			raymond3.image = "images/Raymondwatch3.JPG";
			raymond3.product = product13;
			Tag.create(product13, Category.find(product13.category_id).name);
			Tag.create(product13, product13.name);
			Tag.create(product13,"Dog");
			List<Image> image8 = new ArrayList<Image>();
			image8.add(raymond);
			Image.saveImg(raymond);
			image8.add(raymond1);
			Image.saveImg(raymond1);
			image8.add(raymond2);
			Image.saveImg(raymond2);
			image8.add(raymond3);
			Image.saveImg(raymond3);
			
			Product product14 = Product.create("Michael KORS BUCKLE SANDALS", 99.00, 7, User.find(5), "New with box: A brand-new, unused, and unworn item (including handmade items) "
					+ "in the original packaging (such as the original box or bag) "
					+ "and/or with the original tags attached. ", 2 );
			
			Image sandals = new Image();
			sandals.image = "images/michaelkors.JPG";
			sandals.product = product14;
			Image sandals1 = new Image();
			sandals1.image = "images/michaelkors1.jpeg";
			sandals1.product = product14;
			Image sandals2 = new Image();
			sandals2.image = "images/michaelkors2.jpeg";
			sandals2.product = product14;
			Tag.create(product14, Category.find(product14.category_id).name);
			Tag.create(product14, product14.name);
			Tag.create(product14,"Dog");
			List<Image> image9 = new ArrayList<Image>();
			image9.add(sandals);
			Image.saveImg(sandals);
			image9.add(sandals1);
			Image.saveImg(sandals1);
			image9.add(sandals2);
			Image.saveImg(sandals2);
			
			
			Product product15 = Product.create("Tory Burch Thong Sandals", 119.00, 10, User.find(6), "New without box: A brand-new, unused, and unworn item (including handmade items) "
					+ "that is not in original packaging or may be missing original packaging materials (such as the original box or bag). ", 2);
			
			Image tory = new Image();
			tory.image = "images/toryburch.JPG";
			tory.product = product15;
			Image tory1 = new Image();
			tory1.image = "images/toryburch1.jpeg";
			tory1.product = product15;
			Image tory2 = new Image();
			tory2.image = "images/toryburch2.jpeg";
			tory2.product = product15;
			Image tory3 = new Image();
			tory3.image = "images/toryburch3.jpeg";
			tory3.product = product15;
			Image tory4 = new Image();
			tory4.image = "images/toryburch4.jpeg";
			tory4.product = product15;
			Tag.create(product15, Category.find(product15.category_id).name);
			Tag.create(product15, product15.name);
			Tag.create(product15,"Dog");
			List<Image> image10 = new ArrayList<Image>();
			image10.add(tory);
			Image.saveImg(tory);
			image10.add(tory1);
			Image.saveImg(tory1);
			image10.add(tory2);
			Image.saveImg(tory2);
			image10.add(tory3);
			Image.saveImg(tory3);
			image10.add(tory4);
			Image.saveImg(tory4);
			
			Product product16 = Product.create("U2: son gs of innocence- Deluxe Edition (2014)", 5.50, 5, User.find(2), "Brand New: An item that has never been opened or removed from the manufacturer’s sealing (if applicable). "
					+ "Item is in original shrink wrap (if applicable). See the seller's listing for full details. Number of Discs:	2."
					+ "Genre:	Rock. Tracks:  16.", 11);
			
			Image u2 = new Image();
			u2.image = "images/u2CD.JPG";
			u2.product = product16;
			Image u23 = new Image();
			u23.image = "images/u2CD1.JPG";
			u23.product = product16;
			Tag.create(product16, Category.find(product16.category_id).name);
			Tag.create(product16, product16.name);
			Tag.create(product16,"Dog");
			List<Image> image11 = new ArrayList<Image>();
			image11.add(u2);
			Image.saveImg(u2);
			image11.add(u23);
			Image.saveImg(u23);
			
			
			Product product17 = Product.create("Vintage Look Vase Coffee TABLE", 55.00, 1, User.find(3), "Good condition, Hand Made. Region of Origin:	Asia. Material:	Wood. Age:	Post-1950", 9);
			
			Image table = new Image();
			table.image = "images/coffeetable.JPG";
			table.product = product17;
			Image table1 = new Image();
			table1.image = "images/coffeetable1.JPG";
			table1.product = product17;
			Image table2 = new Image();
			table2.image = "images/coffeetable2.JPG";
			table2.product = product17;
			Tag.create(product17, Category.find(product17.category_id).name);
			Tag.create(product17, product17.name);
			Tag.create(product17,"Dog");
			List<Image> image12 = new ArrayList<Image>();
			image12.add(table);
			Image.saveImg(table);
			image12.add(table1);
			Image.saveImg(table1);
			image12.add(table2);
			Image.saveImg(table2);
			
			Product product18 = Product.create("GIANT BIG TEDDY BEAR", 17.99, 20, User.find(4), "New: A brand-new, unused, unopened, undamaged item (including handmade items)"
			+ "Country of Manufacture:	China. Size:	75cm/29.5 inch(Ear to Feet)", 13);
			
			Image bear = new Image();
			bear.image = "images/tedybear.JPG";
			bear.product = product18;
			Image bear1 = new Image();
			bear1.image = "images/tedybear1.JPG";
			bear1.product = product18;
			Image bear2 = new Image();
			bear2.image = "images/tedybear2.JPG";
			bear2.product = product16;
			Tag.create(product18, Category.find(product18.category_id).name);
			Tag.create(product18, product18.name);
			Tag.create(product18,"Dog");
			List<Image> image13 = new ArrayList<Image>();
			image13.add(bear);
			Image.saveImg(bear);
			image13.add(bear1);
			Image.saveImg(bear1);
			image13.add(bear2);
			Image.saveImg(bear2);
			
			

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
		
		if(Blog.findBlogById(1) == null) {
			Blog.createBlog("BitBay started", 
					"A new website for online shopping has started to work today. We will be at your service 24/7 and provide top service"
					+ "to all our users. Our primary conceirn is safety for our users so they can buy, sell and trade with out any worry"
					+ "for security."
					+ "We really hope you will find our website fun and intuitive to use."
					+ "Kind Regards BitBay team.", 
					"images/logo.png", 1, "Admin");
		}
		
		if(Report.find(1) == null) {
			Report.report(Product.find(1), User.find(2), "Report for product 1 from user with id 2.");
			Report.report(Product.find(1), User.find(3), "Report for product 1 from user with id 3.");
			Report.report(Product.find(2), User.find(4), "Report for product 2 from user with id 4.");
			Report.report(Product.find(2), User.find(5), "Report for product 2 from user with id 5.");
			Report.report(Product.find(2), User.find(6), "Report for product 2 from user with id 6.");
			Report.report(Product.find(3), User.find(2), "Report for product 3 from user with id 2.");
			Report.report(Product.find(4), User.find(3), "Report for product 4 from user with id 3.");
			Report.report(Product.find(4), User.find(4), "Report for product 4 from user with id 4.");
			Report.report(Product.find(4), User.find(5), "Report for product 4 from user with id 5.");
			Report.report(Product.find(4), User.find(6), "Report for product 4 from user with id 6.");
		}

	}
}
