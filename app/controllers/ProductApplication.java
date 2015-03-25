package controllers;

import helpers.*;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.swing.ImageIcon;

import com.google.common.io.Files;

import models.*;
import play.Logger;
import play.data.*;
import play.db.ebean.Model.Finder;
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
		String image_url = "images/bitbaySlika2.jpg";// form.data().get("image url");
		
		Product.create(name, price,
				description,id,image_url);
		savePicture(id);
		Logger.info("User with email: " + session().get("email") + "created product with name: " + name);
		return redirect("/homepage");
	}

	/**
	 * opens a page with all products
	 * @return
	 */
	public static Result productPage(){
		Logger.info("Product page opened");
		return ok(productpage.render(Product.productList(), FAQ.all()));
	}

	/**
	 * opens a page where user can pick category for his product
	 * @return
	 */
	public static Result toPickCategory() {
		Logger.info("Opened page for adding category for product");
		return ok(addproductcategory.render(session().get("email"),Category.list(),FAQ.all()));
	}

	/**
	 * opens a page where user adds info for his product
	 * @param id int id of the category
	 * @return
	 */
	public static Result toInfo(int id) {
		Logger.info("Opened page for adding product");
		return ok(addproduct.render(session().get("email"),id,productForm, FAQ.all()));
	}

	/**
	 * method that delete product and redirect to other products
	 * @param id int id of the product
	 * @return
	 */
	public static Result deleteProduct(int id) {
		
		Product.delete(id);
		Logger.warn("product with id: " + id + " has been deleted");
		return redirect("/productpage");

	}
	
	/**
	 * opens a page where user can update his product
	 * @param id int id of the product
	 * @return
	 */
	public static Result updateProduct(int id){
		Logger.info("Opened page for updating product");
		return ok(updateproduct.render(Product.find(id)));
	}
	
	/**
	 * gets the data from the updated product
	 * saves it in database
	 * @param id int id of the product
	 * @return
	 */
	public static Result update (int id){
		
		updatePicture(id);
		
		Product updateProduct= Product.find(id);
		updateProduct.name=productForm.bindFromRequest().field("name").value();
		updateProduct.price=Double.parseDouble(productForm.bindFromRequest().field("price").value());
		updateProduct.description=productForm.bindFromRequest().field("description").value();
		Product.update(updateProduct);
		Logger.info("Product with id: " + id + " has been updated");
		return redirect("/myproducts");	
	}
	
	/**
	 * updates picture on given product
	 * @param id int id of the product
	 * @return result 
	 */
	public static Result updatePicture(int id){
		
			
		MultipartFormData body = request().body().asMultipartFormData(); 
		FilePart filePart = body.getFile("image_url");
		if(filePart  == null){
			Logger.debug("File part is null");
			flash("error","File part is null");
			if(User.find(session().get("email")).admin)
				return redirect("/profile");
			return redirect("/myproducts");
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
			if(User.find(session().get("email")).admin)
				return redirect("/profile");
			return redirect("/myproducts");
		}
		double megabiteSyze = (double) ((image.length()/1024)/1024);
		if(megabiteSyze >2) {
			Logger.debug("Image size not valid ");
			flash("error", "Image size not valid");
			if(User.find(session().get("email")).admin)
				return redirect("/profile");
			return redirect("/myproducts");
		}

		try {
			Files.move(image, new File("./public/images/Productimages/"+new Date().toString()+filePart.getFilename()));
			String image_url="images" + File.separator + "Productimages" + File.separator + new Date().toString() + filePart.getFilename();
			
			Product updateProduct = ProductApplication.find(id);
			updateProduct.image_url=image_url;
			Product.update(updateProduct);
			ImageIcon tmp= new ImageIcon(image_url);
			Image resize = tmp.getImage();
			resize.getScaledInstance(800, 600, Image.SCALE_DEFAULT);
			flash("success","Your photo have been successfully updated");
			if(User.find(session().get("email")).admin)
				return redirect("/profile");
			return redirect("/myproducts");
			
		} catch (IOException e) {
			Logger.error("Failed to move file");
			e.printStackTrace();
			flash("error", "Failed to move file");
			if(User.find(session().get("email")).admin)
				return redirect("/profile");
			return redirect("/myproducts");
		}

		
	}
	
	/**
	 * saves picture on given product
	 * @param id int id of the product
	 * @return result 
	 */
	public static Result savePicture(int id){
		
			
		MultipartFormData body = request().body().asMultipartFormData(); 
		FilePart filePart = body.getFile("image_url");
		if(filePart  == null){
			Logger.debug("File part is null");
			if(User.find(session().get("email")).admin)
				return redirect("/profile");
			return redirect("/myproducts");
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
			if(User.find(session().get("email")).admin)
				return redirect("/profile");
			return redirect("/myproducts");
		}
		double megabiteSyze = (double) ((image.length()/1024)/1024);
		if(megabiteSyze >2) {
			Logger.debug("Image size not valid ");
			flash("error", "Image size not valid");
			if(User.find(session().get("email")).admin)
				return redirect("/profile");
			return redirect("/myproducts");
		}

		try {
			Files.move(image, new File("./public/images/Productimages/"+new Date().toString()+filePart.getFilename()));
			String image_url="images" + File.separator + "Productimages" + File.separator + new Date().toString() + filePart.getFilename();
			
			ImageIcon tmp= new ImageIcon(image_url);
			Image resize = tmp.getImage();
			resize.getScaledInstance(800, 600, Image.SCALE_DEFAULT);
			if(User.find(session().get("email")).admin)
				return redirect("/profile");
			return redirect("/myproducts");
			
		} catch (IOException e) {
			Logger.error("Failed to move file");
			e.printStackTrace();
			if(User.find(session().get("email")).admin)
				return redirect("/profile");
			return redirect("/myproducts");
		}

		
	}
	
	/**
	 * Page of the product
	 * @param id int id of the product
	 * @return result
	 */
	public static Result itemPage(int id){
		return ok(itempage.render(session("email"), Product.find(id), FAQ.all()));
		
	}

}
