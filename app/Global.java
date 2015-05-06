import helpers.HashHelper;

import java.util.ArrayList;
import java.util.List;

import models.Blog;
import models.Cart;
import models.Category;
import models.Comment;
import models.FAQ;
import models.Image;
import models.Message;
import models.Orders;
import models.Product;
import models.ProductQuantity;
import models.Report;
import models.Tag;
import models.User;
import play.Application;
import play.GlobalSettings;
import play.db.ebean.Model.Finder;

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

			makeUser("mehnnbitbay@gmail.com",
					HashHelper.createPassword(play.i18n.Messages.get("user1")),
					true, true, "Admin Street 229", "Admin", true, 5.00, false);
			makeUser("mustafa.ademovic93@gmail.com",
					HashHelper.createPassword(play.i18n.Messages.get("user2")),
					false, true, "Direktorska 33", "Mustafa", true, 4.5, false);
			makeUser("emina.muratovic@bitcamp.ba",
					HashHelper.createPassword(play.i18n.Messages.get("user3")),
					false, true, "Zrtava turbo folka", "Emina", true, 4, false);
			makeUser("haris.arifovic@bitcamp.ba",
					HashHelper.createPassword(play.i18n.Messages.get("user4")),
					false, true, "Lozionicka 2", "Haris", true, 3.5, false);
			makeUser("nermin.graca@bitcamp.ba",
					HashHelper.createPassword(play.i18n.Messages.get("user5")),
					false, true, "Laticka bb", "Nermin", true, 3, false);
			makeUser("nermin.vucinic@bitcamp.ba",
					HashHelper.createPassword(play.i18n.Messages.get("user6")),
					false, true, "Trebinjska 113", "Nermin", true, 2.5, false);
			makeUser("blogger@bitcamp.ba",
					HashHelper.createPassword(play.i18n.Messages.get("user7")),
					false, true, "Bloggers St. 72", "Blogger", true, 2.0, true);
		}

		/**
		 * Creating 10 messages for several users
		 */
		if (Message.find(1) == null) {
			Message.create(play.i18n.Messages.get("globalMessage"),
					User.find(2), User.find(1),
					play.i18n.Messages.get("globalMessage2"));
			Message.create(play.i18n.Messages.get("globalMessage"),
					User.find(3), User.find(1),
					play.i18n.Messages.get("globalMessage2"));
			Message.create(play.i18n.Messages.get("globalMessage"),
					User.find(4), User.find(1),
					play.i18n.Messages.get("globalMessage2"));
			Message.create(play.i18n.Messages.get("globalMessage"),
					User.find(5), User.find(1),
					play.i18n.Messages.get("globalMessage2"));
			Message.create(play.i18n.Messages.get("globalMessage"),
					User.find(3), User.find(2),
					play.i18n.Messages.get("globalMessage2"));
			Message.create(play.i18n.Messages.get("globalMessage"),
					User.find(4), User.find(2),
					play.i18n.Messages.get("globalMessage2"));
			Message.create(play.i18n.Messages.get("globalMessage"),
					User.find(1), User.find(3),
					play.i18n.Messages.get("globalMessage2"));
			Message.create(play.i18n.Messages.get("globalMessage"),
					User.find(2), User.find(3),
					play.i18n.Messages.get("globalMessage2"));
			Message.create(play.i18n.Messages.get("globalMessage3"),
					User.find(2), User.find(6),
					play.i18n.Messages.get("globalMessage2"));
			Message.create(play.i18n.Messages.get("globalMessage4"),
					User.find(3), User.find(6),
					play.i18n.Messages.get("globalMessage2"));
			Message.create(play.i18n.Messages.get("globalMessage5"),
					User.find(4), User.find(6),
					play.i18n.Messages.get("globalMessage2"));

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
			makeProduct(play.i18n.Messages.get("productName1"), 18.00, 1,
					User.find(3),
					play.i18n.Messages.get("productDescription1"), 5,
					play.i18n.Messages.get("productImagePath1"), "");
			makeProduct(play.i18n.Messages.get("productName2"), 24900.0, 1,
					User.find(2),
					play.i18n.Messages.get("productDescription2"), 1,
					play.i18n.Messages.get("productImagePath2"), "");
			makeProduct(play.i18n.Messages.get("productName3"), 2000.00, 15,
					User.find(3),
					play.i18n.Messages.get("productDescription3"), 3,
					play.i18n.Messages.get("productImagePath3"),
					play.i18n.Messages.get("productImagePath3a"),
					play.i18n.Messages.get("productImagePath3aa"), "Samsung");
			makeProduct(play.i18n.Messages.get("productName4"), 0.99, 1,
					User.find(3),
					play.i18n.Messages.get("productDescription4"), 13,
					play.i18n.Messages.get("productImagePath4"), "");
			makeProduct(play.i18n.Messages.get("productName5"), 45750, 2,
					User.find(4),
					play.i18n.Messages.get("productDescription5"), 1,
					play.i18n.Messages.get("productImagePath5"), "");
			makeProduct(play.i18n.Messages.get("productName6"), 120, 3,
					User.find(6),
					play.i18n.Messages.get("productDescription6"), 8,
					play.i18n.Messages.get("productImagePath6"), "");
			makeProduct(play.i18n.Messages.get("productName7"), 15000, 1,
					User.find(4),
					play.i18n.Messages.get("productDescription7"), 1,
					play.i18n.Messages.get("productImagePath7"), "");
			makeProduct(play.i18n.Messages.get("productName8"), 1530, 15,
					User.find(5),
					play.i18n.Messages.get("productDescription8"), 3,
					play.i18n.Messages.get("productImagePath8"), "");
			makeProduct(play.i18n.Messages.get("productName9"), 120, 1,
					User.find(6),
					play.i18n.Messages.get("productDescription9"), 8,
					play.i18n.Messages.get("productImagePath9"), "");

			// 10th

			makeProduct(play.i18n.Messages.get("productName10"), 100000, 3,
					User.find(2),
					play.i18n.Messages.get("productDescription10"), 5,
					play.i18n.Messages.get("productImagePath10"), "");
			makeProduct(play.i18n.Messages.get("productName11"), 9.99, 50,
					User.find(3),
					play.i18n.Messages.get("productDescription11"), 3,
					play.i18n.Messages.get("productImagePath11"), "Samsung");
			makeProduct(play.i18n.Messages.get("productName12"), 19.99, 100,
					User.find(3),
					play.i18n.Messages.get("productDescription12"), 3,
					play.i18n.Messages.get("productImagePath12"), "Samsung");
			makeProduct(play.i18n.Messages.get("productName13"), 29.99, 10,
					User.find(3),
					play.i18n.Messages.get("productDescription13"), 3,
					play.i18n.Messages.get("productImagePath13"), "Samsung");
			makeProduct(play.i18n.Messages.get("productName14"), 5.55, 15,
					User.find(6),
					play.i18n.Messages.get("productDescription14"), 8,
					play.i18n.Messages.get("productImagePath14"), "Dog");
			makeProduct(play.i18n.Messages.get("productName15"), 9.69, 20,
					User.find(6),
					play.i18n.Messages.get("productDescription15"), 8,
					play.i18n.Messages.get("productImagePath15"), "Dog");
			makeProduct(play.i18n.Messages.get("productName16"), 21.29, 5,
					User.find(6),
					play.i18n.Messages.get("productDescription16"), 8,
					play.i18n.Messages.get("productImagePath16"), "Dog");
			makeProduct(play.i18n.Messages.get("productName17"), 552.49, 7,
					User.find(3),
					play.i18n.Messages.get("productDescription17"), 2,
					play.i18n.Messages.get("productImagePath17a"),
					play.i18n.Messages.get("productImagePath17b"),
					play.i18n.Messages.get("productImagePath17c"), "");
			makeProduct(play.i18n.Messages.get("productName18"), 399.00, 3,
					User.find(4),
					play.i18n.Messages.get("productDescription18"), 2,
					play.i18n.Messages.get("productImagePath18a"),
					play.i18n.Messages.get("productImagePath18b"),
					play.i18n.Messages.get("productImagePath18c"), "");
			makeProduct(play.i18n.Messages.get("productName19"), 99.00, 7,
					User.find(5),
					play.i18n.Messages.get("productDescription19"), 2,
					play.i18n.Messages.get("productImagePath19a"),
					play.i18n.Messages.get("productImagePath19b"),
					play.i18n.Messages.get("productImagePath19c"), "");
			makeProduct(play.i18n.Messages.get("productName20"), 119.00, 10,
					User.find(6),
					play.i18n.Messages.get("productDescription20"), 2,
					play.i18n.Messages.get("productImagePath20a"),
					play.i18n.Messages.get("productImagePath20b"),
					play.i18n.Messages.get("productImagePath20c"), "");
			makeProduct(play.i18n.Messages.get("productName21"), 5.50, 5,
					User.find(2),
					play.i18n.Messages.get("productDescription21"), 11,
					play.i18n.Messages.get("productImagePath21"), "");
			makeProduct(play.i18n.Messages.get("productName22"), 55.00, 1,
					User.find(3),
					play.i18n.Messages.get("productDescription22"), 9,
					play.i18n.Messages.get("productImagePath22"), "");
			makeProduct(play.i18n.Messages.get("productName23"), 17.99, 20,
					User.find(4),
					play.i18n.Messages.get("productDescription23"), 13,
					play.i18n.Messages.get("productImagePath23"), "");
		}

		/**
		 * Creating 8 different FAQ's
		 */

		if (!FAQ.find("I can't get items shipped until Monday and I'm afraid of hurting my top-rated seller qualification. How should I go about this?")) {
			FAQ.createFAQ(play.i18n.Messages.get("FAQQuestion1"),
					play.i18n.Messages.get("FAQAnswers1"));
			FAQ.createFAQ(play.i18n.Messages.get("FAQQuestion2"),
					play.i18n.Messages.get("FAQAnswers2"));
			FAQ.createFAQ(play.i18n.Messages.get("FAQQuestion3"),
					play.i18n.Messages.get("FAQAnswers3"));
			FAQ.createFAQ(play.i18n.Messages.get("FAQQuestion4"),
					play.i18n.Messages.get("FAQAnswers4"));
			FAQ.createFAQ(play.i18n.Messages.get("FAQQuestion5"),
					play.i18n.Messages.get("FAQAnswers5"));
			FAQ.createFAQ(play.i18n.Messages.get("FAQQuestion6"),
					play.i18n.Messages.get("FAQAnswers6"));
			FAQ.createFAQ(play.i18n.Messages.get("FAQQuestion7"),
					play.i18n.Messages.get("FAQAnswers7"));
			FAQ.createFAQ(play.i18n.Messages.get("FAQQuestion8"),
					play.i18n.Messages.get("FAQAnswers8"));
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
			Blog.createBlog("bitBay started",
					play.i18n.Messages.get("BlogMessage")
							+ "Kind Regards bitBay team.", "images/logo.png",
					1, "Admin");
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
	 * Shortening the code for creating a user on messages start
	 * 
	 * @param email
	 *            String User's email
	 * @param password
	 *            String User's password
	 * @param isAdmin
	 *            boolean Is the user an admin user
	 * @param isVerified
	 *            boolean Is the user verified
	 * @param street
	 *            String User's street address
	 * @param nickname
	 *            String User's nickname
	 * @param addInfo
	 *            boolean Has the user filled additional info
	 * @param rating
	 *            double User's rating
	 * @param isBlogger
	 *            boolean Is the user a blogger
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
	 * Shortening the code for creating an order on messages start
	 * 
	 * @param userId
	 *            int User's id for finding the cart
	 * @param productId1
	 *            int Product's id to add to the order
	 * @param productId2
	 *            int Product's id to add to the order
	 * @param token
	 *            String Orders' token
	 * @param priceđ
	 *            double Orders' price
	 * @param orderNumber
	 *            int Number that finds that specific order
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
	 *            String Product's name
	 * @param price
	 *            double Product's price
	 * @param quantity
	 *            int Product's quantity
	 * @param owner
	 *            User Product's owner
	 * @param description
	 *            String Product's description
	 * @param categoryId
	 *            int Product's category
	 * @param imagePath
	 *            String Product's picture
	 * @param tag
	 *            String Product's tag
	 */
	private void makeProduct(String name, double price, int quantity,
			User owner, String description, int categoryId, String imagePath,
			String tag) {
		Product p = Product.create(name, price, quantity, owner, description,
				categoryId);
		Image i = new Image();
		i.image = imagePath;
		i.product = p;
		Tag.create(p, Category.find(p.categoryId).name);
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
	 *            String Product's name
	 * @param price
	 *            double Product's price
	 * @param quantity
	 *            int Product's quantity
	 * @param owner
	 *            User Product's owner
	 * @param description
	 *            String Product's description
	 * @param categoryId
	 *            int Product's category
	 * @param imagePath1
	 *            String Product's picture 1
	 * @param imagePath2
	 *            String Product's picture 2
	 * @param imagePath3
	 *            String Product's picture 3
	 * @param tag
	 *            String Product's tag
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
		Tag.create(p, Category.find(p.categoryId).name);
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
