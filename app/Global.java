import java.util.ArrayList;
import java.util.List;

import helpers.HashHelper;
import models.*;
import play.Application;
import play.GlobalSettings;
import play.db.ebean.Model.Finder;

/**
 * At the opening of the website there is already created: 6 users 8 categories
 * 10 products(connected to their parent category) 3 FAQs
 * 
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
			u2.user_address = "Direktorska 33";
			User.create(u2);
			u2.username = "Mustafa";
			u2.hasAdditionalInfo = true;
			u2.rating = 4.5;
			u2.update();

			User u3 = new User("emina.muratovic@bitcamp.ba",
					HashHelper.createPassword("emina"), false, true);
			u3.user_address = "Zrtava turbo folka 12";
			User.create(u3);
			u3.username = "Emina";
			u3.hasAdditionalInfo = true;
			u3.rating = 4;
			u3.update();

			User u4 = new User("haris.arifovic@bitcamp.ba",
					HashHelper.createPassword("haris"), false, true);
			u4.user_address = "Lozionicka 2";
			User.create(u4);
			u4.username = "Haris";
			u4.hasAdditionalInfo = true;
			u4.rating = 3.5;
			u4.update();

			User u5 = new User("hikmet.durgutovic@bitcamp.ba",
					HashHelper.createPassword("hikmet"), false, true);
			User.create(u5);
			u5.username = "Hikmet";
			u5.hasAdditionalInfo = true;
			u5.rating = 3.0;
			u5.update();

			User u6 = new User("nermin.vucinic@bitcamp.ba",
					HashHelper.createPassword("nermin"), false, true);
			u6.user_address = "Trebinjska 113";
			User.create(u6);
			u6.username = "Nermin";
			u6.hasAdditionalInfo = true;
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

		if (Message.find(1) == null) {

			Message.create("Dragi svi "
					+ "u ime predavaca Arifa je na Google Classroom "
					+ "(Predavanja) postavljen kod. "
					+ "Molim da ovo primite k znanju. " + "Lijep pozdrav",
					User.find(2), User.find(1), "Contact Seller message!");
			Message.create("Dragi svi "
					+ "u ime predavaca Arifa je na Google Classroom "
					+ "(Predavanja) postavljen kod. "
					+ "Molim da ovo primite k znanju. " + "Lijep pozdrav",
					User.find(3), User.find(1), "Contact Seller message!");
			Message.create("Dragi svi "
					+ "u ime predavaca Arifa je na Google Classroom "
					+ "(Predavanja) postavljen kod. "
					+ "Molim da ovo primite k znanju. " + "Lijep pozdrav",
					User.find(4), User.find(1), "Contact Seller message!");
			Message.create("Dragi svi "
					+ "u ime predavaca Arifa je na Google Classroom "
					+ "(Predavanja) postavljen kod. "
					+ "Molim da ovo primite k znanju. " + "Lijep pozdrav",
					User.find(5), User.find(1), "Contact Seller message!");
			Message.create("Dragi svi "
					+ "u ime predavaca Arifa je na Google Classroom "
					+ "(Predavanja) postavljen kod. "
					+ "Molim da ovo primite k znanju. " + "Lijep pozdrav",
					User.find(3), User.find(2), "Contact Seller message!");
			Message.create("Dragi svi "
					+ "u ime predavaca Arifa je na Google Classroom "
					+ "(Predavanja) postavljen kod. "
					+ "Molim da ovo primite k znanju. " + "Lijep pozdrav",
					User.find(4), User.find(2), "Contact Seller message!");
			Message.create("Dragi svi "
					+ "u ime predavaca Arifa je na Google Classroom "
					+ "(Predavanja) postavljen kod. "
					+ "Molim da ovo primite k znanju. " + "Lijep pozdrav",
					User.find(1), User.find(3), "Contact Seller message!");
			Message.create("Dragi svi "
					+ "u ime predavaca Arifa je na Google Classroom "
					+ "(Predavanja) postavljen kod. "
					+ "Molim da ovo primite k znanju. " + "Lijep pozdrav",
					User.find(2), User.find(3), "Contact Seller message!");
			Message.create("Thanks for response. Everything was ok with item.",
					User.find(2), User.find(6), "Contact Seller message!");
			Message.create(
					"It was a pleasure working with. Best of luck in future trades",
					User.find(3), User.find(6), "Contact Seller message!");
			Message.create(
					"Where is my dog?! I've payed it to you fair and square!!",
					User.find(4), User.find(6), "Contact Seller message!");

		}

		if (Category.find(1) == null) {
			String categoryArray[] = { "Motors", "Fashion", "Smartphones",
					"Electronics", "Collectibles", "Office", "Antiques",
					"Sports", "Music", "Garden", "Toys", "Books",
					"Health & Beauty" };

			for (int i = 0; i < categoryArray.length; i++) {
				Category.create(categoryArray[i]);
			}

		}

		if (Product.find(1) == null) {

			Product pro = Product
					.create("POST CARD of H.M.C.S.ASSINIBOINE",
							18.00,
							1,
							User.find(3),
							"Used: An item that has been used previously. WW2 Photo Post Card of HMCS Assin.",
							5);

			Image img3 = new Image();
			img3.image = "images/assiniboine.JPG";
			img3.product = pro;
			Tag.create(pro, Category.find(pro.category_id).name);
			Tag.create(pro, pro.name);
			List<Image> imggg = new ArrayList<Image>();
			imggg.add(img3);
			Image.saveImg(img3);
			pro.category_id = 5;
			pro.images = imggg;
			pro.update();

			Product prod = Product
					.create("2014 Mazda CX-5 4DR SUV", 24900.00, 1,
							User.find(2), "condition:	used; " + "year: 2014; "
									+ "Make: Mazda; " + "mileage: 12,831; "
									+ "Transmission: Automatic;"
									+ "	Fuel Type:	Gas", 1);

			Image img4 = new Image();
			img4.image = "images/6800.jpg";
			img4.product = prod;
			Tag.create(prod, Category.find(prod.category_id).name);
			Tag.create(prod, prod.name);
			List<Image> imgggg = new ArrayList<Image>();
			imgggg.add(img3);
			Image.saveImg(img4);
			prod.category_id = 1;
			prod.images = imgggg;

			Product produ = Product.create("Samsung Galaxy S6", 2000.00, 15,
					User.find(3), "Detailed: item info, "
							+ "Product: Identifiers, " + "Brand: Samsung, "
							+ "Carrier: Unlocked,"
							+ " Family Line: Samsung Galaxy S6,"
							+ " Type: Smartphone, " + "Digital Camera: Yes,"
							+ " Email Access: Yes,"
							+ " Battery Capacity: 2550 mAh,"
							+ " Display Technology:	Quad HD Super AMOLED", 3);

			Image img5 = new Image();
			img5.image = "images/samsung-galaxy-s6-11.jpg";
			img5.product = produ;
			Image img5a = new Image();
			img5a.image = "images/samsung-galaxy-s6-2.jpg";
			img5a.product = produ;
			Image img5aa = new Image();
			img5aa.image = "images/samsung-galaxy-s6-4.jpg";
			img5aa.product = produ;
			Tag.create(produ, Category.find(produ.category_id).name);
			Tag.create(produ, produ.name);
			Tag.create(produ, "Samsung");
			List<Image> imggggg = new ArrayList<Image>();
			imggggg.add(img5);
			Image.saveImg(img5);
			imggggg.add(img5a);
			Image.saveImg(img5a);
			imggggg.add(img5aa);
			Image.saveImg(img5aa);
			produ.category_id = 3;
			produ.images = imggggg;

			Product produc = Product
					.create("THE BOOK OF WHO? by Rodney Dale",
							0.99,
							1,
							User.find(3),
							"Brand New: A new, unread, unused book in perfect condition with no missing or damaged pages."
									+ "Publication Year:	2005", 12);

			Image img6 = new Image();
			img6.image = "images/bookofwho.JPG";
			img6.product = produc;
			Tag.create(produc, Category.find(produc.category_id).name);
			Tag.create(produc, produc.name);
			List<Image> imgggggg = new ArrayList<Image>();
			imgggggg.add(img6);
			Image.saveImg(img6);
			produc.category_id = 12;
			produc.images = imgggggg;

			Product product = Product.create("Mercedes 270", 45750, 2,
					User.find(4), "Year: 2015, " + "Make: Mercedes; "
							+ "Condition: used; " + "Mileage: 21,841; "
							+ "Transmission: manual", 1);

			Image img7 = new Image();
			img7.image = "images/mercedes.jpg";
			img7.product = product;
			Tag.create(product, Category.find(product.category_id).name);
			Tag.create(product, product.name);
			List<Image> imggggggg = new ArrayList<Image>();
			imggggggg.add(img7);
			Image.saveImg(img7);
			product.category_id = 1;
			product.images = imggggggg;

			Product product1 = Product
					.create("Height Adjustment Office Chair",
							48.51,
							10,
							User.find(6),
							"Material: mesh, plastic, metal, foam; Ergonomically designed.",
							6);

			Image img8 = new Image();
			img8.image = "images/heightchair.JPG";
			img8.product = product1;
			Image chair = new Image();
			chair.image = "images/heightchair1.JPG";
			chair.product = product1;
			Image chair1 = new Image();
			chair1.image = "images/heightchair2.JPG";
			chair1.product = product1;
			Image chair2 = new Image();
			chair2.image = "images/heightchair3.jpg";
			chair2.product = product1;
			Tag.create(product1, Category.find(product1.category_id).name);
			Tag.create(product1, product1.name);
			List<Image> imgggggggg = new ArrayList<Image>();
			imgggggggg.add(img8);
			Image.saveImg(img8);
			imgggggggg.add(chair);
			Image.saveImg(chair);
			imgggggggg.add(chair1);
			Image.saveImg(chair1);
			imgggggggg.add(chair2);
			Image.saveImg(chair2);
			product1.category_id = 6;
			product1.images = imgggggggg;

			Product product2 = Product.create("BMW 320", 15000, 1,
					User.find(4), "Year: 2003; " + "Mileage: 129,27; "
							+ " Transmission: automatic;" + " Make: BMW ", 1);

			Image img9 = new Image();
			img9.image = "images/bmw.jpg";
			img9.product = product2;
			Tag.create(product2, Category.find(product2.category_id).name);
			Tag.create(product2, product2.name);
			List<Image> imggggggggg = new ArrayList<Image>();
			imggggggggg.add(img9);
			Image.saveImg(img9);
			product2.category_id = 1;
			product2.images = imggggggggg;

			Product product3 = Product.create("Iphone 6", 1530, 15,
					User.find(5), "Brand: Apple," + "Storage Capacity: 64 GB, "
							+ " Camera:	8.0MP," + " Model: iPhone 6 Plus,"
							+ "Digital Camera: Yes," + " Email Access: Yes,"
							+ " Battery Capacity: 2000 mAh,"
							+ " Operating System:iOS", 3);

			Image img10 = new Image();
			img10.image = "images/iphone.jpg";
			img10.product = product3;
			Image phone = new Image();
			phone.image = "images/iphone6.jpg";
			phone.product = product3;
			Image phone1 = new Image();
			phone1.image = "images/iphone61.jpg";
			phone1.product = product3;
			Image phone2 = new Image();
			phone2.image = "images/iphone62.jpg";
			phone2.product = product3;
			Tag.create(product3, Category.find(product3.category_id).name);
			Tag.create(product3, product3.name);
			List<Image> imgggggggggg = new ArrayList<Image>();
			imgggggggggg.add(img10);
			Image.saveImg(img10);
			imgggggggggg.add(phone);
			Image.saveImg(phone);
			imgggggggggg.add(phone1);
			Image.saveImg(phone1);
			imgggggggggg.add(phone2);
			Image.saveImg(phone2);
			product3.category_id = 3;
			product3.images = imgggggggggg;

			Product product4 = Product
					.create("Crawford Metal Pation Mosquito Net",
							279.95,
							10,
							User.find(6),
							"Stylish design w/ Cream White Fabric to match all kinds furniture. 10' x 10' Rattan gazebo",
							10);

			Image img11 = new Image();
			img11.image = "images/crawford.JPG";
			img11.product = product4;
			Image net = new Image();
			net.image = "images/crawford1.JPG";
			net.product = product4;
			Tag.create(product4, Category.find(product4.category_id).name);
			Tag.create(product4, product4.name);
			List<Image> imggggggggggg = new ArrayList<Image>();
			imggggggggggg.add(img11);
			Image.saveImg(img11);
			imggggggggggg.add(net);
			Image.saveImg(net);
			product4.category_id = 10;
			product4.images = imggggggggggg;

			Product product6 = Product.create("Samsung galaxy S6 cover", 9.99,
					50, User.find(3), "Silicon cover for Samsung galaxy S6", 3);

			Image img13 = new Image();
			img13.image = "images/samsungCover.jpg";
			img13.product = product6;
			Tag.create(product6, Category.find(product6.category_id).name);
			Tag.create(product6, product6.name);
			Tag.create(product6, "Samsung");
			List<Image> image1 = new ArrayList<Image>();
			image1.add(img13);
			Image.saveImg(img13);
			product6.category_id = 3;
			product6.images = image1;

			Product product7 = Product.create("Headphones for S6", 19.99, 100,
					User.find(3),
					"Very stylish headphones for Samsung galaxy S6", 3);

			Image img14 = new Image();
			img14.image = "images/samsungHeadphones.jpg";
			img14.product = product7;
			Tag.create(product7, Category.find(product7.category_id).name);
			Tag.create(product7, product7.name);
			Tag.create(product7, "Samsung");
			List<Image> image2 = new ArrayList<Image>();
			image2.add(img14);
			Image.saveImg(img14);
			product7.category_id = 3;
			product7.images = image2;

			Product product8 = Product
					.create("Dock for Samsung galaxy S6 ",
							29.99,
							10,
							User.find(3),
							"Beatiful all-in-one dock for the new Samsung galaxy S6",
							3);

			Image img15 = new Image();
			img15.image = "images/samsungDock.jpg";
			img15.product = product8;
			Tag.create(product8, Category.find(product8.category_id).name);
			Tag.create(product8, product8.name);
			Tag.create(product8, "Samsung");
			List<Image> image3 = new ArrayList<Image>();
			image3.add(img15);
			Image.saveImg(img15);
			product8.category_id = 3;
			product8.images = image3;

			Product product9 = Product.create("3 Person Outdoor Patio Gazebo ",
					599.99, 50, User.find(6),
					"Overall Dimensions: 91\" x 58\" x 75\"/92\". Max Weight Capacity: 800 lbs"
							+ "Material: Steel & polyester", 10);

			Image img16 = new Image();
			img16.image = "images/3person.JPG";
			img16.product = product9;
			Image person = new Image();
			person.image = "images/3person1.JPG";
			person.product = product9;
			Image person1 = new Image();
			person1.image = "images/3person2.JPG";
			person1.product = product9;
			Tag.create(product9, Category.find(product9.category_id).name);
			Tag.create(product9, product9.name);
			List<Image> image4 = new ArrayList<Image>();
			image4.add(img16);
			Image.saveImg(img16);
			image4.add(person);
			Image.saveImg(person);
			image4.add(person1);
			Image.saveImg(person1);
			product9.category_id = 10;
			product9.images = image4;

			Product product10 = Product
					.create("50 PERSONALISED LANYARDS",
							98.57,
							200,
							User.find(6),
							"MPN:personalised lanyards,neck straps 20mm width; Country of Manufacture: United Kingdom",
							6);

			Image img17 = new Image();
			img17.image = "images/lanyards.jpg";
			img17.product = product10;
			Tag.create(product10, Category.find(product10.category_id).name);
			Tag.create(product10, product10.name);
			List<Image> image5 = new ArrayList<Image>();
			image5.add(img17);
			Image.saveImg(img17);
			product10.category_id = 6;
			product10.images = image5;

			Product product11 = Product
					.create("Modern High-Back Chair",
							125.95,
							200,
							User.find(6),
							"Wheel Color: White; Frame and Base: Chrome; Height adjustable",
							6);

			Image img18 = new Image();
			img18.image = "images/chair.JPG";
			img18.product = product11;
			Image high = new Image();
			high.image = "images/chair1.JPG";
			high.product = product11;
			Image high1 = new Image();
			high1.image = "images/chair2.JPG";
			high1.product = product11;
			Image high2 = new Image();
			high2.image = "images/chair3.JPG";
			high2.product = product11;
			Tag.create(product11, Category.find(product11.category_id).name);
			Tag.create(product11, product11.name);
			List<Image> image6 = new ArrayList<Image>();
			image6.add(img18);
			Image.saveImg(img18);
			image6.add(high);
			Image.saveImg(high);
			image6.add(high1);
			Image.saveImg(high1);
			image6.add(high2);
			Image.saveImg(high2);
			product11.category_id = 6;
			product11.images = image6;

			Product product12 = Product
					.create("NEW Authentic GUCCI",
							552.49,
							7,
							User.find(3),
							"New with tags: A brand-new, "
									+ "unused, and unworn item (including handmade items) in the original "
									+ "packaging ", 2);

			Image gucci = new Image();
			gucci.image = "images/GucciBag.JPG";
			gucci.product = product12;
			Image gucci1 = new Image();
			gucci1.image = "images/guccibag2.jpg";
			gucci1.product = product12;
			
			Tag.create(product12, Category.find(product12.category_id).name);
			Tag.create(product12, product12.name);
			List<Image> image7 = new ArrayList<Image>();
			image7.add(gucci);
			Image.saveImg(gucci);
			image7.add(gucci1);
			Image.saveImg(gucci1);
			product12.category_id = 2;
			product12.images = image7;

			Product product13 = Product.create("Raymond Stainless Watch",
					399.00, 3, User.find(4),
					"This watch is new display model. "
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
			List<Image> image8 = new ArrayList<Image>();
			image8.add(raymond);
			Image.saveImg(raymond);
			image8.add(raymond1);
			Image.saveImg(raymond1);
			image8.add(raymond2);
			Image.saveImg(raymond2);
			image8.add(raymond3);
			Image.saveImg(raymond3);
			product13.category_id = 2;
			product13.images = image8;

			Product product14 = Product
					.create("Michael KORS BUCKLE SANDALS",
							99.00,
							7,
							User.find(5),
							"New with box: A brand-new, unused, and unworn item (including handmade items) "
									+ "in the original packaging (such as the original box or bag) "
									+ "and/or with the original tags attached. ",
							2);

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
			List<Image> image9 = new ArrayList<Image>();
			image9.add(sandals);
			Image.saveImg(sandals);
			image9.add(sandals1);
			Image.saveImg(sandals1);
			image9.add(sandals2);
			Image.saveImg(sandals2);
			product14.category_id = 2;
			product14.images = image9;

			Product product15 = Product
					.create("Tory Burch Thong Sandals",
							119.00,
							10,
							User.find(6),
							"New without box: A brand-new, unused, and unworn item (including handmade items) "
									+ "that is not in original packaging or may be missing original packaging materials (such as the original box or bag). ",
							2);

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
			product15.category_id = 2;
			product15.images = image10;

			Product product16 = Product
					.create("U2: son gs of innocence- Deluxe Edition (2014)",
							5.50,
							5,
							User.find(2),
							"Brand New: An item that has never been opened or removed from the manufacturer’s sealing (if applicable). "
									+ "Item is in original shrink wrap (if applicable). See the seller's listing for full details. Number of Discs:	2."
									+ "Genre:	Rock. Tracks:  16.", 9);

			Image u2 = new Image();
			u2.image = "images/u2CD.JPG";
			u2.product = product16;
			Image u23 = new Image();
			u23.image = "images/u2CD1.JPG";
			u23.product = product16;
			Tag.create(product16, Category.find(product16.category_id).name);
			Tag.create(product16, product16.name);
			List<Image> image11 = new ArrayList<Image>();
			image11.add(u2);
			Image.saveImg(u2);
			image11.add(u23);
			Image.saveImg(u23);
			product16.category_id = 9;
			product16.images = image11;

			Product product17 = Product
					.create("Vintage Look Vase Coffee TABLE",
							55.00,
							1,
							User.find(3),
							"Good condition, Hand Made. Region of Origin:	Asia. Material:	Wood. Age:	Post-1950",
							7);

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
			List<Image> image12 = new ArrayList<Image>();
			image12.add(table);
			Image.saveImg(table);
			image12.add(table1);
			Image.saveImg(table1);
			image12.add(table2);
			Image.saveImg(table2);
			product17.category_id = 7;
			product17.images = image12;

			Product product18 = Product
					.create("GIANT BIG TEDDY BEAR",
							17.99,
							20,
							User.find(4),
							"New: A brand-new, unused, unopened, undamaged item (including handmade items)"
									+ "Country of Manufacture:	China. Size:	75cm/29.5 inch(Ear to Feet)",
							11);

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
			Tag.create(product18, "Dog");
			List<Image> image13 = new ArrayList<Image>();
			image13.add(bear);
			Image.saveImg(bear);
			image13.add(bear1);
			Image.saveImg(bear1);
			image13.add(bear2);
			Image.saveImg(bear2);
			product18.category_id = 11;
			product18.images = image13;

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
			product19.images = imageList14;

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
			product20.images = imageList15;

			Product product21 = Product
					.create("THE ROSY CROSS ITS TEACHINGS 1ST EDITION ",
							179.99,
							20,
							User.find(6),
							"THE MODERN MANIFESTOES ISSUED BY THE BROTHERHOOD, ORDER, TEMPLE AND FRATERNITY OF THE ROSY CROSS; I.E. "
									+ "FOR THE GUIDANCE OF THOSE SEEKING A MORE PERFECT {BALANCED} LIFE",
							12);

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
			product21.category_id = 6;
			product21.images = imageList16;

			Product product22 = Product
					.create("Crafting and Executing Strategy",
							30.99,
							20,
							User.find(6),
							" Brand New, Well Wrapped, **ISE** International Student Edition Book. Soft Cover, Paper Back and written in English",
							12);

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
			product22.category_id = 12;
			product22.images = imageList17;

			Product product23 = Product
					.create("ZOOT ULTRA SPEED",
							50.99,
							20,
							User.find(6),
							" Beim Ultra Speed hat Zoot sein ganzes Wissen über Materialien und Kompression eingesetzt, um einen Laufschuh zu bauen, "
									+ "der es möglich macht den Wechsel in Rekordzeit zu absolvieren. Dieser Triathlonschuh wurde für geschmeidige,",
							2);

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
			product23.category_id = 2;
			product23.images = imageList18;

			Product product24 = Product
					.create("ORIGINAL SPF 15 Foundation",
							23.28,
							20,
							User.find(6),
							" Effect: soft focus effect; Shade: Fair; For all skin types; Brand: Bare Escentuals",
							13);
			Image foundation = new Image();
			foundation.image = "images/foundation.JPG";
			foundation.product = product24;
			Image foundation1 = new Image();
			foundation1.image = "images/foundation1.JPG";
			foundation1.product = product24;
			Tag.create(product24, Category.find(product24.category_id).name);
			Tag.create(product24, product24.name);
			List<Image> imageList19 = new ArrayList<Image>();
			imageList19.add(foundation);
			Image.saveImg(foundation);
			imageList19.add(foundation1);
			Image.saveImg(foundation1);
			product24.category_id = 13;
			product24.images = imageList19;

			Product product25 = Product
					.create("Pure Brightening Serum - LATTE",
							26.45,
							10,
							User.find(6),
							" Coconut-derived base with jojoba; For all skin types; Brand: Bare Escentuals",
							13);
			Image serum = new Image();
			serum.image = "images/serumlatte.JPG";
			serum.product = product25;
			Tag.create(product25, Category.find(product25.category_id).name);
			Tag.create(product25, product25.name);
			List<Image> imageList20 = new ArrayList<Image>();
			imageList20.add(serum);
			Image.saveImg(serum);
			product25.category_id = 13;
			product25.images = imageList20;

			Product product26 = Product
					.create("Washable Men's Razor",
							19.40,
							200,
							User.find(6),
							"Cordless Rotary 3D Rechargeable Electric Deluxe;Model:BY-31 ",
							13);
			Image razor = new Image();
			razor.image = "images/razor.JPG";
			razor.product = product26;
			Image razor1 = new Image();
			razor1.image = "images/razor1.JPG";
			razor1.product = product26;
			Image razor2 = new Image();
			razor2.image = "images/razor2.JPG";
			razor2.product = product26;
			Image razor3 = new Image();
			razor3.image = "images/razor3.JPG";
			razor3.product = product26;
			Tag.create(product26, Category.find(product26.category_id).name);
			Tag.create(product26, product26.name);
			List<Image> imageList21 = new ArrayList<Image>();
			imageList21.add(razor);
			Image.saveImg(razor);
			imageList21.add(razor1);
			Image.saveImg(razor1);
			imageList21.add(razor2);
			Image.saveImg(razor2);
			imageList21.add(razor3);
			Image.saveImg(razor3);
			product26.category_id = 13;
			product26.images = imageList21;

			Product product27 = Product
					.create("Digital Blood Pressure Monitor",
							10.60,
							20,
							User.find(6),
							"Easy to use. Fast, accurate reading. Digital readout. Advice on keeping your blood pressure within acceptable limits.",
							13);
			Image blood = new Image();
			blood.image = "images/bloodpressure.JPG";
			blood.product = product27;
			Tag.create(product27, Category.find(product27.category_id).name);
			Tag.create(product27, product27.name);
			List<Image> imageList22 = new ArrayList<Image>();
			imageList22.add(blood);
			Image.saveImg(blood);
			product27.category_id = 13;
			product27.images = imageList22;

			Product product28 = Product
					.create("Exerpeutic Air Elliptical",
							99.99,
							20,
							User.find(6),
							"Max Weight: 260.000; Material: steel, plastic, slip resistant pedals; Power source: battery-powered",
							8);
			Image air = new Image();
			air.image = "images/exerpeutic.JPG";
			air.product = product28;
			Tag.create(product28, Category.find(product28.category_id).name);
			Tag.create(product28, product28.name);
			List<Image> imageList23 = new ArrayList<Image>();
			imageList23.add(air);
			Image.saveImg(air);
			product28.category_id = 8;
			product28.images = imageList23;

			Product product29 = Product.create("GEL Pad Half Finger Glove",
					7.82, 400, User.find(6),
					"Gender: Unisex; Model: Half Finger/Fingerless", 8);
			Image gel = new Image();
			gel.image = "images/gelglove.JPG";
			gel.product = product29;
			Image gel1 = new Image();
			gel1.image = "images/gelglove1.JPG";
			gel1.product = product29;
			Image gel2 = new Image();
			gel2.image = "images/gelglove2.JPG";
			gel2.product = product29;
			Image gel3 = new Image();
			gel3.image = "images/gelglove3.JPG";
			gel3.product = product29;
			Tag.create(product29, Category.find(product29.category_id).name);
			Tag.create(product29, product29.name);
			List<Image> imageList24 = new ArrayList<Image>();
			imageList24.add(gel);
			Image.saveImg(gel);
			imageList24.add(gel1);
			Image.saveImg(gel1);
			imageList24.add(gel2);
			Image.saveImg(gel2);
			imageList24.add(gel3);
			Image.saveImg(gel3);
			product29.category_id = 8;
			product29.images = imageList24;

			Product product30 = Product
					.create("Front Rear Wheel Safety Light Lamp",
							2.29,
							20,
							User.find(6),
							"2xLED bicycle Bike Cycling Silicone; Features:	Batteries Included; Country of manufacture: China",
							8);
			Image lamp = new Image();
			lamp.image = "images/frontrear.JPG";
			lamp.product = product30;
			Tag.create(product30, Category.find(product30.category_id).name);
			Tag.create(product30, product30.name);
			List<Image> imageList25 = new ArrayList<Image>();
			imageList25.add(lamp);
			Image.saveImg(lamp);
			product30.category_id = 8;
			product30.images = imageList25;

			Product product31 = Product
					.create("Hiking Trekking Travel bag",
							23.25,
							200,
							User.find(6),
							"Color: black; Military Tactical Rucksack; Country of manufacture: China",
							8);
			Image bag = new Image();
			bag.image = "images/travelbag.JPG";
			bag.product = product31;
			Image bag1 = new Image();
			bag1.image = "images/travelbag1.JPG";
			bag1.product = product31;
			Image bag2 = new Image();
			bag2.image = "images/travelbag2.JPG";
			bag2.product = product31;
			Image bag3 = new Image();
			bag3.image = "images/travelbag3.JPG";
			bag3.product = product31;
			Tag.create(product31, Category.find(product31.category_id).name);
			Tag.create(product31, product31.name);
			Tag.create(product31, "ZOOT");
			List<Image> imageList26 = new ArrayList<Image>();
			imageList26.add(bag);
			Image.saveImg(bag);
			imageList26.add(bag1);
			Image.saveImg(bag1);
			imageList26.add(bag2);
			Image.saveImg(bag2);
			imageList26.add(bag3);
			Image.saveImg(bag3);
			product31.category_id = 8;
			product31.images = imageList26;

		}

		if (!FAQ.find("I can't get items shipped until Monday and I'm afraid of hurting my top-rated seller qualification. How should I go about this?")) {
			FAQ.createFAQ(
					"I can't get items shipped until Monday and I'm afraid of hurting my top-rated seller qualification. How should I go about this?",
					"You may, if you can, decide to reward their patience by refunding the shipping cost (let them know that in the apology email).");
			FAQ.createFAQ(
					"I have 30 something bids on my vintage camper and about 6 of them are from people with 0 feedback. Is this scammers? I have sold on bitbay for years and never saw this before.",
					"People with 0 feedback are not necessarily scammers.  We all had 0 feedback once.  ");
			FAQ.createFAQ(
					"Additional free listing this month (new listings only) Why didn't I receive this?",
					"Promo offers are by invitation only; no party crashing allowed.  Unless there is an einstein among us, no one can figure out the criteria used by bitBay to get these promos.");
			FAQ.createFAQ(
					"I reported a buyer. Can you tell me results of my report?",
					"To make buyers feel comfortable about buying, we don’t disclose the results of a report. Reports from sellers help us make bitBay a "
							+ "great experience for everyone. Since the launch of the enhanced Report a Buyer feature, we’ve had more reports submitted by sellers. "
							+ "In response, we’ve been able to take more actions to prevent abuse by buyers. If we take action on a buyer who is reported for policy "
							+ "violations, any transaction defects created by that buyer are automatically removed. We will let sellers know if they have had defects "
							+ "removed either on their Seller Dashboard or via a monthly communication from bitBay.");
			FAQ.createFAQ(
					"Does bitBay mainly side with the buyer in case resolution?",
					"No. We strive to make sure cases get resolved fairly to the satisfaction of both parties. If you feel a case has been resolved unfairly,"
							+ " you can appeal the decision by providing new information to the case, such as additional information from the shipping carrier to "
							+ "Customer Support within 45 days from the day the case is closed. bitBay reviews information and documentation from both buyers and sellers"
							+ " in detail before making a final decision. If a seller's appeal is granted, all defects for that transaction will be automatically removed.");
			FAQ.createFAQ(
					"What should I do when I feel a buyer is misusing the feedback system by threatening me?",
					"First, place yourself in the buyer’s shoes and ask yourself if the buyer is asking for something unreasonable. You’ll find that most buyers "
							+ "just want to resolve an issue. If you feel the buyer is in fact using low Feedback as a threat or the buyer’s demands are beyond what you "
							+ "promised, please report that buyer. See eBay Feedback Extortion policy to learn more.");

			FAQ.createFAQ(
					"What is bitBay doing to get buyers who repeatedly violate bitBay policies off the site?",
					"Depending on a buyer’s behavior, there are a number of actions the team can take—from issuing a warning, to setting buyer restrictions, to suspending the buyer from our site. "
							+ "If we take action on a buyer, all defects created by that buyer will be automatically removed.");
			FAQ.createFAQ(
					"A buyer contacted me saying they did not receive their order. What should I do?",
					"Work with the buyer to reach a mutually agreeable solution in regard to this transaction. How you decide to proceed is ultimately determined by you and your customer service policy,"
							+ "but all sellers are expected to demonstrate a high level of customer service. ");
		}
		if (Comment.find(1) == null) {
			Comment.createComment("Hope to deal with you again. Thank you.",
					User.find(2), Product.find(1));
			Comment.createComment(
					"Quick response and fast delivery. Perfect! THANKS!!",
					User.find(4), Product.find(1));
			Comment.createComment(
					"Thank you for an easy, pleasant transaction. Excellent product. A++++++.",
					User.find(4), Product.find(2));
			Comment.createComment("Welcome your next purchase!Nice product!",
					User.find(3), Product.find(2));
			Comment.createComment(
					"Good product, prompt delivery, valued seller, highly recommended.",
					User.find(6), Product.find(3));
			Comment.createComment(
					"SUPER GREAT PRODUCT A+++ ANY ISSUE LET ME KNOW EMERGENT SOLUTION FOR YOU :).",
					User.find(2), Product.find(3));
			Comment.createComment(
					"Thank you for an easy, pleasant transaction. Excellent product. A++++++.",
					User.find(4), Product.find(4));
			Comment.createComment(
					"Good product, prompt delivery, valued seller, highly recommended.",
					User.find(6), Product.find(4));
			Comment.createComment(
					"Quick response and fast delivery. Perfect! THANKS!!",
					User.find(3), Product.find(5));
			Comment.createComment(
					"Good product, prompt delivery, valued seller, highly recommended.",
					User.find(2), Product.find(5));
			Comment.createComment(
					"Great communication. A pleasure to do business with.",
					User.find(3), Product.find(6));
			Comment.createComment(
					"Thank you for an easy, pleasant transaction. Excellent product A++++++",
					User.find(4), Product.find(6));
			Comment.createComment(
					"Good product, prompt delivery, valued seller, highly recommended.",
					User.find(2), Product.find(7));
			Comment.createComment(
					"Thank you a great product Call back anytime A+++++++",
					User.find(3), Product.find(7));
			Comment.createComment(
					"Thank you a great product Call back anytime A+++++++",
					User.find(4), Product.find(8));
			Comment.createComment("Hope to deal with you again. Thank you.",
					User.find(3), Product.find(8));
			Comment.createComment(
					"Thank you a great product Call back anytime A+++++++",
					User.find(2), Product.find(9));
			Comment.createComment("Hope to deal with you again. Thank you.",
					User.find(3), Product.find(9));
			Comment.createComment(
					"Thank you a great product Call back anytime A+++++++",
					User.find(4), Product.find(10));
			Comment.createComment("Hope to deal with you again. Thank you.",
					User.find(6), Product.find(10));
		}
		if (Orders.find(1) == null) {
			Cart c1 = Cart.find(6);
			Cart.addProduct(Product.find(12), c1);
			Cart.addProduct(Product.find(3), c1);
			Orders.create(new Orders(c1, User.find(6), "testnera"), 2019.99);
			new ProductQuantity(Product.find(12).id, 1, Orders.find(1)).save();
			new ProductQuantity(Product.find(3).id, 1, Orders.find(1)).save();
			Cart.clear(6);

			Cart c2 = Cart.find(3);
			Cart.addProduct(Product.find(2), c2);
			Cart.addProduct(Product.find(6), c2);
			Cart.addProduct(Product.find(14), c2);
			Orders.create(new Orders(c2, User.find(3), "testemina"), 25125.55);
			new ProductQuantity(Product.find(2).id, 1, Orders.find(2)).save();
			new ProductQuantity(Product.find(6).id, 1, Orders.find(2)).save();
			new ProductQuantity(Product.find(14).id, 1, Orders.find(2)).save();
			Cart.clear(3);

			Cart c3 = Cart.find(2);
			Cart.addProduct(Product.find(13), c3);
			Cart.addProduct(Product.find(3), c3);
			Orders.create(new Orders(c3, User.find(2), "testmustafa"), 2029.99);
			new ProductQuantity(Product.find(13).id, 1, Orders.find(3)).save();
			new ProductQuantity(Product.find(3).id, 1, Orders.find(3)).save();
			Cart.clear(2);

			Cart c4 = Cart.find(3);
			Cart.addProduct(Product.find(6), c4);
			Cart.addProduct(Product.find(15), c4);
			Orders.create(new Orders(c4, User.find(3), "testemina"), 129.69);
			new ProductQuantity(Product.find(6).id, 1, Orders.find(4)).save();
			new ProductQuantity(Product.find(15).id, 1, Orders.find(4)).save();
			Cart.clear(3);

		}

		if (Blog.findBlogById(1) == null) {
			Blog.createBlog(
					"bitBay started",
					"A new website for online shopping has started to work today. We will be at your service 24/7 and provide top service "
							+ "to all our users. Our primary conceirn is safety for our users so they can buy, sell and trade with out any worry"
							+ "for security. "
							+ "We really hope you will find our website fun and intuitive to use. "
							+ "Kind Regards bitBay team.", "images/logo.png",
					1, "Admin");
		}

		if (Report.find(1) == null) {
			Report.report(Product.find(1), User.find(2),
					"Report for product 1 from user with id 2.");
			Report.report(Product.find(1), User.find(3),
					"Report for product 1 from user with id 3.");
			Report.report(Product.find(2), User.find(4),
					"Report for product 2 from user with id 4.");
			Report.report(Product.find(2), User.find(5),
					"Report for product 2 from user with id 5.");
			Report.report(Product.find(2), User.find(6),
					"Report for product 2 from user with id 6.");
			Report.report(Product.find(3), User.find(2),
					"Report for product 3 from user with id 2.");
			Report.report(Product.find(4), User.find(3),
					"Report for product 4 from user with id 3.");
			Report.report(Product.find(4), User.find(4),
					"Report for product 4 from user with id 4.");
			Report.report(Product.find(4), User.find(5),
					"Report for product 4 from user with id 5.");
			Report.report(Product.find(4), User.find(6),
					"Report for product 4 from user with id 6.");
		}

	}
}
