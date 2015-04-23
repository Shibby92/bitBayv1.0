package controllers;

import helpers.*;

import java.util.Iterator;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.swing.ImageIcon;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.io.Files;

import controllers.UserLoginApplication.Contact;
import models.*;
import play.Logger;
import play.Play;
import play.data.*;
import play.db.ebean.Model.Finder;
import play.libs.F.Function;
import play.libs.F.Promise;
import play.libs.ws.WS;
import play.libs.ws.WSResponse;
import play.mvc.*;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import views.html.*;

/**
 * Controls the ad application Redirects on the pages when needed
 * @author eminamuratovic
 */
public class ProductApplication extends Controller {

	static Finder<Integer, Product> find = new Finder<Integer, Product>(
			Integer.class, Product.class);

	static Form<User> loginUser = new Form<User>(User.class);
	static Form<Product> productForm = new Form<Product>(Product.class);
	
	/**
	 * user picks new category for his product
	 * @return result
	 */
	@Security.Authenticated(UserFilter.class)
	public static Result pickCategory() {

		DynamicForm form = Form.form().bindFromRequest();

		String category = form.data().get("category");
		Logger.info(category + " category has been picked");
		return redirect("/addproduct/" + Category.categoryId(category));
	}

	/**
	 * adds additional info to product
	 * creates new product
	 * returns user to his home page
	 * @param id int id of the product
	 * @return result
	 */
	@Security.Authenticated(UserFilter.class)
	public static Product find(int id) {
		return find.byId(id);
	}
	
	/**
	 * Adds additional info to product
	 * (name, price, quantity, images and description)
	 * @param id int id of the category
	 * @return result
	 */
	@Security.Authenticated(UserFilter.class)
	public static Result addAdditionalInfo(int id) {

		DynamicForm form = Form.form().bindFromRequest();
		String name = form.get("name");
		double price = Double.valueOf(form.get("price"));
		int quantity = Integer.valueOf(form.get("quantity"));
		String description = form.get("description");
		List<models.Image> image_urls = savePicture(id);

		if (image_urls == null) {
			return redirect("/addproductpage/" + id);
	
		}
		
		if(image_urls.size()==0){
			flash("pictureSelect", "You must select a picture for your product!");
			return redirect("/addproductpage/"+id);
		}
		

		Product.create(name, price, quantity,
				User.find(session().get("email")), description, id, image_urls);

		Logger.info("User with email: " + session().get("email")
				+ "created product with name: " + name);
		flash("success", "You have successfuly added product!");
		return redirect("/homepage");
	}
	

	/**
	 * opens a page with all products
	 * @return result
	 */
	public static Result productPage() {
		String email = session().get("email");
		Logger.info("Product page opened");
		return ok(productpage.render(email, Product.productList(), FAQ.all()));
	}

	/**
	 * opens a page with all of the categories
	 * @param name String name of the category
	 * @return result
	 */
	public static Result category(String name) {
		String email = session().get("email");
		Logger.info("Category page list opened");
		return ok(category.render(email, name, Product.listByCategory(name),
				FAQ.all()));
	}

	/**
	 * opens a page where user can pick category for his product
	 * @return result
	 */
	@Security.Authenticated(UserFilter.class)
	public static Result toPickCategory() {
		Logger.info("Opened page for adding category for product");
		String email = session().get("email");
		return ok(addproductcategory.render(email, Category.list(), FAQ.all()));

	}
	

	/**
	 * opens a page where user adds info for his product
	 * @param id int id of the category
	 * @return result
	 */
	@Security.Authenticated(UserFilter.class)
	public static Result toInfo(int id) {
		Logger.info("Opened page for adding product");
		String email = session().get("email");
		return ok(addproduct.render(email, id, productForm, FAQ.all()));
	}

