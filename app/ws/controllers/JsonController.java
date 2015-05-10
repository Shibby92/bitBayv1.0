package ws.controllers;

import helpers.JsonHelper;
import helpers.ServiceAuth;
import helpers.Session;
import models.Product;
import models.User;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import models.*;
import play.Logger;
import play.data.Form;
import play.libs.Json;
import play.mvc.*;
import views.html.*;


/**
 * class used for accepting client json requests and generating apropriate response also in json format
 * @author nerminvucinic
 *
 */

public class JsonController extends Controller {

	static Form<Product> productForm = new Form<Product>(Product.class);
	static Form<User> userForm = new Form<User>(User.class);

	
	/**
	 * method that accepts json post request, parses its body, 
	 * and from parameters get that way creates new User object
	 * @return user object in json format
	 */
	public static Result registration() {

		JsonNode json = request().body().asJson();
		String email = json.findPath("email").textValue();
		String password = json.findPath("password").textValue();
		String confirmPassword = json.findPath("confirmPassword").textValue();
		if (email.equals(null) || email.isEmpty()) {
			Logger.info("Login error, email not valid");
			ObjectNode message = Json.newObject();
			return badRequest(message.put("error", "Email not valid."));
		}
		if (password.equals(null) || password.isEmpty()) {
			Logger.info("Login error, password not valid");
			ObjectNode message = Json.newObject();
			return badRequest(message.put("error", "Password not valid."));
		}
		if (confirmPassword.equals(null) || confirmPassword.isEmpty()
				|| !password.equals(confirmPassword)) {
			Logger.info("Login error, password not valid");
			ObjectNode message = Json.newObject();
			return badRequest(message.put("error",
					"Password cofirmation error."));
		}
		User newUser = User.createUser(email, password);
		return ok(JsonHelper.userToJson(newUser));
	}
	
	/**
	 * method that accepts json post request, parses its body, 
	 * then compares values get from that body with corecponding values in database, 
	 * and if they are matched, creates the new User object
	 * @return user object in json format or appropriate message
	 */
	public static Result login() {

		JsonNode json = request().body().asJson();
		String email = json.findPath("email").textValue();
		String password = json.findPath("password").textValue();
		if (email.equals(null) || email.isEmpty()) {
			Logger.info("Login error, email not valid");
			ObjectNode message = Json.newObject();
			return badRequest(message.put("error", "Email not valid."));
		}
		if (password.equals(null) || password.isEmpty()) {
			Logger.info("Login error, password not valid");
			ObjectNode message = Json.newObject();
			return badRequest(message.put("error", "Password not valid."));
		}
		if (User.checkLogin(email, password) == true) {
			User user = User.find(email);
			//session().clear();
			session("email", email);
			return ok(JsonHelper.userToJson(user));
		} else {
			ObjectNode message = Json.newObject();
			return badRequest(message.put("error",
					"You are not registered user. Please register first!"));

		}
	}

	/**
	 * Method adds product to cart and retuns Json Array
	 * @return
	 */
	public static Result toCart() {

		JsonNode json = request().body().asJson();
		int userId = json.findPath("userId").intValue();
		Logger.debug(String.valueOf("userId"+userId));
		int productId = json.findPath("productId").intValue();
		Logger.debug(String.valueOf("productId"+productId));

		User user = User.find(userId);
		Product product = Product.find(productId);
		
		
		if(user == null) {
			Logger.info("User null");
		}
		if(user == null) {
			Logger.info("Product null");
		}
		
		Cart cart = Cart.getCartbyUserId(userId);
		

		//int orderedQuantity = 1;
		product.setOrderedQuantity(1);
		product.amount = product.price;
		product.update();
		product.save();
		Cart.addProduct(product, cart);

		return ok(JsonHelper.cartToJson(cart.id));
	}
	
	/**
	 * Method gets contents of users cart
	 * @return
	 */
	public static Result getCart() {		
		JsonNode json = request().body().asJson();
		int userId = json.findPath("userId").intValue();
		Logger.debug(String.valueOf("userId"+userId));
		Cart cart = Cart.getCartbyUserId(userId);		
		return ok(JsonHelper.cartToJson(cart.id));
	}
	
	public static Result cartPage(int id) {
		User user = User.find(id);
		if (user == null) {
			Logger.debug(" User in cart null ");
		}
		Logger.debug(user.email + " Cart Page email");
		String email = user.email;
		session("email", email);
		//System.out.println(session().get("email") + "***********");
		return ok(cartpage.render(email, Cart.getCartbyUserId(id), FAQ.all()));
	}
	
