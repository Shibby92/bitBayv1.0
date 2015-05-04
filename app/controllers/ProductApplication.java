package controllers;

import helpers.*;
import java.awt.Image;
import java.io.*;
import java.util.*;

import javax.swing.ImageIcon;

import models.*;
import play.*;
import play.data.*;
import play.db.ebean.Model.Finder;
import play.libs.F.*;
import play.libs.ws.*;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.*;
import views.html.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.io.Files;


// TODO: Auto-generated Javadoc
/**
 * The Class ProductApplication
 */
public class ProductApplication extends Controller {

	/** The find. */
	static Finder<Integer, Product> find = new Finder<Integer, Product>(
			Integer.class, Product.class);

	/** The login user. */
	static Form<User> loginUser = new Form<User>(User.class);
	
	/** The product form. */
	static Form<Product> productForm = new Form<Product>(Product.class);
	
	/** The cart finder. */
	static Finder<Integer, Cart> cartFinder = new Finder<Integer, Cart>(
			Integer.class, Cart.class);
	
	/**
	 * User picks new category for his product.
	 *
	 * @return the result
	 */
	@Security.Authenticated(UserFilter.class)
	public static Result pickCategory() {

		DynamicForm form = Form.form().bindFromRequest();

		String category = form.data().get("category");
		Logger.info(category + " category has been picked");
		return redirect("/addproduct/" + Category.categoryId(category));
	}

	/**
	 * Adds additional info to product.
	 * Creates new product.
	 *
	 * @param id int id of the product
	 * @return the result
	 */
	@Security.Authenticated(UserFilter.class)
	public static Product find(int id) {
		return find.byId(id);
	}
	
	/**
	 * Adds additional info to product
	 * (name, price, quantity, images and description).
	 *
	 * @param id int id of the category
	 * @return int result
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

		if (image_urls.size() == 0) {
			flash("pictureSelect",
					"You must select a picture for your product!");
			return redirect("/addproductpage/" + id);
		}

		Product.create(name, price, quantity,
				User.find(session().get("email")), description, id, image_urls);

		Logger.info("User with email: " + session().get("email")
				+ "created product with name: " + name);
		flash("success", "You have successfuly added product!");
		return redirect("/homepage");
	}


	/**
	 * Opens a page where user can pick category for his product.
	 *
	 * @return the result
	 */
	@Security.Authenticated(UserFilter.class)
	public static Result toPickCategory() {
		Logger.info("Opened page for adding category for product");
		String email = session().get("email");
		return ok(addproductcategory.render(email, Category.list(), FAQ.all()));

	}
	

	/**
	 * Opens a page where user adds info for his product.
	 *
	 * @param id int id of the category
	 * @return the result
	 */
	@Security.Authenticated(UserFilter.class)
	public static Result toInfo(int id) {
		Logger.info("Opened page for adding product");
		String email = session().get("email");
		return ok(addproduct.render(email, id, productForm, FAQ.all()));
	}

	/**
	 * Deletes product.
	 *
	 * @param id int id of the product
	 * @return the result
	 */
	@Security.Authenticated(UserFilter.class)
	public static Result deleteProduct(int id) {
		Product p = Product.find(id);
		p.deleted = true;
		p.update();
		Logger.warn("Product with id: " + id + " has been deleted");
		flash("success", "You have successfuly deleted product");
		return redirect("/profile");

	}
	
	
	/**
	 * Opens a page where user can update his product.
	 *
	 * @param id int id of the product
	 * @return the result
	 */
	@Security.Authenticated(UserFilter.class)
	public static Result updateProduct(int id) {
		String email = session().get("email");
		Logger.info("Opened page for updating product");
		return ok(updateproduct.render(email, Product.find(id), FAQ.all()));
	}
	
	
	/**
	 * Gets the data from the updated product.
	 * Saves it in database.
	 *
	 * @param id int id of the product
	 * @return the result
	 */
	@Security.Authenticated(UserFilter.class)
	public static Result updateP(int id) {
		Logger.info("Opened page for updating product");

		Product updateProduct = Product.find(id);

		List<models.Image> image_urls = updatePicture(id);

		if (image_urls == null) {
			return redirect("/updateproduct/" + id);

		}

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

			if (image_urls != null) {
				updateProduct.images = image_urls;
			}

			updateProduct.quantity = Integer.parseInt(productForm
					.bindFromRequest().field("quantity").value());
			updateProduct.update();

			Logger.info("Product with id: " + id + " has been updated");
			flash("success", "Product successfully updated!");
			if (User.find(session().get("email")).admin)
				return redirect("/profile/"
						+ User.find(session().get("email")).id);
			return redirect("/profile");
		}
	}
	
	
	
	/**
	 * Updates picture on given product.
	 *
	 * @param id int id of the product
	 * @return list of images
	 */
	@Security.Authenticated(UserFilter.class)
	public static List<models.Image> updatePicture(int id) {

		Product updateProduct = ProductApplication.find(id);

		MultipartFormData body = request().body().asMultipartFormData();
		List<FilePart> fileParts = body.getFiles();
		List<models.Image> imgs = new ArrayList<models.Image>();
		if (fileParts == null || fileParts.size() == 0) {
			flash("error", "You need to upload image!");
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
				Product.deleteImage(updateProduct);

				File profile = new File("./public/images/"
						+ UUID.randomUUID().toString() + extension);

				Logger.debug(profile.getPath());
				String image_url = "images" + File.separator
						+ profile.getName();
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
	 * Saves picture on given product.
	 *
	 * @param id int id of the product
	 * @return list of images
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

				File profile = new File("./public/images/"
						+ UUID.randomUUID().toString() + extension);

				Logger.debug(profile.getPath());
				String image_url = "images" + File.separator
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
	 * Opens a page from one product.
	 *
	 * @param id int id of the product
	 * @return the result
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
	 * Renewing product.
	 *
	 * @param id int the id of the product
	 * @return the result
	 */
	@Security.Authenticated(UserFilter.class)
	public static Result renew(int id) {
		Product temp = find.byId(id);
		temp.sold = false;
		temp.order = null;
		temp.quantity = 1;
		temp.update();
		flash("renew", "Product " + temp.name
				+ " has been successfully renewed!");
		return redirect("/myproducts/" + User.find(session().get("email")).id);
	}
	
	/**
	 * Opened page for reporting product.
	 *
	 * @param id int the id of the product
	 * @return the result
	 */
	@Security.Authenticated(UserFilter.class)
	public static Result reportProductPage(int id) {
		Logger.info("User " + session().get("email")
				+ " has open reporting product page");
		String message = "";
		return ok(reportpage.render(session().get("email"), id, message));
	}

	/**
	 * Reports a product.
	 *
	 * @param id int the id of the product
	 * @return the promise
	 */
	@Security.Authenticated(UserFilter.class)
	public static Promise<Result> reportProduct(final int id) {
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
								&& !submit.hasGlobalErrors()) {

							Report newReport = Report.report(Product.find(id),
									User.find(session().get("email")), report);
							for (User u : User.admins()) {
								ContactHelper.send(session().get("email"),
										u.email, report, Product.find(id));
								ContactHelper.sendToPage(
										session().get("email"), u.email,
										report, Product.find(id),
										"Report product id: " + id);
							}

							flash("success",
									"You have successfuly reported product!");

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
							return ok(reportpage.render(session().get("email"),
									id, report));

						}
					}
				});
		// return the promisse
		return holder;
	}
}