	/**
	 * method that delete product and redirect to other products
	 * @param id int id of the product
	 * @return result
	 */
	@Security.Authenticated(UserFilter.class)
	public static Result deleteProduct(int id) {
		Product p = Product.find(id);
		p.deleted = true;
		p.update();
		Logger.warn("product with id: " + id + " has been deleted");
		flash("success","Youy have successfuly deleted product");
		return redirect("/profile");

	}
	
	
	/**
	 * opens a page where user can update his product
	 * @param id int id of the product
	 * @return result
	 */
	@Security.Authenticated(UserFilter.class)
	public static Result updateProduct(int id) {
		String email = session().get("email");
		Logger.info("Opened page for updating product");
		return ok(updateproduct.render(email, Product.find(id), FAQ.all()));
	}
	
	
	/**
	 * gets the data from the updated product
	 * saves it in database
	 * @param id int id of the product
	 * @return result
	 */
	@Security.Authenticated(UserFilter.class)
	public static Result updateP(int id) {
		Logger.info("Opened page for updating product");

		Product updateProduct = Product.find(id);
		if (updateProduct.sold == true) {
			updateProduct.sold = false;
		}

		if (productForm.hasErrors()) {
			return TODO;
		} else {

			updateProduct.name = productForm.bindFromRequest().field("name")
					.value();
			updateProduct.price = Double.parseDouble(productForm
					.bindFromRequest().field("price").value());
			updateProduct.description = productForm.bindFromRequest()
					.field("description").value();

			List<models.Image> image_urls = updatePicture(id);

			if (image_urls != null) {
				updateProduct.images = image_urls;
			}

			updateProduct.quantity = Integer.parseInt(productForm
					.bindFromRequest().field("quantity").value());
			updateProduct.update();

			Logger.info("Product with id: " + id + " has been updated");
			flash("success", "Product successfully updated!");
			if (User.find(session().get("email")).admin)
				return redirect("/profile/" + User.find(session().get("email")).id);
			return redirect("/myproducts/"
					+ User.find(session().get("email")).id);
		}
	}
	
	
	
	/**
	 * updates picture on given product
	 * @param id int id of the product
	 * @return result 
	 */
	@Security.Authenticated(UserFilter.class)
	public static List<models.Image> updatePicture(int id) {

		Product updateProduct = ProductApplication.find(id);
		Product.deleteImage(updateProduct);
		MultipartFormData body = request().body().asMultipartFormData();
		List<FilePart> fileParts = body.getFiles();
		List<models.Image> imgs = new ArrayList<models.Image>();
		if (fileParts == null || fileParts.size() == 0) {
			Logger.debug("File part is null");
			return null;
		}
		for (FilePart filePart : fileParts) {
			if (filePart == null) {
				Logger.debug("File part is null");
				return null;
			}
			if (fileParts.size() > 5) {
				Logger.debug("User tried to update more than 5 images");
				return null;
			}
			Logger.debug("Content type: " + filePart.getContentType());
			Logger.debug("Key: " + filePart.getKey());
			File image = filePart.getFile();
			String extension = filePart.getFilename().substring(
					filePart.getFilename().lastIndexOf('.'));
			extension.trim();

			if (!extension.equalsIgnoreCase(".jpeg")
					&& !extension.equalsIgnoreCase(".jpg")
					&& !extension.equalsIgnoreCase(".png")) {
				Logger.error("Image type not valid");
				flash("error", "Image type not valid");
				return null;
			}
			double megabiteSyze = (double) ((image.length() / 1024) / 1024);
			if (megabiteSyze > 2) {
				Logger.debug("Image size not valid ");
				flash("error", "Image size not valid");
				return null;
			}

			try {
				models.Image img = new models.Image();

				File profile = new File("./public/images/Productimages/"
						+ UUID.randomUUID().toString() + extension);

				Logger.debug(profile.getPath());
				String image_url = "images" + File.separator + "Productimages"
						+ File.separator + profile.getName();
				img.image = image_url;
				img.product = updateProduct;

				Files.move(image, profile);
				ImageIcon tmp = new ImageIcon(image_url);
				Image resize = tmp.getImage();
				resize.getScaledInstance(800, 600, Image.SCALE_DEFAULT);

				imgs.add(img);

			} catch (IOException e) {
				Logger.error("Failed to move file");
				e.printStackTrace();
				flash("error", "Failed to move file");
				return null;
			}
		}

		return imgs;

	}
	
