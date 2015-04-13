package ws.controllers;

import java.util.Date;
import java.util.List;

import javax.mail.internet.ParseException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import play.mvc.Http.Context;
import helpers.*;
import models.*;
import play.Logger;
import play.data.Form;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.*;

public class JsonController extends Controller {

	static Form<Product> productForm= new Form <Product>(Product.class);
	static Form<User> userForm = new Form<User>(User.class);

	
	@BodyParser.Of(play.mvc.BodyParser.Json.class)
	public static Result registerPage() {
		String email = session().get("email");
		return ok(toregister.render(userForm, email, FAQ.all()));
	}
	
	@BodyParser.Of(play.mvc.BodyParser.Json.class)
	public static Result loginPage() {
		String email = session().get("email");
		return ok(logintest.render(email, FAQ.all()));	}
	
	/**
	 * TODO comments
	 * @return
	 */
	public static Result registration(){
		
		JsonNode json = request().body().asJson();
		String email = json.findPath("email").textValue();
		String password = json.findPath("password").textValue();
		String confirmPassword = json.findPath("confirmPassword").textValue();
		if(email.equals(null) || email.isEmpty()){
			Logger.info("Login error, email not valid");
			ObjectNode message = Json.newObject();
			return badRequest(message.put("error", "Email not valid."));
		}
		if(password.equals(null) || password.isEmpty()){
			Logger.info("Login error, password not valid");
			ObjectNode message = Json.newObject();
			return badRequest(message.put("error", "Password not valid."));
		}
		if(confirmPassword.equals(null) || confirmPassword.isEmpty() || !password.equals(confirmPassword)){
			Logger.info("Login error, password not valid");
			ObjectNode message = Json.newObject();
			return badRequest(message.put("error", "Password cofirmation error."));
		}
		User newUser = User.createUser(email, password);
		return ok(JsonHelper.userToJson(newUser));	
		}
	
	
	public static Result login(){
		
		JsonNode json = request().body().asJson();
		String email = json.findPath("email").textValue();
		String password = json.findPath("password").textValue();
		if(email.equals(null) || email.isEmpty()){
			Logger.info("Login error, email not valid");
			ObjectNode message = Json.newObject();
			return badRequest(message.put("error", "Email not valid."));
		}
		if(password.equals(null) || password.isEmpty()){
			Logger.info("Login error, password not valid");
			ObjectNode message = Json.newObject();
			return badRequest(message.put("error", "Password not valid."));
		}
		if (User.checkLogin(email, password) == true) {
			User user = User.find(email);
			session().clear();
			session("email", user.email);
			return ok(JsonHelper.userToJson(user));
		}
		else{
			ObjectNode message = Json.newObject();
			return badRequest(message.put("error", "You are not registered user. Please register first!"));

		}
	}
	
	
	@BodyParser.Of(play.mvc.BodyParser.Json.class)
	public static Result addProductPage(int id) {
		String email = session().get("email");
		//User user=User.find(email);
		
		return ok(addproduct.render(email,id,productForm, FAQ.all()));
	}
	
	@Security.Authenticated(ServiceAuth.class)
	public static Result addProduct() {
		JsonNode json = request().body().asJson();
		String name = json.findPath("name").textValue();
		Double price = json.findPath("price").doubleValue();
		int quantity = json.findPath("quantity").intValue();
		String description = json.findPath("description").textValue();
		String image=null;
		User u = new ServiceAuth().getCurrentUser(ctx());
		if(u == null)
			u = Session.getCurrent(ctx());
		if(u == null)
		return badRequest("No user");
		Product newProduct = Product.create(name, price, quantity,description,image,u);
		
		return ok(JsonHelper.productToJson(newProduct));
	}
	
	
	public static Result allProducts() {
	//	String email = session().get("email");
		Logger.info("Products page opened");
		return ok(JsonHelper.productListToJson(Product.productList()));
	}
	
	
	public static Result allUsers() {
		//	String email = session().get("email");
			Logger.info("Users page opened");
			return ok(JsonHelper.usersListToJson(User.all()));
		}
	
	
	public static Result viewProduct(int id) {
		Product currProduct = Product.find(id);
		return ok(JsonHelper.productToJson(currProduct));
	}
	
	 public static Result userProducts(int id) {
		 User u = User.find(id);
		  return ok(JsonHelper.productListToJson(Product.userProducts(u)));
	 }
	 
	 public static Result searchProducts(String name) {
			List<Product> searchProducts = Product.find.where().ilike("name", "%" + name + "%").findList();
			if (searchProducts.isEmpty()) {
				return badRequest(JsonHelper.messageToJSon("info","No search results"));
			}
			return ok(JsonHelper.productListToJson(searchProducts));

		}
	 
	 public static Result searchUsers(String name) {
			List<User> searchUsers = User.find.where().ilike("name", "%" + name + "%").findList();
			if (searchUsers.isEmpty()) {
				return badRequest(JsonHelper.messageToJSon("info","No search results"));
			}
			return ok(JsonHelper.usersListToJson(searchUsers));
}

	 public static Result searchCategories(String name) {
			List<Category> searchCategories = Category.find.where().ilike("name", "%" + name + "%").findList();
			if (searchCategories.isEmpty()) {
				return badRequest(JsonHelper.messageToJSon("info","No search results"));
			}
			return ok(JsonHelper.categoryListToJson(searchCategories));
}
	 
	
}
