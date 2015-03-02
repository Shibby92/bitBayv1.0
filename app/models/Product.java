package models;

import java.util.Date;

import javax.persistence.*;

import play.data.validation.Constraints.*;
import play.db.ebean.Model;

/**
 * Creates product/add
 * @author eminamuratovic
 *
 */
@Entity
public class Product extends Model {

	@Id
	public int id;

	@Required
	public String name;

	@Required
	public int category_id;

	@Required
	public int owner_id;

	@Required
	public Date created;

	@Required
	public int quantity;

	@Required
	public double price;

	@Required
	public String description;

	@Required
	public String image_url;

	static Finder<Integer, Product> find = new Finder<Integer, Product>(
			Integer.class, Product.class);
	
	/**
	 * creates a product
	 * @param name String name of the product
	 * @param category_id int id of the category
	 * @param owner_id int id of the seller
	 * @param created Date date added
	 * @param quantity int quantity of the product
	 * @param price double price of the product
	 * @param description String description of the product
	 * @param image_url String url of the image of the product
	 */
	public Product(String name, int category_id, int owner_id, Date created, int quantity, double price, String description, String image_url) {
		this.name = name;
		this.category_id = category_id;
		this.owner_id = owner_id;
		this.created = created;
		this.quantity = quantity;
		this.price = price;
		this.description = description;
		this.image_url = image_url;
	}
	
	/**
	 * creates a product with all of the parameters
	 * @param name String name of the product
	 * @param category_id int id of the category
	 * @param owner_id int id of the seller
	 * @param created Date date added
	 * @param quantity int quantity of the product
	 * @param price double price of the product
	 * @param description String description of the product
	 * @param image_url String url of the image of the product
	 */
	public static void create(String name, int category_id, int owner_id, Date created, int quantity, double price, String description, String image_url) {
		new Product(name, category_id, owner_id, created, quantity, price, description, image_url).save();
	}
	
	/**
	 * finds a product by his id
	 * @param id int id of the product
	 * @return product
	 */
	public static Product find(int id) {
		return find.byId(id);
	}
	
	
 

}