	/**
	 * saves picture on given product
	 * @param id int id of the product
	 * @return result 
	 */
	@Security.Authenticated(UserFilter.class)
	public static List<models.Image> savePicture(int id) {
		List<models.Image> image_urls = new ArrayList<models.Image>();
		MultipartFormData body = request().body().asMultipartFormData();
		List<FilePart> fileParts = body.getFiles();
		if (fileParts == null || fileParts.size() == 0) {
			flash("error", "You need to upload image!");
			Logger.debug("File part is null");
			return null;
		}
		for (FilePart filePart : fileParts) {
			if (filePart == null) {
				flash("error", "You need to upload image!");
				Logger.debug("File part is null");
				return null;
			}
			if (fileParts.size() > 5) {
				flash("error", "Use less than 5 images!");
				Logger.debug("User tried to save more than 5 images");
				return null;
			}
			Logger.debug("Filepart: " + filePart.toString());
			Logger.debug("Content type: " + filePart.getContentType());
			Logger.debug("Key: " + filePart.getKey());
			File image = filePart.getFile();
			String extension = filePart.getFilename().substring(
					filePart.getFilename().lastIndexOf('.'));
			extension.trim();

			if (!extension.equalsIgnoreCase(".jpeg")
					&& !extension.equalsIgnoreCase(".jpg")
					&& !extension.equalsIgnoreCase(".png")) {
				Logger.error("Image type not valid");
				flash("error", "Image type not valid");
				return null;
			}
			double megabiteSyze = (double) ((image.length() / 1024) / 1024);
			if (megabiteSyze > 2) {
				Logger.debug("Image size not valid ");
				flash("error", "Image size not valid");
				return null;
			}

			try {
				models.Image img = new models.Image();

				File profile = new File("./public/images/Productimages/"
						+ UUID.randomUUID().toString() + extension);

				Logger.debug(profile.getPath());
				String image_url = "images" + File.separator + "Productimages/"
						+ profile.getName();

				img.image = image_url;

				Files.move(image, profile);
				ImageIcon tmp = new ImageIcon(img.image);
				Image resize = tmp.getImage();
				resize.getScaledInstance(800, 600, Image.SCALE_DEFAULT);

				image_urls.add(img);

			} catch (IOException e) {
				flash("error", "Failed to move file");
				Logger.error("Failed to move file");
				Logger.debug(e.getMessage());
				return null;
			}
		}
		return image_urls;

	}
	/**
	 * Page of the product
	 * @param id int id of the product
	 * @return result
	 */
	public static Result itemPage(int id) {
		if (session().get("email") == null)
			Logger.info("Guest has opened item with id: " + id);
		else
			Logger.info("User with email: " + session().get("email")
					+ " opened item with id: " + id);

		Product p = Product.find(id);
		return ok(itempage.render(session("email"), Product.find(id),
				FAQ.all(), models.Image.photosByProduct(p), Comment.all(), Category.list()));
	}
	
	/**
	 * opens page with all product of one user
	 * @param id int id of the user
	 * @return result
	 */
	@Security.Authenticated(UserFilter.class)
	public static Result myProducts(int id) {
		String email = session().get("email");
		Logger.info("User with email: " + session().get("email")
				+ " has opened his products");
		return ok(myproducts.render(email, Product.myProducts(id), FAQ.all()));

	}
	
	
	
	
	
	/********************************************************************
	 ************************* CART SECTION ****************************/

	static Finder<Integer, Cart> cartFinder = new Finder<Integer, Cart>(
			Integer.class, Cart.class);

	/**
	 * page with all products from one cart
	 * @param id int id of the cart
	 * @return result
	 */
	@Security.Authenticated(UserFilter.class)
	public static Result cartPage(int id) {
		String email = session().get("email");
		return ok(cartpage.render(email, Cart.getCart(id), FAQ.all()));
	}

