

package models;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.*;

import play.Logger;
import play.data.format.Formats.DateTime;
import play.data.validation.Constraints.*;
import play.db.ebean.Model;
import play.db.ebean.Model.*;


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
	public Cart cart;
	
	@ManyToOne
	public User owner;

	public Date created;

	
	public int quantity;

	@Required
	@Column(scale = 2) 
	public double price;

	@Required
	@MinLength(2)
	@MaxLength(144)
	public String description;

	
	public String image_url;
	
	public String image1;

	public String image2;

	public String image3;
	
	@ManyToOne
	public Orders order;
	
	public boolean sold;
	
	public List<String> image_urls = new ArrayList<String>();
	

	public static Finder<Integer, Product> find = new Finder<Integer, Product>(
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
		this.sold=false;
	}
	
	public Product(String name, double price, User owner, String description,int id, String image1) {
		this.name = name;
		this.price = price;
		this.owner = owner;
		this.description = description;
		this.category_id=id;
		this.image1 = image1;
		this.image_urls.add(image1);
		this.image_url=this.image_urls.get(0);
		this.sold=false;
	}
	
	public Product(String name, double price, User owner, String description,int id, String image1, String image2) {
		this.name = name;
		this.price = price;
		this.owner = owner;
		this.description = description;
		this.category_id=id;
		this.image1 = image1;
		this.image_urls.add(image1);
		this.image2 = image2;
		this.image_urls.add(image2);
		this.image_url=this.image_urls.get(0);
		this.sold=false;
	}
	
	public Product(String name, double price, User owner, String description,int id, String image1, String image2, String image3) {
		this.name = name;
		this.price = price;
		this.owner = owner;
		this.description = description;
		this.category_id=id;
		this.image1 = image1;
		this.image_urls.add(image1);
		this.image2 = image2;
		this.image_urls.add(image2);
		this.image3 = image3;
		this.image_urls.add(image3);
		this.image_url=this.image_urls.get(0);
		this.sold=false;
	}
	
	public static void create(String name,  double price, User owner, String description,int id, String image1) {
		new Product(name,  price, owner, description,id,image1).save();
	}
	
	public static void create(String name,  double price, User owner, String description,int id, String image1, String image2) {
		new Product(name,  price, owner, description,id,image1, image2).save();
	}
	
	public static void create(String name,  double price, User owner, String description,int id, String image1, String image2, String image3) {
		new Product(name,  price, owner, description,id,image1, image2, image3).save();
	}

	public static void create(String name, int category_id, User owner, Date created, int quantity, double price, String description, String image_url) {
		new Product(name, category_id, owner, created, quantity, price, description, image_url).save();
	}
	
	/**
	 * finds a product by his id
	 * @param id int id of the product
	 * @return product
	 */
	public static Product find(int id) {
		return find.byId(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
		Logger.info(""+product.name);
		product.save();
	}
	
	public static List<Product> myProducts(int id) {
		
		List<Product> pp = find.where("owner_id = " + id).findList();
		return pp;
	}
	
	public String getFirstPic() {
		return this.image1;
	}
	
	public String getSecondPic() {
		return this.image2;
	}
	
	public String getLastPic() {
		return this.image3;
	}
	
	public void updateFirstPic(String image1) {
		this.image1 = image1;
	}
	
	public void updateSecondPic(String image2) {
		this.image2 = image2;
	}
	
	public void updateLastPic(String image3) {
		this.image3 = image3;
	}
	
	public static void deleteImage(Product p) {
		File f = new File("./public/" + p.image_url); 
		boolean b = f.delete();
	}
	
	
	public static List<String> allImages(int id) {
		return find.byId(id).image_urls;
	}

	
	
 

}
