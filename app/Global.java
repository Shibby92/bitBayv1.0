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
					"House",
					100000,
					"This house has got four bedrooms, a living-room, a dining-room, a kitchen, a bathroom and a separate toilet.",
					1,"images/bitbaySlika2.jpg");

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
}