	/**
	 * gets info from one product
	 * then puts it in cart
	 * @param id int id of the product
	 * @return result
	 */
	@Security.Authenticated(UserFilter.class)
	public static Result productToCart(int id) {
		int orderedTotalQta = 0;
		String email = session().get("email");
		Product p = find.byId(id);
		if (session().isEmpty()) {
			flash("guest", "Please log in to buy stuff!");
			return redirect("/login");
		}
		int userid = User.findUser.where().eq("email", session().get("email"))
				.findUnique().id;
		Cart cart = Cart.getCart(email);

		Logger.info(String.valueOf(userid));
		DynamicForm form = Form.form().bindFromRequest();
		int orderedQuantity = Integer.valueOf(form.get("orderedQuantity"));
		orderedTotalQta = orderedQuantity + p.getOrderedQuantity();
		if (orderedTotalQta > p.getQuantity()) {
			flash("excess",
					"You cannot order quantity that exceeds one available on stock!");
			p.setOrderedQuantity(p.getOrderedQuantity());

			return redirect("/itempage/" + id);
		} else if(orderedQuantity==0){
			flash("excess",
					"You cannot order zero quantity!");
			return redirect("/itempage/" + id);
			
		}else{
			p.setOrderedQuantity(orderedTotalQta);
			p.amount = p.getPrice() * p.getOrderedQuantity();
			Logger.info(String.valueOf("Naruceno: " + orderedQuantity));
			p.update();
			p.save();
			if (cart.productList.contains(p)) {
				Cart.addQuantity(p, cart, orderedQuantity);
				return redirect("/cartpage/" + userid);
				
			} else {
				Cart.addProduct(p, cart);
				Logger.info(String.valueOf("Naruceno posle: "
						+ p.orderedQuantity));
				return redirect("/cartpage/" + userid);
			}
		}
	}
	public static Result changeShippingAddress (int id){
		DynamicForm form= Form.form().bindFromRequest();
		String shipA=form.get("shippingAddress");
		Cart c=Cart.find(id);
		c.shippingAddress=shipA;
		c.update();
		flash("shipSuccess", "Shipping address successfully changed!");
		return ok(cartpage.render(session().get("email"),Cart.find(id),FAQ.all()));
	}

	/**
	 * deletes a product from the cart
	 * @param id int id of the product
	 * @return result
	 */
	@Security.Authenticated(UserFilter.class)
	public static Result deleteProductFromCart(int id) {
		String email = session().get("email");
		Product productDel = find.byId(id);
		Cart cart = productDel.cart;

		cart.productList.remove(productDel);
		cart.update();
		cart.save();
		if (cart.productList.size() < 1) {
			cart.checkout = 0;
			cart.size = 0;
		} else {
			cart.checkout = cart.checkout - productDel.price
					* productDel.getOrderedQuantity();
			if (cart.checkout < 0)
				cart.checkout = 0;
			cart.size = cart.size - productDel.getOrderedQuantity();
		}
		cart.update();
		cart.save();
		productDel.cart = null;
		productDel.setOrderedQuantity(0);
		productDel.update();
		productDel.save();

		return ok(cartpage.render(email, cart, FAQ.all()));

	}
	
	
	
	/***************************************************************/
	/***************************************************************/
	/***************************************************************/
	
	/**
	 * gets info from page where user adds his comment to product
	 * @param id int id of the product
	 * @return result
	 */
	@Security.Authenticated(UserFilter.class)
	public static Result addNewComment(int id) {
		DynamicForm form = Form.form().bindFromRequest();
		Product p = Product.find(id);
		String comment = form.get("comment");
		Comment.createComment(comment, User.find(session().get("email")), p);
		Logger.info("New comment added: " + comment);

		List<Comment> list2 = Comment.all();

		flash("success", "New comment added");
		return ok(itempage.render(session("email"), Product.find(id),
				FAQ.all(), models.Image.photosByProduct(p), list2, Category.list()));

	}
	
	/**
	 * deletes comment from the product
	 * @param id int id of the comment
	 * @param p_id int id of the product
	 * @return result
	 */
	@Security.Authenticated(AdminFilter.class)
	public static Result deleteComment(int id, int p_id) {
		Comment.delete(id);
		Logger.warn("Comment with id: " + id + " has been deleted");
		flash("success", "Comment deleted!");
		return redirect("/itempage/" + p_id);

	}
	
