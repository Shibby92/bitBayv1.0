package ws.controllers;

import java.util.Date;
import javax.mail.internet.ParseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import helpers.Session;
import helpers.ServiceAuth;
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

	
	
	@BodyParser.Of(play.mvc.BodyParser.Json.class)
	public static Result registerPage() {
		return ok(toregister.render());
	}
	
	@BodyParser.Of(play.mvc.BodyParser.Json.class)
	public static Result loginPage() {
		return ok(logintest.render(null));
	}
	
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
	public static Result addProductPage() {
		return ok(addproduct.render());
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
	
	public static Result viewProduct(int id) {
		Product currProduct = Product.find(id);
		return ok(JsonHelper.productToJson(currProduct));
	}
	
	 public static Result userProducts(int id) {
		 User u = User.find(id);
		  return ok(JsonHelper.productListToJson(Product.userProducts(u)));
	 }
	
}
