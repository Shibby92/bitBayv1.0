package controllers;

import helpers.*;

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
 * 
 * @author eminamuratovic
 *
 */
public class ProductApplication extends Controller {
	
	static Finder<Integer, Product> find = new Finder<Integer, Product>(
			Integer.class, Product.class);
	
	static Form<User> loginUser = new Form<User>(User.class);
	static Form<Product> productForm= new Form <Product>(Product.class);
	
	
	// user picks new category for his product
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
	 * @param id
	 * @return
	 */
	public static Product find(int id) {
		return find.byId(id);
	} 
	
	
	@Security.Authenticated(UserFilter.class)
	public static Result addAdditionalInfo(int id) {
		String image2 = null;
		String image3 = null;
		
		//Form <Product> form=productForm.bindFromRequest();
		DynamicForm form = Form.form().bindFromRequest();
		String name = form.get("name");
//		User owner = new User(session().get("username"), form.get("password"));
		
//		DateFormat format = new SimpleDateFormat("MMMM d, yyyy");
//		Date created = new Date();
//		int quantity = 0;// Integer.parseInt(form.data().get("quantity"));
		//double price= 100;
		double price = Double.valueOf(form.get("price"));
		
		String description = form.get("description");
		//String image_url = "images/bitbaySlika2.jpg";// form.data().get("image url");
		
		List<String> image_urls = savePicture(id);
			
		for(String image_url: image_urls) {
			
			if(image_url == null) {
				flash("error", "Image not valid!");
				return redirect("/addproductpage/" + id);
			}
			
	}
		
		String image1 = image_urls.get(0);
		if(image_urls.size()>1){
		if(image_urls.get(1) != null)
			image2 = image_urls.get(1);
		if(image_urls.get(2) != null)
			image3 = image_urls.get(2);
		}
		if(image_urls.size() == 1)
				Product.create(name, price, User.find(session().get("email")),
						description,id,image1);
		if(image_urls.size() == 2)
			Product.create(name, price, User.find(session().get("email")),
					description,id,image1, image2);
		if(image_urls.size() == 3)
			Product.create(name, price, User.find(session().get("email")),
					description,id,image1, image2, image3);
		

				Logger.info("User with email: " + session().get("email") + "created product with name: " + name);
				return redirect("/homepage");
			
	}
			

	/**
	 * opens a page with all products
	 * @return
	 */
	public static Result productPage(){
		String email = session().get("email");
		Logger.info("Product page opened");
		return ok(productpage.render(email, Product.productList(), FAQ.all()));
	}

	/**
	 * opens a page with all of the categories
	 * @param name String name of the category
	 * @return
	 */
	public static Result category(String name) {
		String email = session().get("email");
		Logger.info("Category page list opened");
		return ok(category.render(email,name,Product.listByCategory(name), FAQ.all()));
	}


	/**
	 * opens a page where user can pick category for his product
	 * @return
	 */
	public static Result toPickCategory() {
		Logger.info("Opened page for adding category for product");
		String email = session().get("email");
		return ok(addproductcategory.render(email, Category.list(), FAQ.all()));

	}

	/**
	 * opens a page where user adds info for his product
	 * @param id int id of the category
	 * @return
	 */
	public static Result toInfo(int id) {
		Logger.info("Opened page for adding product");
		String email = session().get("email");
		return ok(addproduct.render(email,id,productForm, FAQ.all()));
	}

	/**
	 * method that delete product and redirect to other products
	 * @param id int id of the product
	 * @return
	 */
	public static Result deleteProduct(int id) {
		
		Product.delete(id);
		Logger.warn("product with id: " + id + " has been deleted");
		return redirect("/profile");

	}
	
	/**
	 * opens a page where user can update his product
	 * @param id int id of the product
	 * @return
	 */
	public static Result updateProduct(int id){
		String email = session().get("email");
		Logger.info("Opened page for updating product");
		return ok(updateproduct.render(email,Product.find(id), FAQ.all()));
	}
	
