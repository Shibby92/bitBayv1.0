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
		
		Product.create(name, price, User.find(session().get("email")),
				description,id,image_url);
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
		Logger.info("Opened page for updating product");
		return ok(updateproduct.render(Product.find(id)));
	}
	
	/**
	 * gets the data from the updated product
	 * saves it in database
	 * @param id int id of the product
	 * @return
	 */
	public static Result update(int id){
		savePicture(id);
		
		Product updateProduct= Product.find(id);
		updateProduct.name=productForm.bindFromRequest().field("name").value();
		updateProduct.price=Double.parseDouble(productForm.bindFromRequest().field("price").value());
		updateProduct.description=productForm.bindFromRequest().field("description").value();
		Product.update(updateProduct);
		Logger.info("Product with id: " + id + " has been updated");
		return redirect("/profile");


		
	}
	
	/**
	 * saves picture in product
	 * @param id int id of the product
	 * @return result
	 */
	public static Result savePicture(int id){
		Product updateProduct = ProductApplication.find(id);
			
		MultipartFormData body = request().body().asMultipartFormData(); 
		FilePart filePart = body.getFile("image_url");
		if(filePart  == null){
			return redirect("/profile");
		}
		Logger.debug("Content type: " + filePart.getContentType());
		Logger.debug("Key: " + filePart.getKey());
		File image = filePart.getFile();
		double megabiteSyze = (image.length()/1024)/1024;
		if(megabiteSyze >2)
			return redirect("/productpage");
		try {
			
			Files.move(image, new File("./public/images/Productimages/"+new Date().toString()+filePart.getFilename()));
			Logger.debug("file should be moved");
		} catch (IOException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String image_url="images/Productimages/"+new Date().toString()+filePart.getFilename();

		updateProduct.image_url=image_url;
		Product.update(updateProduct);
		ImageIcon tmp= new ImageIcon(image_url);
		Image resize = tmp.getImage();
		resize.getScaledInstance(800, 600, Image.SCALE_DEFAULT);
		return redirect("/profile");
	}
	/**
	 * 
	 * @param id int id of the 
	 * @return result
	 */
	public static Result itemPage(int id){
		if(session().get("email") == null)
			Logger.info("Guest has opened item with id: " + id);
		else
			Logger.info("User with email: " + session().get("email") + " opened item with id: " + id);
		return ok(itempage.render(session("email"), Product.find(id), FAQ.all()));
		
	}
	
	public static Result myProducts(int id) {
		Logger.info("User with email: " + session().get("email") + " has opened his products");
		return ok(myproducts.render(Product.myProducts(id)));
	
		
	}
	/********************************************************************
	 ************************* CART SECTION ****************************/

	static Finder<Integer, Cart> cartFinder = new Finder<Integer, Cart>(
			Integer.class, Cart.class);

	
	

	public static Result productToCart(int id) {
		int userid = User.findUser.where().eq("email", session().get("email"))
				.findUnique().id;
		Logger.info(String.valueOf(userid));
		Cart temp = cartFinder.where().eq("userid", userid).findUnique();
		if(temp.productList!=null){
		if(temp.productList.contains(find.byId(id))){
			flash("error","You have added that product already!");
			return ok(cartpage.render(Cart.getCart(userid), FAQ.all()));
		}
		}
		Cart.addProduct(find.byId(id), temp);
		return ok(cartpage.render(Cart.getCart(userid), FAQ.all()));
	}

	public static Result deleteProductFromCart(int id) {
		Product toDelete = find.byId(id);
		Cart cart = toDelete.cart;
		cart.productList.remove(toDelete);
		cart.checkout-=toDelete.price;
		toDelete.cart = null;
		cart.update();
		toDelete.update();
		return ok(cartpage.render(cart, FAQ.all()));

	}
	/***************************************************************/
	}
