package models;

import java.util.Date;
import java.util.List;

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
	@MaxLength(20)
	public String name;

	
	public int category_id;

	
	@ManyToOne
	public User owner;

	
	public Date created;

	
	public int quantity;

	@Required
	public double price;

	@Required
	@MinLength(2)
	@MaxLength(144)
	public String description;

	
	public String image_url;

	static Finder<Integer, Product> find = new Finder<Integer, Product>(
			Integer.class, Product.class);
	static Finder<String,Category> findCategory= new Finder<String,Category>(String.class,Category.class);
	
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
	public Product(String name, int category_id, User owner, Date created, int quantity, double price, String description, String image_url) {
		this.name = name;
		this.category_id = category_id;
		this.owner = owner;
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
	public static void create(String name, int category_id, User owner, Date created, int quantity, double price, String description, String image_url) {
		new Product(name, category_id, owner, created, quantity, price, description, image_url).save();
	}

	// Constructor for "required" attributes
	public Product(String name, double price, String description,int id) {
		this.name = name;
		this.price = price;
		this.description = description;
		this.category_id=id;
	}
	public static void create(String name,  double price, String description,int id) {
		new Product(name,  price, description,id).save();
	}

	/**
	 * finds a product by his id
	 * @param id int id of the product
	 * @return product
	 */
	public static Product find(int id) {
		return find.byId(id);
	}

	public static List<Product> productList(){
		return find.all();
	}

	public static List<Product> listByCategory(String category) {
		int id=findCategory.where().eq("name",category).findUnique().id;
		return find.where().eq("category_id",id).findList();
		
	}

	//method which will find id of the product and delete it, else will throw exception,method is used in ProductApplication
	public static void delete(int id) {
		if (find.byId(id) == null) {
			throw new IllegalArgumentException("Produkt ne postoji");
		} else {
			Product temp = find.byId(id);
			temp.delete();
		}
	}
	public static void update(Product product) {
		product.save();
	}

	
	
 

}