	public static Result removeFromCart() {
		Logger.debug("Cart remove from");
		JsonNode json = request().body().asJson();
		int productId = json.findPath("productId").intValue();
		Logger.debug(String.valueOf("prodId "+productId));
		int userId = json.findPath("userId").intValue();
		Product product = Product.find(productId);
		Logger.debug("Name " + product.name);
		Cart cart = product.cart;
		//cart = Cart.removeProductFromCart(productId);
		Logger.debug("Cart size " + String.valueOf(cart.productList.size()));
		cart.productList.remove(product);
		Logger.debug("Cart size 2: " + String.valueOf(cart.productList.size()));
		cart.update();
		cart.save();
		if (cart.productList.size() < 1) {
			cart.checkout = 0;
			cart.size = 0;
		} else {
			cart.checkout = cart.checkout - product.price
					* product.getOrderedQuantity();
			if (cart.checkout < 0)
				cart.checkout = 0;
			cart.size = cart.size - product.getOrderedQuantity();
		}
		cart.update();
		cart.save();
		product.cart = null;
		product.setOrderedQuantity(0);
		product.update();
		product.save();
		return ok(JsonHelper.cartToJson(cart.id));
	}


	/**
	 * method that accepts json post request, parses its body, 
	 * and from parameters get that way creates new product object
	 * @return product object in json format
	 */
	@Security.Authenticated(ServiceAuth.class)
	public static Result addProduct() {
		JsonNode json = request().body().asJson();
		String name = json.findPath("name").textValue();
		Double price = json.findPath("price").doubleValue();
		int quantity = json.findPath("quantity").intValue();
		String description = json.findPath("description").textValue();
		String image = null;
		User u = new ServiceAuth().getCurrentUser(ctx());
		if (u == null)
			u = Session.getCurrent(ctx());
		if (u == null)
			return badRequest("No user");
		Product newProduct = Product.create(name, price, quantity, description,
				image, u);

		return ok(JsonHelper.productToJson(newProduct));
	}

	
	/**
	 * uses JsonHelper class to return list of objects of product class in json format
	 * @return list of objects of product class in json format
	 */
	public static Result allProducts() {
		// String email = session().get("email");
		Logger.info("Products page opened");
		return ok(JsonHelper.productListToJson(Product.productList()));
	}

	
	/**
	 * uses JsonHelper class to return list of objects of user class in json format
	 * @return list of objects of user class in json format
	 */
	public static Result allUsers() {
		// String email = session().get("email");
		Logger.info("Users page opened");
		return ok(JsonHelper.usersListToJson(User.all()));
	}

	
	/**
	 * first finds product object based on passed parameter, 
	 * and then using JsonHelper class creates and returns that object in json format
	 * @param id  of the product object
	 * @return product object in json format
	 */
	public static Result viewProduct(int id) {
		Product currProduct = Product.find(id);
		return ok(JsonHelper.productToJson(currProduct));
	}

	/**
	 * @author Nermin Graca
	 * Method returns object of class User in Json
	 * @param id
	 * @return
	 */
	public static Result viewUser(int id) {
		User currUser = User.find(id);
		return ok(JsonHelper.userToJson(currUser));
	}

	
	/**
	 * first finds the user based in passed parameter, and then
	 * using JsonHelper class creates and returns list of product objects in json format for that user
	 * @param id  of the user
	 * @return returns list of product objects in json format for  user
	 */
	public static Result userProducts(int id) {
		User u = User.find(id);
		return ok(JsonHelper.productListToJson(Product.myProducts(u.id)));
	}

	/*
	 * public static Result searchProducts(String name) { List<Product>
	 * searchProducts = Product.find.where().ilike("name", "%" + name +
	 * "%").findList(); if (searchProducts.isEmpty()) { return
	 * badRequest(JsonHelper.messageToJSon("info","No search results")); }
	 * return ok(JsonHelper.productListToJson(searchProducts));
	 * 
	 * }
	 * 
	 * 
	 * public static Result searchUsers(String name) { List<User> searchUsers =
	 * User.find.where().ilike("name", "%" + name + "%").findList(); if
	 * (searchUsers.isEmpty()) { return
	 * badRequest(JsonHelper.messageToJSon("info","No search results")); }
	 * return ok(JsonHelper.usersListToJson(searchUsers)); }
	 * 
	 * 
	 * public static Result searchCategories(String name) { List<Category>
	 * searchCategories = Category.find.where().ilike("name", "%" + name +
	 * "%").findList(); if (searchCategories.isEmpty()) { return
	 * badRequest(JsonHelper.messageToJSon("info","No search results")); }
	 * return ok(JsonHelper.categoryListToJson(searchCategories)); }
	 */

}
