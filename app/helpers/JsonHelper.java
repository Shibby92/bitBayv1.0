package helpers;

import java.util.List;

import play.libs.Json;
import models.FAQ;
import models.Product;
import models.User;
import models.Category;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;


public class JsonHelper {
	
	public static ObjectNode messageToJSon(String tag, String value) {
		ObjectNode jsnNode = Json.newObject();
		jsnNode.put(tag, value);
		return jsnNode;
	}
	
	public static ObjectNode userToJson(User u){
		ObjectNode userNode = Json.newObject();
		userNode.put("id", u.id);
		userNode.put("email", u.email);
		userNode.put("password", u.password);
		userNode.put("username", u.username);
		userNode.put("admin", u.admin);
		userNode.put("verified", u.verification);
		userNode.put("confirmation", u.confirmation);
		ArrayNode products = productListToJson(u.products);
		userNode.put("products", products);
		return userNode;
	}
	
	public static ObjectNode productToJson(Product p){
		ObjectNode productNode = Json.newObject();
		productNode.put("name", p.name);
		productNode.put("description", p.description);
		productNode.put("categoryID", p.category_id);
		productNode.put("price", p.price);
		ObjectNode owner = userToJson(p.owner);
		productNode.put("owner", owner);
		productNode.put("isSold", p.sold);
		productNode.put("id", p.id);
		productNode.put("quantity", p.quantity);
		productNode.put("productImagePath1", p.image1);
		productNode.put("productImagePath1", p.image2);
		productNode.put("productImagePath1", p.image3);

		return productNode;
	}
	
	
	public static ObjectNode categoryToJson(Category c){
		ObjectNode categoryNode = Json.newObject();
		categoryNode.put("id", c.id);
		categoryNode.put("name", c.name);
		return categoryNode;
	}
	
	
	public static ObjectNode faqToJson(FAQ f){
		ObjectNode faqNode = Json.newObject();
		faqNode.put("id", f.id);
		faqNode.put("question", f.question);
		faqNode.put("answer", f.answer);
		return faqNode;
	}
	
	
	
	public static ArrayNode usersListToJson(List<User> users){
		ArrayNode arrayUsersNode = new ArrayNode(JsonNodeFactory.instance);
		for(User u: users){
			ObjectNode user = userToJson(u);
			arrayUsersNode.add(user);
		}
		return arrayUsersNode;
	}
	
	
	
	public static ArrayNode categoryListToJson(List<Category> categories){
		ArrayNode categoriesArrayNode = new ArrayNode(JsonNodeFactory.instance);
		for(Category c: categories){
			ObjectNode category = categoryToJson(c);
			categoriesArrayNode.add(category);
		}
		return categoriesArrayNode;
	}
	
	public static ArrayNode productListToJson(List<Product> products){
		ArrayNode productsArrayNode = new ArrayNode(JsonNodeFactory.instance);
		for(Product p: products){
			ObjectNode product = productToJson(p);
			productsArrayNode.add(product);
		}
		return productsArrayNode;
	}
	
	
	
}
