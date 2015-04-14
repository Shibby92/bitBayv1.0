package helpers;

import java.util.List;

import play.libs.Json;
import models.FAQ;
import models.Product;
import models.User;
import models.Category;
import play.db.ebean.*;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * helper class that enables us to convnert objects of our models to json objects.
 * It has multiple methods, each one is responsible for one model, 
 * plus methods for converting list of objects of our models to json objects
 * @author nerminvucinic
 *
 */
public class JsonHelper {
	
	
	/**
	 * creates json object that represents message that we will send to user
	 * @param tag name for our message
	 * @param value content of message
	 * @return json object(our message in json form)
	 */
	public static ObjectNode messageToJSon(String tag, String value) {
		ObjectNode jsnNode = Json.newObject();
		jsnNode.put(tag, value);
		return jsnNode;
	}
	
	/**
	 * creates the json object that represents object of our user class
	 * @param u user object
	 * @return json object representing object of user class
	 */
	public static ObjectNode userToJson(User u){
		ObjectNode userNode = Json.newObject();
		userNode.put("id", u.id);
		userNode.put("email", u.email);
		userNode.put("password", u.password);
		if(u.username!=null){
		userNode.put("username", u.username);
		}
		//userNode.put("username", u.username);
		//userNode.put("admin", u.admin);
		//userNode.put("verified", u.verification);
		//userNode.put("confirmation", u.confirmation);
		ArrayNode products = productListToJson(u.products);
		userNode.put("products", products);
		return userNode;
	}
	
	
	/**
	 * creates the json object that represents object of our product class
	 * @param p product object
	 * @return json object representing object of product class
	 */
	public static ObjectNode productToJson(Product p){
		ObjectNode productNode = Json.newObject();
		productNode.put("name", p.name);
		productNode.put("description", p.description);
		productNode.put("categoryID", p.category_id);
		productNode.put("price", p.price);
		if(p.owner.username==null){
		productNode.put("owner", p.owner.email);
		}
		else{
		productNode.put("owner", p.owner.username);
		}
		productNode.put("isSold", p.sold);
		productNode.put("id", p.id);
		productNode.put("quantity", p.quantity);
		productNode.put("productImagePath1", p.image1);
		//productNode.put("productImagePath1", p.image2);
		//productNode.put("productImagePath1", p.image3);

		return productNode;
	}
	
	
	/**
	 * creates the json object that represents object of our category class
	 * @param c category object
	 * @return json object representing object of category class
	 */
	public static ObjectNode categoryToJson(Category c){
		ObjectNode categoryNode = Json.newObject();
		categoryNode.put("id", c.id);
		categoryNode.put("name", c.name);
		return categoryNode;
	}
	
	
	/**
	 * creates the json object that represents object of our FAQ class
	 * @param f FAQ object
	 * @return json object representing object of FAQ class
	 */
	public static ObjectNode faqToJson(FAQ f){
		ObjectNode faqNode = Json.newObject();
		faqNode.put("id", f.id);
		faqNode.put("question", f.question);
		faqNode.put("answer", f.answer);
		return faqNode;
	}
	
	
	/**
	 * creates the json object that represents list of objects of our user class
	 * @param users list of object of user class
	 * @return json object representing list of objects of user class
	 */
	public static ArrayNode usersListToJson(List<User> users){
		ArrayNode arrayUsersNode = new ArrayNode(JsonNodeFactory.instance);
		for(User u: users){
			ObjectNode user = userToJson(u);
			arrayUsersNode.add(user);
		}
		return arrayUsersNode;
	}
	
	
	/**
	 * creates the json object that represents list of objects of our category class
	 * @param categories list of objects of category class
	 * @return json object representing list of objects of category class
	 */
	public static ArrayNode categoryListToJson(List<Category> categories){
		ArrayNode categoriesArrayNode = new ArrayNode(JsonNodeFactory.instance);
		for(Category c: categories){
			ObjectNode category = categoryToJson(c);
			categoriesArrayNode.add(category);
		}
		return categoriesArrayNode;
	}
	
	
	/**
	 * creates the json object that represents list of objects of our product class
	 * @param categories list of objects of product class
	 * @return json object representing list of objects of product class
	 */
	public static ArrayNode productListToJson(List<Product> products){
		ArrayNode productsArrayNode = new ArrayNode(JsonNodeFactory.instance);
		for(Product p: products){
			ObjectNode product = productToJson(p);
			productsArrayNode.add(product);
		}
		return productsArrayNode;
	}
	
	
	
}