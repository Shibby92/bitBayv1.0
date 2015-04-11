package controllers.WebServiceControllers;

import java.util.Date;
import java.util.List;

import javax.mail.internet.ParseException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import helpers.*;
import models.*;
import play.Logger;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;
import helpers.AdminFilter;
import helpers.JsonHelper;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
//import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.h2.util.StringUtils;

import com.google.common.io.Files;

import models.Category;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Security;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import views.html.*;

public class JsonController extends Controller{
	
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
		User u = User.createUser(email, password);
		return ok();
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
		return ok();
	}
	
	public static Result listUsersJson(){  
        List<User> users = User.findAll(); 
       // if (request().accepts("text/json"))
        return ok(JsonHelper.usersListJson(users));  
    }  
	
	public static Result listProductsJson(){  
        List<Product> products = Product.findAll(); 
       // if (request().accepts("text/json"))
        return ok(JsonHelper.productListJson(products));  
    }  
	
	public static Result listCategoriesJson(){  
        List<Category> categories = Category.findAll(); 
       // if (request().accepts("text/json"))
        return ok(JsonHelper.categoryListJson(categories));  
    }  
	
}
