package models;

import java.io.File;
import java.sql.Timestamp;
//import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import play.Logger;
import play.data.validation.Constraints.*;
import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

/**
 * Creates product
 * 
 * @author eminamuratovic
 */
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@productId")
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

	@Version
	@Column(columnDefinition = "timestamp default '2015-16-04 20:17:06'")
	public Timestamp updated;

	@Required
	public int quantity;

	@Required
	@Column(scale = 2)
	public double price;

	@Required
	@MinLength(10)
	@MaxLength(240)
	public String description;

	public String image_url;

	public String image1;

	public String image2;

	public String image3;

	@OneToMany(cascade = CascadeType.ALL)
	public List<Image> images;

	public boolean deleted;

	@ManyToMany(mappedBy = "productList", cascade = CascadeType.ALL)
	public List<Orders> order = new ArrayList<Orders>();

	public boolean sold;

	public List<String> image_urls = new ArrayList<String>();

	public int orderedQuantity;

	public double amount;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
	public List<Tag> tags;

	public static Finder<Integer, Product> find = new Finder<Integer, Product>(
			Integer.class, Product.class);
	static Finder<String, Category> findCategory = new Finder<String, Category>(
			String.class, Category.class);

	/**
	 * creates a product
	 * 
	 * @param name
	 *            String name of the product
	 * @param category_id
	 *            int id of the category
	 * @param owner_id
	 *            int id of the seller
	 * @param created
	 *            Date date added
	 * @param quantity
	 *            int quantity of the product
	 * @param price
	 *            double price of the product
	 * @param description
	 *            String description of the product
	 * @param image_url
	 *            String url of the image of the product
	 */
	public Product(String name, int category_id, User owner, Timestamp updated,
			int quantity, double price, String description, String image_url) {
		this.name = name;
		this.category_id = category_id;
		this.owner = owner;
		this.updated = updated;
		this.quantity = quantity;
		this.price = price;
		this.description = description;
		this.image_url = image_url;
		this.saveManyToManyAssociations("order");
		this.sold = false;
		this.orderedQuantity = 0;
		this.amount = 0;
		this.deleted = false;
	}

	public Product(String name, double price, User owner, String description,
			int id, String image1) {
		this.name = name;
		this.price = price;
		this.owner = owner;
		this.description = description;
		this.category_id = id;
		this.image_url = image1;
		this.image1 = image1;
		this.image_urls.add(image1);
		this.image_url = this.image_urls.get(0);
		this.saveManyToManyAssociations("order");
		this.sold = false;
		this.orderedQuantity = 0;
		this.amount = 0;
		this.deleted = false;
	}

	public Product(String name, double price, User owner, String description,
			int id, String image1, String image2) {
		this.name = name;
		this.price = price;
		this.owner = owner;
		this.description = description;
		this.category_id = id;
		this.image1 = image1;
		this.image_urls.add(image1);
		this.image2 = image2;
		this.image_urls.add(image2);
		this.image_url = this.image_urls.get(0);
		this.saveManyToManyAssociations("order");
		this.sold = false;
		this.orderedQuantity = 0;
		this.amount = 0;
		this.deleted = false;

	}

	public Product(String name, double price, User owner, String description,
			int id, String image1, String image2, String image3) {
		this.name = name;
		this.price = price;
		this.owner = owner;
		this.description = description;
		this.category_id = id;
		this.image1 = image1;
		this.image_urls.add(image1);
		this.image2 = image2;
		this.image_urls.add(image2);
		this.image3 = image3;
		this.image_urls.add(image3);
		this.image_url = this.image_urls.get(0);
		this.saveManyToManyAssociations("order");
		this.sold = false;
		this.orderedQuantity = 0;
		this.amount = 0;
		this.deleted = false;

	}

	public Product(String name, double price, int quantity, User owner,
			String description, int id, String image1, String image2) {
		this.name = name;
		this.price = price;
		this.quantity = quantity;
		this.owner = owner;
		this.description = description;
		this.category_id = id;
		this.image1 = image1;
		this.image2 = image2;
		this.orderedQuantity = 0;
		this.image_url = image1;
		this.orderedQuantity = 0;
		this.saveManyToManyAssociations("order");
		this.amount = 0;
		this.deleted = false;

	}

	public Product(String name, double price, int quantity, User owner,
			String description, int id, String image1) {
		this.name = name;
		this.price = price;
		this.quantity = quantity;
		this.owner = owner;
		this.description = description;
		this.category_id = id;
		this.image1 = image1;
		this.orderedQuantity = 0;
		this.amount = 0;
		this.deleted = false;

	}

	public Product(String name, double price, User owner, String description,
			int id, List<Image> images) {
		this.name = name;
		this.price = price;
		this.owner = owner;
		this.description = description;
		this.category_id = id;
		this.images = images;
		this.sold = false;
		this.deleted = false;
	}

	public Product(String name, double price, int quantity, User owner,
			String description, int id, List<Image> images) {
		this.name = name;
		this.price = price;
		this.owner = owner;
		this.description = description;
		this.category_id = id;
		this.images = images;
		this.sold = false;
		this.quantity = quantity;
		this.orderedQuantity = 0;
		this.deleted = false;
	}

	public Product(String name, double price, User owner, String description) {
		this.name = name;
		this.price = price;
		this.owner = owner;
		this.description = description;
		this.sold = false;
		this.orderedQuantity = 0;
		this.deleted = false;
	}

	public Product(Product product) {
		this.name = product.name;
		this.price = product.price;
		this.owner = product.owner;
		this.description = product.description;
		this.category_id = product.id;
		this.image1 = product.image1;
		this.image_urls.add(product.image1);
		this.image2 = product.image2;
		this.image_urls.add(product.image2);
		this.image3 = product.image3;
		this.image_urls.add(product.image3);
		this.image_url = this.image_urls.get(0);
		this.quantity = product.quantity - product.orderedQuantity;
		this.sold = false;
		this.saveManyToManyAssociations("order");
		this.deleted = false;
	}

	public Product(String name, double price, int quantity, User owner,
			String description, int id, String image1, String image2,
			String image3) {
		this.image_url = image1;
		this.orderedQuantity = 0;
		this.saveManyToManyAssociations("order");
		this.name = name;
		this.price = price;
		this.quantity = quantity;
		this.owner = owner;
		this.description = description;
		this.category_id = id;
		this.image1 = image1;
		this.image2 = image2;
		this.image3 = image3;
		this.deleted = false;
	}

	public Product(String name, double price, int quantity, User owner,
			String description) {
		this.name = name;
		this.price = price;
		this.quantity = quantity;
		this.owner = owner;
		this.description = description;
		this.deleted = false;
	}

	public Product(String name, double price, int quantity, User owner,
			String description, int id) {
		this.saveManyToManyAssociations("order");
		this.name = name;
		this.price = price;
		this.quantity = quantity;
		this.owner = owner;
		this.description = description;
		this.category_id = id;
		this.orderedQuantity = 0;
		this.amount = 0;
		this.deleted = false;

	}

	public Product(String name, double price, int quantity, String description,
			String image, User owner) {
		this.name = name;
		this.price = price;
		this.quantity = quantity;
		this.owner = owner;
		this.description = description;
		this.deleted = false;

	}

	// Constructor for easier testing
	public Product(String string, double d, String string2, int i) {
		this.name = string;
		this.price = d;
		this.description = string2;
		this.quantity = i;
		this.deleted = false;
	}

	public static void create(String name, double price, User owner,
			String description, int id, String image1) {

		new Product(name, price, owner, description, id, image1).save();
	}

	public static void create(String name, double price, User owner,
			String description, int id, String image1, String image2) {
		new Product(name, price, owner, description, id, image1, image2).save();
	}

	public static void create(String name, double price, User owner,
			String description, int id, String image1, String image2,
			String image3) {
		new Product(name, price, owner, description, id, image1, image2, image3)
				.save();
	}

	public static void create(String name, int category_id, User owner,
			Timestamp created, int quantity, double price, String description,
			String image_url) {
		new Product(name, category_id, owner, created, quantity, price,
				description, image_url).save();
	}

	public static void create(String name, double price, int quantity,
			User owner, String description, int id, List<Image> images) {

		Product p = new Product(name, price, quantity, owner, description, id,
				images);
		p.save();
		for (Image image : images) {
			image.product = Product.find(p.getId());
			image.save();
			Tag.create(p, Category.find(p.category_id).name);
			Tag.create(p, p.name);
		}
	}

	public static void create(String name, double price, User owner,
			String description) {
		new Product(name, price, owner, description).save();

	}

	public static Product create(String name, double price, int quantity,
			User owner, String description, int id) {
		Product p = new Product(name, price, quantity, owner, description, id);
		p.save();
		return p;

	}

	public static void create(String name, double price, int quantity,
			User owner, String description, int id, String image1, String image2) {
		new Product(name, price, quantity, owner, description, id, image1,
				image2).save();
	}

	public static void create(String name, double price, int quantity,
			User owner, String description, int id, String image1) {
		new Product(name, price, quantity, owner, description, id, image1)
				.save();
	}

	public static void create(String name, double price, int quantity,
			User owner, String description, int id, String image1,
			String image2, String image3) {
		new Product(name, price, quantity, owner, description, id, image1,
				image2, image3).save();
	}

	public static Product create(String name, double price, int quantity,
			String description, String image, User owner) {
		Product newProduct = new Product(name, price, quantity, description,
				image, owner);
		return newProduct;
	}

	/**
	 * finds a product by his id
	 * 
	 * @param id
	 *            int id of the product
	 * @return product
	 */
	public static Product find(int id) {
		return find.byId(id);
	}

	public int getOrderedQuantity() {
		return orderedQuantity;
	}

	public void setOrderedQuantity(int orderedQuantity) {
		this.orderedQuantity = orderedQuantity;
		this.update();
	}

	public static double total(Product p) {
		return p.price * p.getOrderedQuantity();
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return this.id;
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

	public static List<Product> productList() {
		return find.all();
	}

	public static List<Product> listByCategory(String category) {
		int id = findCategory.where().eq("name", category).findUnique().id;
		return find.where().eq("category_id", id).findList();

	}

	// method which will find id of the product and delete it, else will throw
	// exception,method is used in ProductApplication
	public static void delete(int id) {
		if (find.byId(id) == null) {
			throw new IllegalArgumentException("Produkt ne postoji");
		} else {
			Product temp = find.byId(id);
			temp.delete();
		}
	}

	public static void update(Product product) {
		product.update();
	}

	public static List<Product> myProducts(int id) {

		List<Product> pp = find.where("owner_id = " + id).findList();
		return pp;
	}

	public static List<Product> userProducts(User user) {
		List<Product> userProductList = find.where().eq("owner", user)
				.findList();
		return userProductList;
	}

	public String getFirstPic() {
		return this.image1;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public static void deleteImage(Product p) {
		for (Image image : p.images) {
			Image i = Image.find(image.id);

			File f = new File("./public/" + image.image);
			Logger.debug("File for delete: " + f.toString());
			f.delete();
			i.delete();
		}
	}

	public static List<String> allImages(int id) {
		return find.byId(id).image_urls;
	}

	public static List<Product> findAll() {
		return find.all();
	}

	public static String getIds(List<Product> products) {
		if (products.size() < 1) {
			return null;
		}

		StringBuilder sb = new StringBuilder();
		for (Product product : products) {
			sb.append(product.id).append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	/**
	 * Making recommendation list
	 * 
	 * @param cartProducts
	 *            Products that are going to be bought
	 * @param allProducts
	 *            All products from the database
	 * @return List of products that are recommended by the cartProducts
	 */

	public static List<Product> recommendProducts(User user) {
		List<Product> recommendedProducts = new ArrayList<Product>();
		for (Product productFromCart : Cart.find(user.id).productList) {
			List<Orders> containableOrders = new ArrayList<Orders>();
			for (Orders order : Orders.find.all()) {
				for (Product p : order.productList) {
					if (p.id == productFromCart.id) {
						containableOrders.add(order);
					}
				}
			}
			for (Orders order : containableOrders) {
				for (Product compare : order.productList) {
					if (productFromCart.id != compare.id) {
						if (similarity(productFromCart, compare) >= 2) {
							if(recommendedProducts.size()<4){
							recommendedProducts.add(compare);
							}
						}

					}
				}
			}
		}
		for(Product p: Cart.find(user.id).productList){
			if(recommendedProducts.contains(p)){
				recommendedProducts.remove(p);
			}
		}
		return recommendedProducts;
	}

	public static List<Product> findRecommendation(List<Product> cartProducts,
			List<Product> allProducts) {
		// clearing cartProducts from allProducts
		for (Product cartProduct : cartProducts) {
			allProducts.remove(cartProduct);
		}
		List<Product> similarProducts = new ArrayList<Product>();
		// similarity level 3
		for (Product cartProduct : cartProducts) {
			for (Product recommend : allProducts) {
				if (similarity(cartProduct, recommend) > 2) {
					similarProducts.add(recommend);
					if (similarProducts.size() == 4) {
						break;
					}
				}
			}
			for (Product duplicate : similarProducts) {
				allProducts.remove(duplicate);
			}
			if (similarProducts.size() == 4) {
				break;
			}
		}
		if (similarProducts.size() < 4) {
			// similarity level 2
			for (Product cartProduct : cartProducts) {
				for (Product recommend : allProducts) {
					if (similarity(cartProduct, recommend) == 2) {
						similarProducts.add(recommend);

						if (similarProducts.size() == 4) {
							break;
						}
					}
				}
				for (Product duplicate : similarProducts) {
					allProducts.remove(duplicate);
				}
				if (similarProducts.size() == 4) {
					break;
				}
			}
		}
		if (similarProducts.size() < 4) {
			// similarity level 1
			for (Product cartProduct : cartProducts) {
				for (Product recommend : allProducts) {
					if (similarity(cartProduct, recommend) == 1) {
						similarProducts.add(recommend);

						if (similarProducts.size() == 4) {
							break;
						}
					}
				}
				for (Product duplicate : similarProducts) {
					allProducts.remove(duplicate);
				}
				if (similarProducts.size() == 4) {
					break;
				}
			}
		}
		return similarProducts;
	}

	/**
	 * Defines the level of similarity of two products by comparing their tags
	 * 
	 * @param product
	 *            Product to be compared
	 * @param recommend
	 *            Product to be compared
	 * @return level of similarity
	 */
	private static int similarity(Product product, Product recommend) {
		int similarityLevel = 0;
		for (Tag tag1 : product.tags) {
			for (Tag tag2 : recommend.tags) {
				if (tag1.tag.equals(tag2.tag)) {
					similarityLevel++;
				}
			}
		}
		Logger.debug(String.valueOf(similarityLevel));
		return similarityLevel;
	}

}
