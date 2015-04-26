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
			u.rating = 5.0;
			u.update();

			User u2 = new User("mustafa.ademovic93@gmail.com",
					HashHelper.createPassword("123456"), false, true);
			u2.user_address = "Admin Street 229 ";
			User.create(u2);
			u2.username = "Mustafa";
			u2.hasAdditionalInfo = true;
			u2.rating = 4.5;
			u2.update();

			User u3 = new User("emina.muratovic@bitcamp.ba",
					HashHelper.createPassword("emina"), false, true);
			u3.user_address = "Admin Street 229 ";
			User.create(u3);
			u3.username = "Emina";
			u3.hasAdditionalInfo = true;
			u3.rating = 4;
			u3.update();

			User u4 = new User("haris.arifovic@bitcamp.ba",
					HashHelper.createPassword("haris"), false, true);
			u4.user_address="Lozionicka 2";
			User.create(u4);
			u4.username="Haris";
			u4.hasAdditionalInfo=true;
			u4.rating = 3.5;
			u4.update();

			User u5 = new User("hikmet.durgutovic@bitcamp.ba",
					HashHelper.createPassword("hikmet"), false, true);
			User.create(u5);
			u5.username="Hikmet";
			u5.hasAdditionalInfo=true;
			u5.rating = 3.0;
			u5.update();

			User u6 = new User("nermin.vucinic@bitcamp.ba",
					HashHelper.createPassword("nermin"), false, true);
			u6.user_address="Trebinjska 113";
			User.create(u6);
			u6.username="Nermin";
			u6.hasAdditionalInfo=true;
			u6.rating = 2.5;
			u6.update();
			
			User u7 = new User("blogger@bitcamp.ba", 
					HashHelper.createPassword("blogger"), false, true);
			User.create(u7);
			u7.user_address = "Bloggers St. 72";
			u7.username = "Blogger";
			u7.hasAdditionalInfo = true;
			u7.blogger = true;
			u7.rating = 2.0;
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

			Product pro = Product.create("POST CARD of H.M.C.S.ASSINIBOINE", 18.00, 1, User.find(3),
					"Used: An item that has been used previously. WW2 Photo Post Card of HMCS Assin.", 5);
			
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

			
			
			Product prod = Product.create("2014 Mazda CX-5 4DR SUV", 24900.00, 1, User.find(2),
					"condition:	used; " + "year: 2014; " + "Make: Mazda; "
							+ "mileage: 12,831; " + "Transmission: Automatic;"
									+ "	Fuel Type:	Gas", 1);

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

			
			
			Product produ = Product.create("Galaxy S6", 2000.00, 15, User.find(3),
					"Detailed: item info, " + "Product: Identifiers, "
							+ "Brand: Samsung, " + "Carrier: Unlocked,"
							+ " Family Line: Samsung Galaxy S6,"
							+ " Type: Smartphone, " + "Digital Camera: Yes,"
							+ " Email Access: Yes,"
							+ " Battery Capacity: 2550 mAh,"
							+ " Display Technology:	Quad HD Super AMOLED", 3);

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

			
			
			Product produc = Product.create(
					"THE BOOK OF WHO? by Rodney Dale",
					0.99,
					1,
					User.find(3),
					"Brand New: A new, unread, unused book in perfect condition with no missing or damaged pages."
					+ "Publication Year:	2005", 13);

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

			
			
			Product product = Product.create("Mercedes 270", 45750, 2, User.find(4),
					"Year: 2015, " + "Make: Mercedes; " + "Condition: used; "
							+ "Mileage: 21,841; " + "Transmission: manual", 1);

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

			
			
			Product product1 = Product.create("Dog", 120, 3, User.find(6),
					"Young dog, 3 months, only money!!!", 8);

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

			
			
			Product product2 = Product.create("BMW 320", 15000, 1, User.find(4), "Year: 2003; "
					+ "Mileage: 129,27; " + " Transmission: automatic;"
					+ " Make: BMW ", 1);

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

			
			
			Product product3 = Product.create("Iphone 6", 1530, 15, User.find(5), "Brand: Apple,"
					+ "Storage Capacity: 64 GB, " + " Camera:	8.0MP,"
					+ " Model: iPhone 6 Plus," + "Digital Camera: Yes,"
					+ " Email Access: Yes," + " Battery Capacity: 2000 mAh,"
					+ " Operating System:iOS", 3);

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

			
			
			Product product4 = Product.create("Cat", 120, 1, User.find(6),
					"Young cat, 2 monts, if you want good pet this is the best choice!!!", 8);

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

			
			
			Product product5 = Product.create("House", 100000, 3, User.find(2),
					"This house has got four bedrooms, a living-room, a dining-room, a kitchen", 5);

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
			
			Product product6 =Product.create("Samsung galaxy S6 cover",9.99,50,User.find(3),"Silicon cover for Samsung galaxy S6",3);
			
			Image img13= new Image();
			img13.image="images/samsungCover.jpg";
			img13.product=product6;
			Tag.create(product6, Category.find(product6.category_id).name);
			Tag.create(product6, product6.name);
			Tag.create(product6, "Samsung");
			List<Image> image1= new ArrayList<Image>();
			image1.add(img13);
			Image.saveImg(img13);
			
			
			Product product7 = Product.create("Headphones for Samsung galaxy S6 ",19.99,100,User.find(3),"Very stylish headphones for Samsung galaxy S6",3);
			
			Image img14= new Image();
			img14.image="images/samsungHeadphones.jpg";
			img14.product=product7;
			Tag.create(product7, Category.find(product7.category_id).name);
			Tag.create(product7, product7.name);
			Tag.create(product7, "Samsung");
			List<Image> image2= new ArrayList<Image>();
			image2.add(img14);
			Image.saveImg(img14);
			
			Product product8 = Product.create("Dock for Samsung galaxy S6 ",29.99,10,User.find(3),"Beatiful all-in-one dock for the new Samsung galaxy S6",3);
		
			Image img15= new Image();
			img15.image="images/samsungDock.jpg";
			img15.product=product8;
			Tag.create(product8, Category.find(product8.category_id).name);
			Tag.create(product8, product8.name);
			Tag.create(product8, "Samsung");
			List<Image> image3= new ArrayList<Image>();
			image3.add(img15);
			Image.saveImg(img15);
			
			Product product9 = Product.create("Dog chew rope", 5.55, 15, User.find(6),"A toy for your best friend!", 8);
			
			Image img16 = new Image();
			img16.image = "images/dogChewRope.jpg";
			img16.product = product9;
			Tag.create(product9, Category.find(product9.category_id).name);
			Tag.create(product9, product9.name);
			Tag.create(product9,"Dog");
			List<Image> image4 = new ArrayList<Image>();
			image4.add(img16);
			Image.saveImg(img16);
			
			Product product10 = Product.create("Dog collar", 9.69, 20, User.find(6),"New red plaid stylish dog collar.", 8);
			
			Image img17 = new Image();
			img17.image = "images/dogCollar.jpg";
			img17.product = product10;
			Tag.create(product10, Category.find(product10.category_id).name);
			Tag.create(product10, product10.name);
			Tag.create(product10,"Dog");
			List<Image> image5 = new ArrayList<Image>();
			image5.add(img17);
			Image.saveImg(img17);
			
			Product product11 = Product.create("Treatball", 21.29, 5, User.find(6),"Mystery green rubber treatball for your dog!", 8);
		
			Image img18 = new Image();
			img18.image = "images/dogTreatball.jpg";
			img18.product = product11;
			Tag.create(product11, Category.find(product11.category_id).name);
			Tag.create(product11, product11.name);
			Tag.create(product11,"Dog");
			List<Image> image6 = new ArrayList<Image>();
			image6.add(img18);
			Image.saveImg(img18);
			
			
			Product product12 = Product.create("NEW Authentic GUCCI", 552.49, 7, User.find(3), "New with tags: A brand-new, "
					+ "unused, and unworn item (including handmade items) in the original "
					+ "packaging ", 2);
		
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
			
			
			Product product13 = Product.create("Raymond Stainless Watch", 399.00, 3, User.find(4), "This watch is new display model. "
					+ "Does not come with box and papers. ", 2);
			
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
			
			Product product19 = Product
					.create("NOKIA 3310",
							20.99,
							20,
							User.find(6),
							"This Nokia mobile phone, with its LCD display, lets you view the message clearly."
									+ "The battery allows you to talk for 2.5 hours on a single charge.",
							3);

			Image nokia = new Image();
			nokia.image = "images/nokia.3310.jpg";
			nokia.product = product19;
			Image nokia3310_2 = new Image();
			nokia3310_2.image = "images/nokia3310.2.JPG";
			nokia3310_2.product = product19;
			Image nokia3310_3 = new Image();
			nokia3310_3.image = "images/nokia3310.3.JPG";
			nokia3310_3.product = product19;
			Tag.create(product19, Category.find(product19.category_id).name);
			Tag.create(product19, product19.name);
			Tag.create(product19, "Nokia");
			List<Image> imageList14 = new ArrayList<Image>();
			imageList14.add(nokia);
			Image.saveImg(nokia);
			imageList14.add(nokia3310_2);
			Image.saveImg(nokia3310_2);
			imageList14.add(nokia3310_3);
			Image.saveImg(nokia3310_3);
			product19.category_id = 3;
			product19.images = imggg;

			Product product20 = Product
					.create("Dell Optiplex Desktop Computer PC",
							120.99,
							20,
							User.find(6),
							"Computer comes with new install of licensed Windows 8.1 Pro 64-bit with Media Center + licensed Office 2013 Pro Plus SP1"
									+ " Computer professionally refurbished and tested 100% functional",
							4);

			Image dellPc = new Image();
			dellPc.image = "images/dellPc.jpg";
			dellPc.product = product20;
			Image dellPc2 = new Image();
			dellPc2.image = "images/dellPc2.JPG";
			dellPc2.product = product20;
			Image dellPc3 = new Image();
			dellPc3.image = "images/dellPc3.JPG";
			dellPc3.product = product20;
			Tag.create(product20, Category.find(product20.category_id).name);
			Tag.create(product20, product20.name);
			Tag.create(product20, "Dell");
			List<Image> imageList15 = new ArrayList<Image>();
			imageList15.add(dellPc);
			Image.saveImg(dellPc);
			imageList15.add(dellPc2);
			Image.saveImg(dellPc2);
			imageList15.add(dellPc3);
			Image.saveImg(dellPc3);
			product20.category_id = 4;
			product20.images = imggg;

			Product product21 = Product
					.create("THE ROSY CROSS ITS TEACHINGS 1ST EDITION ",
							179.99,
							20,
							User.find(6),
							"THE MODERN MANIFESTOES ISSUED BY THE BROTHERHOOD, ORDER, TEMPLE AND FRATERNITY OF THE ROSY CROSS; I.E. "
									+ "FOR THE GUIDANCE OF THOSE SEEKING A MORE PERFECT {BALANCED} LIFE",
							14);

			Image redCross = new Image();
			redCross.image = "images/redCross.JPG";
			redCross.product = product21;
			Image redCross2 = new Image();
			redCross2.image = "images/redCross2.JPG";
			redCross2.product = product21;
			Image redCross3 = new Image();
			redCross3.image = "images/redCross3.JPG";
			redCross3.product = product21;
			Tag.create(product21, Category.find(product21.category_id).name);
			Tag.create(product21, product21.name);
			Tag.create(product21, "Book");
			List<Image> imageList16 = new ArrayList<Image>();
			imageList16.add(redCross);
			Image.saveImg(redCross);
			imageList16.add(redCross2);
			Image.saveImg(redCross2);
			imageList16.add(redCross3);
			Image.saveImg(redCross3);
			product21.category_id = 14;
			product21.images = imggg;

			Product product22 = Product
					.create("Crafting and Executing Strategy",
							30.99,
							20,
							User.find(6),
							" Brand New, Well Wrapped, **ISE** International Student Edition Book. Soft Cover, Paper Back and written in English",
							14);

			Image strategyBook = new Image();
			strategyBook.image = "images/bookStrategy.JPG";
			strategyBook.product = product22;
			Image strategyBook2 = new Image();
			strategyBook2.image = "images/bookStrategy2.JPG";
			strategyBook2.product = product22;
			Image strategyBook3 = new Image();
			strategyBook3.image = "images/bookStrategy3.JPG";
			strategyBook3.product = product22;
			Tag.create(product22, Category.find(product22.category_id).name);
			Tag.create(product22, product22.name);
			Tag.create(product22, "Strategy");
			List<Image> imageList17 = new ArrayList<Image>();
			imageList17.add(strategyBook);
			Image.saveImg(strategyBook);
			imageList17.add(strategyBook2);
			Image.saveImg(strategyBook2);
			imageList17.add(strategyBook3);
			Image.saveImg(strategyBook3);
			product22.category_id = 14;
			product22.images = imggg;
			
			Product product23 = Product
					.create("ZOOT ULTRA SPEED",
							50.99,
							20,
							User.find(6),
							" Beim Ultra Speed hat Zoot sein ganzes Wissen über Materialien und Kompression eingesetzt, um einen Laufschuh zu bauen, "
							+ "der es möglich macht den Wechsel in Rekordzeit zu absolvieren. Dieser Triathlonschuh wurde für geschmeidige,",10);

			Image shoes = new Image();
			shoes.image = "images/shoes.JPG";
			shoes.product = product23;
			Image shoes2 = new Image();
			shoes2.image = "images/shoes2.JPG";
			shoes2.product = product23;
			Image shoes3 = new Image();
			shoes3.image = "images/shoes3.JPG";
			shoes3.product = product23;
			Tag.create(product23, Category.find(product23.category_id).name);
			Tag.create(product23, product23.name);
			Tag.create(product23, "ZOOT");
			List<Image> imageList18 = new ArrayList<Image>();
			imageList18.add(shoes);
			Image.saveImg(shoes);
			imageList18.add(shoes2);
			Image.saveImg(shoes2);
			imageList18.add(shoes3);
			Image.saveImg(shoes3);
			product23.category_id = 10;
			product23.images = imggg;

	

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
			Orders.create(new Orders(c1,User.find(6),"testnera"),2019.99);
			new ProductQuantity(Product.find(12).id, 1,Orders.find(1)).save();
			new ProductQuantity(Product.find(3).id, 1,Orders.find(1)).save();
			Cart.clear(6);
			
			Cart c2=Cart.find(3);
			Cart.addProduct(Product.find(2), c2);
			Cart.addProduct(Product.find(6), c2);
			Cart.addProduct(Product.find(14), c2);
			Orders.create(new Orders(c2,User.find(3),"testemina"),25125.55);
			new ProductQuantity(Product.find(2).id, 1,Orders.find(2)).save();
			new ProductQuantity(Product.find(6).id, 1,Orders.find(2)).save();
			new ProductQuantity(Product.find(14).id, 1,Orders.find(2)).save();
			Cart.clear(3);
			
			Cart c3=Cart.find(2);
			Cart.addProduct(Product.find(13), c3);
			Cart.addProduct(Product.find(3), c3);
			Orders.create(new Orders(c3,User.find(2),"testmustafa"),2029.99);
			new ProductQuantity(Product.find(13).id, 1,Orders.find(3)).save();
			new ProductQuantity(Product.find(3).id, 1,Orders.find(3)).save();
			Cart.clear(2);
			
			Cart c4=Cart.find(3);
			Cart.addProduct(Product.find(6), c4);
			Cart.addProduct(Product.find(15), c4);
			Orders.create(new Orders(c4,User.find(3),"testemina"),129.69);
			new ProductQuantity(Product.find(6).id, 1,Orders.find(4)).save();
			new ProductQuantity(Product.find(15).id, 1,Orders.find(4)).save();
			Cart.clear(3);
			
		}
		
		if(Blog.findBlogById(1) == null) {
			Blog.createBlog("bitBay started", 
					"A new website for online shopping has started to work today. We will be at your service 24/7 and provide top service "
					+ "to all our users. Our primary conceirn is safety for our users so they can buy, sell and trade with out any worry"
					+ "for security. "
					+ "We really hope you will find our website fun and intuitive to use. "
					+ "Kind Regards bitBay team.", 
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