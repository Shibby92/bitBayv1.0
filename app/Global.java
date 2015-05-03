import java.util.ArrayList;
import java.util.List;

import helpers.HashHelper;
import models.*;
import play.Application;
import play.GlobalSettings;
import play.Play;

/**
 * Global class is used to create several data for the purpose of easier work
 * around some functionalities, implementations and testings
 * 
 * @author harisarifovic
 *
 */

public class Global extends GlobalSettings {
	/**
	 * Creation of data on start
	 */
	public void onStart(Application app) {

		/**
		 * Creation of 7 users, including 1 administrator and 1 blogger
		 */

		if (User.find("mehnnbitbay@gmail.com") == null) {

			makeUser(
					"mehnnbitbay@gmail.com",
					HashHelper.createPassword(Play.application()
							.configuration().getString("user1")), true, true,
					"Admin Street 229", "Admin", true, 5.00, false);
			makeUser(
					"mustafa.ademovic93@gmail.com",
					HashHelper.createPassword(Play.application()
							.configuration().getString("user2")), false, true,
					"Direktorska 33", "Mustafa", true, 4.5, false);
			makeUser(
					"emina.muratovic@bitcamp.ba",
					HashHelper.createPassword(Play.application()
							.configuration().getString("user3")), false, true,
					"Zrtava turbo folka", "Emina", true, 4, false);
			makeUser(
					"haris.arifovic@bitcamp.ba",
					HashHelper.createPassword(Play.application()
							.configuration().getString("user4")), false, true,
					"Lozionicka 2", "Haris", true, 3.5, false);
			makeUser(
					"nermin.graca@bitcamp.ba",
					HashHelper.createPassword(Play.application()
							.configuration().getString("user5")), false, true,
					"Laticka bb", "Nermin", true, 3, false);
			makeUser(
					"nermin.vucinic@bitcamp.ba",
					HashHelper.createPassword(Play.application()
							.configuration().getString("user6")), false, true,
					"Trebinjska 113", "Nermin", true, 2.5, false);
			makeUser(
					"blogger@bitcamp.ba",
					HashHelper.createPassword(Play.application()
							.configuration().getString("user7")), false, true,
					"Bloggers St. 72", "Blogger", true, 2.0, true);
		}

		/**
		 * Creating 10 messages for several users
		 */
		if (Message.find(1) == null) {
			Message.create(
					Play.application().configuration()
							.getString("globalMessage"),
					User.find(2),
					User.find(1),
					Play.application().configuration()
							.getString("globalMessage2"));
			Message.create(
					Play.application().configuration()
							.getString("globalMessage"),
					User.find(3),
					User.find(1),
					Play.application().configuration()
							.getString("globalMessage2"));
			Message.create(
					Play.application().configuration()
							.getString("globalMessage"),
					User.find(4),
					User.find(1),
					Play.application().configuration()
							.getString("globalMessage2"));
			Message.create(
					Play.application().configuration()
							.getString("globalMessage"),
					User.find(5),
					User.find(1),
					Play.application().configuration()
							.getString("globalMessage2"));
			Message.create(
					Play.application().configuration()
							.getString("globalMessage"),
					User.find(3),
					User.find(2),
					Play.application().configuration()
							.getString("globalMessage2"));
			Message.create(
					Play.application().configuration()
							.getString("globalMessage"),
					User.find(4),
					User.find(2),
					Play.application().configuration()
							.getString("globalMessage2"));
			Message.create(
					Play.application().configuration()
							.getString("globalMessage"),
					User.find(1),
					User.find(3),
					Play.application().configuration()
							.getString("globalMessage2"));
			Message.create(
					Play.application().configuration()
							.getString("globalMessage"),
					User.find(2),
					User.find(3),
					Play.application().configuration()
							.getString("globalMessage2"));
			Message.create(
					Play.application().configuration()
							.getString("globalMessage3"),
					User.find(2),
					User.find(6),
					Play.application().configuration()
							.getString("globalMessage2"));
			Message.create(
					Play.application().configuration()
							.getString("globalMessage4"),
					User.find(3),
					User.find(6),
					Play.application().configuration()
							.getString("globalMessage2"));
			Message.create(
					Play.application().configuration()
							.getString("globalMessage5"),
					User.find(4),
					User.find(6),
					Play.application().configuration()
							.getString("globalMessage2"));

		}

		/**
		 * Creating 15 different categories for better product placement
		 */

		if (Category.find(1) == null) {
			String categoryArray[] = { "Motors", "Fashion", "Smartphones",
					"Electronics", "Houses", "Collectibles", "Business",
					"Animals", "Antiques", "Sports", "Music", "Garden", "Toys",
					"Books", "Health" };

			for (int i = 0; i < categoryArray.length; i++) {
				Category.create(categoryArray[i]);
			}

		}

		/**
		 * Creating 23 different products
		 */
		if (Product.find(1) == null) {
			makeProduct(
					Play.application().configuration()
							.getString("productName1"),
					18.00,
					1,
					User.find(3),
					Play.application().configuration()
							.getString("productDescription1"),
					5,
					Play.application().configuration()
							.getString("productImagePath1"), "");
			makeProduct(
					Play.application().configuration()
							.getString("productName2"),
					24900.0,
					1,
					User.find(2),
					Play.application().configuration()
							.getString("productDescription2"),
					1,
					Play.application().configuration()
							.getString("productImagePath2"), "");
			makeProduct(
					Play.application().configuration()
							.getString("productName3"),
					2000.00,
					15,
					User.find(3),
					Play.application().configuration()
							.getString("productDescription3"),
					3,
					Play.application().configuration()
							.getString("productImagePath3"),
					Play.application().configuration()
							.getString("productImagePath3a"),
					Play.application().configuration()
							.getString("productImagePath3aa"), "Samsung");
			makeProduct(
					Play.application().configuration()
							.getString("productName4"),
					0.99,
					1,
					User.find(3),
					Play.application().configuration()
							.getString("productDescription4"),
					13,
					Play.application().configuration()
							.getString("productImagePath4"), "");
			makeProduct(
					Play.application().configuration()
							.getString("productName5"),
					45750,
					2,
					User.find(4),
					Play.application().configuration()
							.getString("productDescription5"),
					1,
					Play.application().configuration()
							.getString("productImagePath5"), "");
			makeProduct(
					Play.application().configuration()
							.getString("productName6"),
					120,
					3,
					User.find(6),
					Play.application().configuration()
							.getString("productDescription6"),
					8,
					Play.application().configuration()
							.getString("productImagePath6"), "");
			makeProduct(
					Play.application().configuration()
							.getString("productName7"),
					15000,
					1,
					User.find(4),
					Play.application().configuration()
							.getString("productDescription7"),
					1,
					Play.application().configuration()
							.getString("productImagePath7"), "");
			makeProduct(
					Play.application().configuration()
							.getString("productName8"),
					1530,
					15,
					User.find(5),
					Play.application().configuration()
							.getString("productDescription8"),
					3,
					Play.application().configuration()
							.getString("productImagePath8"), "");
			makeProduct(
					Play.application().configuration()
							.getString("productName9"),
					120,
					1,
					User.find(6),
					Play.application().configuration()
							.getString("productDescription9"),
					8,
					Play.application().configuration()
							.getString("productImagePath9"), "");

			// 10th

			makeProduct(
					Play.application().configuration()
							.getString("productName10"),
					100000,
					3,
					User.find(2),
					Play.application().configuration()
							.getString("productDescription10"),
					5,
					Play.application().configuration()
							.getString("productImagePath10"), "");
			makeProduct(
					Play.application().configuration()
							.getString("productName11"),
					9.99,
					50,
					User.find(3),
					Play.application().configuration()
							.getString("productDescription11"),
					3,
					Play.application().configuration()
							.getString("productImagePath11"), "Samsung");
			makeProduct(
					Play.application().configuration()
							.getString("productName12"),
					19.99,
					100,
					User.find(3),
					Play.application().configuration()
							.getString("productDescription12"),
					3,
					Play.application().configuration()
							.getString("productImagePath12"), "Samsung");
			makeProduct(
					Play.application().configuration()
							.getString("productName13"),
					29.99,
					10,
					User.find(3),
					Play.application().configuration()
							.getString("productDescription13"),
					3,
					Play.application().configuration()
							.getString("productImagePath13"), "Samsung");
			makeProduct(
					Play.application().configuration()
							.getString("productName14"),
					5.55,
					15,
					User.find(6),
					Play.application().configuration()
							.getString("productDescription14"),
					8,
					Play.application().configuration()
							.getString("productImagePath14"), "Dog");
			makeProduct(
					Play.application().configuration()
							.getString("productName15"),
					9.69,
					20,
					User.find(6),
					Play.application().configuration()
							.getString("productDescription15"),
					8,
					Play.application().configuration()
							.getString("productImagePath15"), "Dog");
			makeProduct(
					Play.application().configuration()
							.getString("productName16"),
					21.29,
					5,
					User.find(6),
					Play.application().configuration()
							.getString("productDescription16"),
					8,
					Play.application().configuration()
							.getString("productImagePath16"), "Dog");
			makeProduct(
					Play.application().configuration()
							.getString("productName17"),
					552.49,
					7,
					User.find(3),
					Play.application().configuration()
							.getString("productDescription17"),
					2,
					Play.application().configuration()
							.getString("productImagePath17a"),
					Play.application().configuration()
							.getString("productImagePath17b"),
					Play.application().configuration()
							.getString("productImagePath17c"), "");
			makeProduct(
					Play.application().configuration()
							.getString("productName18"),
					399.00,
					3,
					User.find(4),
					Play.application().configuration()
							.getString("productDescription18"),
					2,
					Play.application().configuration()
							.getString("productImagePath18a"),
					Play.application().configuration()
							.getString("productImagePath18b"),
					Play.application().configuration()
							.getString("productImagePath18c"), "");
			makeProduct(
					Play.application().configuration()
							.getString("productName19"),
					99.00,
					7,
					User.find(5),
					Play.application().configuration()
							.getString("productDescription19"),
					2,
					Play.application().configuration()
							.getString("productImagePath19a"),
					Play.application().configuration()
							.getString("productImagePath19b"),
					Play.application().configuration()
							.getString("productImagePath19c"), "");
			makeProduct(
					Play.application().configuration()
							.getString("productName20"),
					119.00,
					10,
					User.find(6),
					Play.application().configuration()
							.getString("productDescription20"),
					2,
					Play.application().configuration()
							.getString("productImagePath20a"),
					Play.application().configuration()
							.getString("productImagePath20b"),
					Play.application().configuration()
							.getString("productImagePath20c"), "");
			makeProduct(
					Play.application().configuration()
							.getString("productName21"),
					5.50,
					5,
					User.find(2),
					Play.application().configuration()
							.getString("productDescription21"),
					11,
					Play.application().configuration()
							.getString("productImagePath21"), "");
			makeProduct(
					Play.application().configuration()
							.getString("productName22"),
					55.00,
					1,
					User.find(3),
					Play.application().configuration()
							.getString("productDescription22"),
					9,
					Play.application().configuration()
							.getString("productImagePath22"), "");
			makeProduct(
					Play.application().configuration()
							.getString("productName23"),
					17.99,
					20,
					User.find(4),
					Play.application().configuration()
							.getString("productDescription23"),
					13,
					Play.application().configuration()
							.getString("productImagePath23"), "");
		}

		/**
		 * Creating 8 different FAQ's
		 */

		if (!FAQ.find("I can't get items shipped until Monday and I'm afraid of hurting my top-rated seller qualification. How should I go about this?")) {
			FAQ.createFAQ(
					Play.application().configuration()
							.getString("FAQQuestion1"), Play.application()
							.configuration().getString("FAQAnswers1"));
			FAQ.createFAQ(
					Play.application().configuration()
							.getString("FAQQuestion2"), Play.application()
							.configuration().getString("FAQAnswers2"));
			FAQ.createFAQ(
					Play.application().configuration()
							.getString("FAQQuestion3"), Play.application()
							.configuration().getString("FAQAnswers3"));
			FAQ.createFAQ(
					Play.application().configuration()
							.getString("FAQQuestion4"), Play.application()
							.configuration().getString("FAQAnswers4"));
			FAQ.createFAQ(
					Play.application().configuration()
							.getString("FAQQuestion5"), Play.application()
							.configuration().getString("FAQAnswers5"));
			FAQ.createFAQ(
					Play.application().configuration()
							.getString("FAQQuestion6"), Play.application()
							.configuration().getString("FAQAnswers6"));
			FAQ.createFAQ(
					Play.application().configuration()
							.getString("FAQQuestion7"), Play.application()
							.configuration().getString("FAQAnswers7"));
			FAQ.createFAQ(
					Play.application().configuration()
							.getString("FAQQuestion8"), Play.application()
							.configuration().getString("FAQAnswers8"));
		}

		/**
		 * Creating 20 comments for several products
		 */

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

		/**
		 * Creating 4 orders for the recommendation algorithm demonstration
		 */

		if (Orders.find(1) == null) {
			makeOrder(6, 12, 3, "testnera", 2019.99, 1);
			makeOrder(3, 6, 14, "testemina", 25125.55, 2);
			makeOrder(2, 13, 3, "testmustafa", 2029.99, 3);
			makeOrder(3, 6, 15, "testemina", 129.69, 4);
		}

		/**
		 * Creating a blog post
		 */

		if (Blog.findBlogById(1) == null) {
			Blog.createBlog("bitBay started", Play.application()
					.configuration().getString("BlogMessage")
					+ "Kind Regards bitBay team.", "images/logo.png", 1,
					"Admin");
		}

		/**
		 * Making 10 different reports for several products
		 */

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

	/**
	 * Shortening the code for creating a user on application start
	 * 
	 * @param email
	 *            User's email
	 * @param password
	 *            User's password
	 * @param isAdmin
	 *            Is the user an admin user
	 * @param isVerified
	 *            Is the user verified
	 * @param street
	 *            User's street address
	 * @param nickname
	 *            User's nickname
	 * @param addInfo
	 *            Has the user filled additional info
	 * @param rating
	 *            User's rating
	 * @param isBlogger
	 *            Is the user a blogger
	 */
	private void makeUser(String email, String password, boolean isAdmin,
			boolean isVerified, String street, String nickname,
			boolean addInfo, double rating, boolean isBlogger) {
		User u = new User(email, password, isAdmin, isVerified);
		u.user_address = street;
		User.create(u);
		u.username = nickname;
		u.hasAdditionalInfo = addInfo;
		u.rating = rating;
		u.blogger = isBlogger;
		u.update();
	}

	/**
	 * Shortening the code for creating an order on application start
	 * 
	 * @param userId
	 *            User's id for finding the cart
	 * @param productId1
	 *            Product's id to add to the order
	 * @param productId2
	 *            Product's id to add to the order
	 * @param token
	 *            Orders' token
	 * @param price
	 *            Orders' price
	 * @param orderNumber
	 *            Number that finds that specific order
	 */
	private void makeOrder(int userId, int productId1, int productId2,
			String token, double price, int orderNumber) {
		Cart c = Cart.find(userId);
		Cart.addProduct(Product.find(productId1), c);
		Cart.addProduct(Product.find(productId2), c);
		Orders.create(new Orders(c, User.find(userId), token), price);
		new ProductQuantity(Product.find(productId1).id, 1,
				Orders.find(orderNumber)).save();
		new ProductQuantity(Product.find(productId2).id, 1,
				Orders.find(orderNumber)).save();
		Cart.clear(userId);
	}

	/**
	 * Method for shortening the creation of products with one picture
	 * 
	 * @param name
	 *            Product's name
	 * @param price
	 *            Product's price
	 * @param quantity
	 *            Product's quantity
	 * @param owner
	 *            Product's owner
	 * @param description
	 *            Product's description
	 * @param categoryId
	 *            Product's category
	 * @param imagePath
	 *            Product's picture
	 * @param tag
	 *            Product's tag
	 */
	private void makeProduct(String name, double price, int quantity,
			User owner, String description, int categoryId, String imagePath,
			String tag) {
		Product p = Product.create(name, price, quantity, owner, description,
				categoryId);
		Image i = new Image();
		i.image = imagePath;
		i.product = p;
		Tag.create(p, Category.find(p.category_id).name);
		Tag.create(p, p.name);
		if (!tag.equals("")) {
			Tag.create(p, tag);
		}
		List<Image> il = new ArrayList<Image>();
		il.add(i);
		Image.saveImg(i);
		p.images = il;
		p.update();
	}

	/**
	 * Method for shortening the creation of products with three pictures
	 * 
	 * @param name
	 *            Product's name
	 * @param price
	 *            Product's price
	 * @param quantity
	 *            Product's quantity
	 * @param owner
	 *            Product's owner
	 * @param description
	 *            Product's description
	 * @param categoryId
	 *            Product's category
	 * @param imagePath1
	 *            Product's picture 1
	 * @param imagePath2
	 *            Product's picture 2
	 * @param imagePath3
	 *            Product's picture 3
	 * @param tag
	 *            Product's tag
	 */
	private void makeProduct(String name, double price, int quantity,
			User owner, String description, int categoryId, String imagePath1,
			String imagePath2, String imagePath3, String tag) {
		Product p = Product.create(name, price, quantity, owner, description,
				categoryId);
		Image ia = new Image();
		ia.image = imagePath1;
		ia.product = p;
		Image ib = new Image();
		ib.image = imagePath2;
		ib.product = p;
		Image ic = new Image();
		ic.image = imagePath3;
		ic.product = p;
		Tag.create(p, Category.find(p.category_id).name);
		Tag.create(p, p.name);
		if (!tag.equals("")) {
			Tag.create(p, tag);
		}
		List<Image> il = new ArrayList<Image>();
		il.add(ia);
		Image.saveImg(ib);
		il.add(ib);
		Image.saveImg(ic);
		il.add(ic);
		Image.saveImg(ic);
		p.images = il;
		p.update();
	}

}