	/**
	 * deletes message from his mail
	 * @param id int id of the message
	 * @return result
	 */
	@Security.Authenticated(UserFilter.class)
	public static Result deleteMessage(int id) {
		Message.delete(id);
		Logger.warn("Message with id: " + id + " has been deleted");
		flash("success", "Message deleted!");
		return redirect("/profile");

	}
	
	/**
	 * opens page where user replies to another user
	 * @param id int id of the sender
	 * @return result
	 */
	@Security.Authenticated(UserFilter.class)
	public static Result replyToMessagePage(int id) {
		String email = session().get("email");
		Logger.info("User with email: " + session().get("email")
				+ " has opened reply contact page");

		return ok(reply.render(email, FAQ.all(), User.find(id)));
	}
	
	/**
	 * opens message from another user
	 * @param id int id of the message
	 * @return result
	 */
	@Security.Authenticated(UserFilter.class)
	public static Result openMessage(int id) {
		String email = session().get("email");
		Logger.info("User with email: " + session().get("email")
				+ " has opened message from: " + Message.find(id).sender.email);
		return ok(message.render(email, FAQ.all(), Message.find(id)));
	}
	
	/**
	 * gets the data from the reply page
	 * @param id int id of the sender
	 * @return result
	 */
	@Security.Authenticated(UserFilter.class)
	public static Promise<Result> reply(final int id) {
		final String userEmail = session().get("email");
		// need this to get the google recapctha value
		final DynamicForm temp = DynamicForm.form().bindFromRequest();

		/*
		 * send a request to google recaptcha api with the value of our secret
		 * code and the value of the recaptcha submitted by the form
		 */
		Promise<Result> holder = WS
				.url("https://www.google.com/recaptcha/api/siteverify")
				.setContentType("application/x-www-form-urlencoded")
				.post(String.format(
						"secret=%s&response=%s",
						// get the API key from the config file
						Play.application().configuration()
								.getString("recaptchaKey"),
						temp.get("g-recaptcha-response")))
				.map(new Function<WSResponse, Result>() {
					// once we get the response this method is loaded
					public Result apply(WSResponse response) {
						// get the response as JSON
						JsonNode json = response.asJson();
						System.out.println(json);
						System.out.println(temp.get("g-recaptcha-response"));
						Form<Contact> submit = Form.form(Contact.class)
								.bindFromRequest();

						// check if value of success is true
						if (json.findValue("success").asBoolean() == true
								&& !submit.hasErrors()) {

							final String message = temp.get("message");

							ContactHelper.send(userEmail, User.find(id).email,
									message);
							ContactHelper.sendToPage(userEmail,
									User.find(id).email, message, "Contact US message");

							flash("success", "Message sent!");

							Logger.info("User with email: "
									+ session().get("email") + " replied to : "
									+ Product.find(id).owner.email);
							return redirect("/profile/" + User.find(session().get("email")).id);
						} else {

							Logger.info("User with email: "
									+ session().get("email")
									+ " did not confirm its humanity");
							flash("error",
									"You have to confirm that you are not a robot!");
							return ok(reply.render(userEmail, FAQ.all(),
									User.find(id)));

						}
					}
				});
		// return the promisse
		return holder;
	}

	
	/**
	 * User can contact seller
	 * He sends mail to his account and to his mail on the page
	 * @param id int id of the product
	 * @return result
	 */
	@Security.Authenticated(UserFilter.class)
	public static Result contactSellerPage(int id) {
		String email = session().get("email");
		Logger.info("User with email: " + session().get("email")
				+ " has opened contact us page");
		

		return ok(contactseller.render(email, FAQ.all(), Product.find(id), ""));

	}
	
