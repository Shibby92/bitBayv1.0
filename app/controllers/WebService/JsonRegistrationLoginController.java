package controllers.WebService;


	import java.util.Date;

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

	public class JsonRegistrationLoginController extends Controller {
	
		private static ObjectNode messageToJSon(String tag, String value) {
			ObjectNode jsnNode = Json.newObject();
			jsnNode.put(tag, value);
			return jsnNode;
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
	}