	/**
	 * gets the data from the updated product
	 * saves it in database
	 * @param id int id of the product
	 * @return
	 */
	public static Result updateP (int id){	
		Logger.info("NALAZIM SE U UPDATE-U");
		Product updateProduct= Product.find(id);
		if(updateProduct.sold==true){
			updateProduct.sold=false;
		}
		updateProduct.name=productForm.bindFromRequest().field("name").value();
		updateProduct.price=Double.parseDouble(productForm.bindFromRequest().field("price").value());
		updateProduct.description=productForm.bindFromRequest().field("description").value();
		String image_url = updatePicture(id);
		if(image_url == null){
			flash("error", "Image not valid!");
			return redirect("/updateproduct/" + id);
		}
		updateProduct.image_url = image_url;
		Product.update(updateProduct);
		Logger.info("Product with id: " + id + " has been updated");
		if(User.find(session().get("email")).admin)
			return redirect("/profile");
		return redirect("/myproducts/" + User.find(session().get("email")).id);	
	}
	
	
	/**
	 * updates picture on given product
	 * @param id int id of the product
	 * @return result 
	 */
	public static String updatePicture(int id){
		MultipartFormData body = request().body().asMultipartFormData(); 
		FilePart filePart = body.getFile("image_url");
		if(filePart  == null){
			Logger.debug("File part is null");
			flash("error","File part is null");
//			if(User.find(session().get("email")).admin)
//				return redirect("/profile");
//			return redirect("/myproducts");
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
//			if(User.find(session().get("email")).admin)
//				return redirect("/profile");
//			return redirect("/myproducts");
			return null;
		}
		double megabiteSyze = (double) ((image.length()/1024)/1024);
		if(megabiteSyze >2) {
			Logger.debug("Image size not valid ");
			flash("error", "Image size not valid");
//			if(User.find(session().get("email")).admin)
//				return redirect("/profile");
//			return redirect("/myproducts");
			return null;
		}

		try {
			File profile = new File("./public/images/Productimages/"
					+ UUID.randomUUID().toString() + extension);
			
			Logger.debug(profile.getPath());
			String image_url = "images" + File.separator + "Productimages"
					+ File.separator
					+ profile.getName();
			
			
			Files.move(image, profile);
			Product updateProduct = ProductApplication.find(id);
			Product.deleteImage(updateProduct);
			updateProduct.image_url=image_url;
			Product.update(updateProduct);
			ImageIcon tmp= new ImageIcon(image_url);
			Image resize = tmp.getImage();
			resize.getScaledInstance(800, 600, Image.SCALE_DEFAULT);
			flash("success","Your photo have been successfully updated");
//			if(User.find(session().get("email")).admin)
//				return redirect("/profile");
//			return redirect("/myproducts");
			return image_url;
			
		} catch (IOException e) {
			Logger.error("Failed to move file");
			e.printStackTrace();
			flash("error", "Failed to move file");
//			if(User.find(session().get("email")).admin)
//				return redirect("/profile");
//			return redirect("/myproducts");
			return null;
		}

		
	}
	