	/**
	 * gets the data from the contact seller page
	 * @param id int id of the product
	 * @return result
	 */
	@Security.Authenticated(UserFilter.class)
	public static Promise<Result> contactSeller(final int id) {
		final String userEmail = session().get("email");

		// need this to get the google recapctha value
		final DynamicForm temp = DynamicForm.form().bindFromRequest();
		final String message = temp.get("message");

		/*
		 * send a request to google recaptcha api with the value of our secret
		 * code and the value of the recaptcha submitted by the form
		 */
		Promise<Result> holder = WS
				.url("https://www.google.com/recaptcha/api/siteverify")
				.setContentType("application/x-www-form-urlencoded")
				.post(String.format(
						"secret=%s&response=%s",
						// get the API key from the config file
						Play.application().configuration()
								.getString("recaptchaKey"),
						temp.get("g-recaptcha-response")))
				.map(new Function<WSResponse, Result>() {
					// once we get the response this method is loaded
					public Result apply(WSResponse response) {
						// get the response as JSON
						JsonNode json = response.asJson();
						System.out.println(json);
						System.out.println(temp.get("g-recaptcha-response"));
						Form<Contact> submit = Form.form(Contact.class)
								.bindFromRequest();

						// check if value of success is true
						if (json.findValue("success").asBoolean() == true
								&& !submit.hasErrors()) {

							final String email = temp.get("email");

							ContactHelper.send(email,
									Product.find(id).owner.email, message);
							ContactHelper.sendToPage(email,
									Product.find(id).owner.email, message, "Message from buyer");

							flash("success", "Message sent!");

							Logger.info("User with email: "
									+ session().get("email")
									+ " has sent message to seller: "
									+ Product.find(id).owner.email);
							return redirect("/contactsellerpage/" + id);
						} else {

							Logger.info("User with email: "
									+ session().get("email")
									+ " did not confirm its humanity");
							flash("error",
									"You have to confirm that you are not a robot!");
							return ok(contactseller.render(userEmail,
									FAQ.all(), Product.find(id), message));

						}
					}
				});
		// return the promisse
		return holder;
	}
	
	@Security.Authenticated(UserFilter.class)
	public static Result renew(int id) {
		Product temp = find.byId(id);
		temp.sold = false;
		temp.order = null;
		temp.quantity=1;
		temp.update();
		flash("renew", "Product " + temp.name
				+ " has been successfully renewed!");
		return redirect("/myproducts/" + User.find(session().get("email")).id);
	}
	
	@Security.Authenticated(UserFilter.class)
	public static Result reportProductPage(int id){
		Logger.info("User " + session().get("email") + " has open reporting product page");
		String message = "";
	return ok(reportpage.render(session().get("email"), id, message));
	}
	
	@Security.Authenticated(UserFilter.class)
	public static Promise<Result> reportProduct(int id) {
		final DynamicForm temp = DynamicForm.form().bindFromRequest();
		final String report = temp.get("report");

		/*
		 * send a request to google recaptcha api with the value of our secret
		 * code and the value of the recaptcha submitted by the form
		 */
		Promise<Result> holder = WS
				.url("https://www.google.com/recaptcha/api/siteverify")
				.setContentType("application/x-www-form-urlencoded")
				.post(String.format(
						"secret=%s&response=%s",
						// get the API key from the config file
						Play.application().configuration()
								.getString("recaptchaKey"),
						temp.get("g-recaptcha-response")))
				.map(new Function<WSResponse, Result>() {
					// once we get the response this method is loaded
					public Result apply(WSResponse response) {
						// get the response as JSON
						JsonNode json = response.asJson();
						System.out.println(json);
						System.out.println(temp.get("g-recaptcha-response"));
						Form<Report> submit = Form.form(Report.class)
								.bindFromRequest();

						// check if value of success is true
						if (json.findValue("success").asBoolean() == true
								&& !submit.hasErrors()) {

							
							Report newReport = Report.report(Product.find(id), User.find(session().get("email")), report);
							for(User u : User.admins()){
							ContactHelper.send(session().get("email"),
									u.email, report, Product.find(id));
							ContactHelper.sendToPage(session().get("email"),
									u.email, report, Product.find(id), "Report product id: " + id);
							}
							
							flash("success", "You have successfuly reported product!");

							Logger.info("User with email: "
									+ session().get("email")
									+ " has reported product with id: "
									+ Product.find(id).id);
							return redirect("/itempage/" + id);
						} else {

							Logger.info("User with email: "
									+ session().get("email")
									+ " did not confirm its humanity");
							flash("error",
									"You have to confirm that you are not a robot!");
							return ok(reportpage.render(session().get("email"), id, report));

						}
					}
				});
		// return the promisse
		return holder;
	}

	
	}