	/**
	 * saves picture on given product
	 * @param id int id of the product
	 * @return result 
	 */
	public static List<String> savePicture(int id){		
		List<String> image_urls = new ArrayList<String>();
		MultipartFormData body = request().body().asMultipartFormData();
		List<FilePart> fileParts = body.getFiles();
		for(FilePart filePart: fileParts) {
		//filePart = body.getFile("image_url");
		if (filePart == null) {
			Logger.debug("File part is null");
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
//			if (User.find(session().get("email")).admin)
//				return redirect("/profile");
//			return redirect("/homepage");
			return null;
		}
		double megabiteSyze = (double) ((image.length() / 1024) / 1024);
		if (megabiteSyze > 2) {
			Logger.debug("Image size not valid ");
			flash("error", "Image size not valid");
//			if (User.find(session().get("email")).admin)
//				return redirect("/profile");
//			return redirect("/homepage");
			return null;
		}
	
		try {
			
			File profile = new File("./public/images/Productimages/"
					+ UUID.randomUUID().toString() + extension);
			
			Logger.debug(profile.getPath());
			String image_url = "images" + File.separator + "Productimages/"
					+ profile.getName();
			
			
			Files.move(image, profile);
			ImageIcon tmp = new ImageIcon(image_url);
			Image resize = tmp.getImage();
			resize.getScaledInstance(800, 600, Image.SCALE_DEFAULT);
			
			
//			if (User.find(session().get("email")).admin)
//				return redirect("/profile");
//			return redirect("/homepage");
			image_urls.add(image_url);

		} catch (IOException e) {
			Logger.error("Failed to move file");
			Logger.debug(e.getMessage());
//			if (User.find(session().get("email")).admin)
//				return redirect("/profile");
//			return redirect("/homepage");
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
	public static Result itemPage(int id){
		if(session().get("email") == null)
			Logger.info("Guest has opened item with id: " + id);
		else
			Logger.info("User with email: " + session().get("email") + " opened item with id: " + id);
		
		Product p = Product.find(id);
		List<String> list = new ArrayList<String>();
		list.add(p.image1);
		list.add(p.image2);
		list.add(p.image3);
		return ok(itempage.render(session("email"), Product.find(id), FAQ.all(), list, Comment.all()));
		
	}
	
	public static Result myProducts(int id) {
		String email = session().get("email");
		Logger.info("User with email: " + session().get("email") + " has opened his products");
		return ok(myproducts.render(email,Product.myProducts(id), FAQ.all()));
	
		
	}
	/********************************************************************
	 ************************* CART SECTION ****************************/

	static Finder<Integer, Cart> cartFinder = new Finder<Integer, Cart>(
			Integer.class, Cart.class);

	public static Result cartPage(int id){
		String email = session().get("email");
		return ok(cartpage.render(email,Cart.getCart(id), FAQ.all()));
	}
	

	public static Result productToCart(int id) {
		String email = session().get("email");
		if(session().isEmpty()){
			flash("guest","Please log in to buy stuff!");
			return redirect("/login");
		}
		int userid = User.findUser.where().eq("email", session().get("email"))
				.findUnique().id;
		Logger.info(String.valueOf(userid));
		Cart temp = cartFinder.where().eq("userid", userid).findUnique();
		if(temp.productList!=null){
		if(temp.productList.contains(find.byId(id))){
			flash("error","You have added that product already!");
			return ok(cartpage.render(email,Cart.getCart(userid), FAQ.all()));
		}
		}
		Cart.addProduct(find.byId(id), temp);
		return ok(cartpage.render(email,Cart.getCart(userid), FAQ.all()));
	}

	public static Result deleteProductFromCart(int id) {
		String email = session().get("email");
		Cart cart=Cart.removeProductFromCart(id);
		return ok(cartpage.render(email,cart, FAQ.all()));

	}
	/***************************************************************/
	
	public static Result addNewComment(int id) {
		DynamicForm form = Form.form().bindFromRequest();
		Product p = Product.find(id);
		String comment = form.get("comment");
		Comment.createComment(comment, User.find(session().get("email")), p);
		Logger.info("New comment added: " + comment);
		flash("success", "New comment added");
	
		List<String> list = new ArrayList<String>();
		list.add(p.image1);
		list.add(p.image2);
		list.add(p.image3);
		List<Comment> list2 = Comment.all();
		
		return ok(itempage.render(session("email"), Product.find(id), FAQ.all(), list, list2));
		
	}
	
	public static Result deleteComment(int id, int p_id){
		Comment.delete(id);
		Logger.warn("Comment with id: " + id + " has been deleted");
		flash("success", "Comment deleted!");
		return redirect("/itempage/" + p_id);
		
	}
	
	public static Result contactSellerPage(int id) {
		String email = session().get("email");
		Logger.info("User with email: " + session().get("email") + " has opened contact us page");
	
		return ok(contactseller.render(email, FAQ.all(), Product.find(id)));

	}
	
	public static Promise<Result> contactSeller(final int id) {
		 final String userEmail = session().get("email");
		//need this to get the google recapctha value
		 final DynamicForm temp = DynamicForm.form().bindFromRequest();
		
		/* send a request to google recaptcha api with the value of our secret code and the value
		 * of the recaptcha submitted by the form */
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

							final String email= temp.get("email");
							final String message= temp.get("message");
							
								ContactHelper.send(email, Product.find(id).owner.email, message);
							
							flash("success", "Message sent!");
							
								Logger.info("User with email: " + session().get("email") + " has sent message to seller: " + Product.find(id).owner.email);
							return redirect("/contactsellerpage/" + id);
						} else {
							
								Logger.info("User with email: " + session().get("email") + " did not confirm its humanity");
							flash("error", "You have to confirm that you are not a robot!");
							return ok(contactseller.render(userEmail, FAQ.all(), Product.find(id) ));


						}
					}
				});
		// return the promisse
		return holder;
	}


	
	
	}
