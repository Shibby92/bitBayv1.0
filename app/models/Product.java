package models;

import java.io.File;
import java.util.*;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.*;

import play.Logger;
import play.data.validation.Constraints.*;
import play.db.ebean.Model;


// TODO: Auto-generated Javadoc
/**
 * Creates product.
 */
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@productId")
public class Product extends Model {

	/** The id. */
	@Id
	public int id;

	/** The name. */
	@Required
	@MaxLength(20)
	public String name;

	/** The category id. */
	public int categoryId;

	/** The cart. */
	@ManyToOne
	public Cart cart;

	/** The owner. */
	@ManyToOne
	public User owner;

	/** The quantity. */
	@Required
	public int quantity;

	/** The price. */
	@Required
	@Column(scale = 2)
	public double price;

	/** The description. */
	@Required
	@MinLength(10)
	@MaxLength(240)
	public String description;

	/** The images. */
	@OneToMany(cascade = CascadeType.ALL)
	public List<Image> images;

	/** The deleted. */
	public boolean deleted;

	/** The order. */
	@ManyToMany(mappedBy = "productList", cascade = CascadeType.ALL)
	public List<Orders> order = new ArrayList<Orders>();

	/** The sold. */
	public boolean sold;

	/** The ordered quantity. */
	public int orderedQuantity;

	/** The amount. */
	public double amount;

	public List<String> image_urls = new ArrayList<String>();

		/** The tags. */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
	public List<Tag> tags;

	/** The find. */
	public static Finder<Integer, Product> find = new Finder<Integer, Product>(
			Integer.class, Product.class);
	
	/** The find category. */
	static Finder<String, Category> findCategory = new Finder<String, Category>(
			String.class, Category.class);

	/**
	 * Instantiates a new product.
	 *
	 * @param name String the name
	 * @param price double the price
	 * @param quantity int the quantity
	 * @param owner User the owner
	 * @param description String the description
	 * @param categoryId int the category id
	 * @param images List<Image> the images
	 */
	public Product(String name, double price, int quantity, User owner,
			String description, int categoryId, List<Image> images) {
		this.name = name;
		this.price = price;
		this.owner = owner;
		this.description = description;
		this.categoryId = categoryId;
		this.images = images;
		this.sold = false;
		this.quantity = quantity;
		this.orderedQuantity = 0;
		this.deleted = false;
	}

	/**
	 * Instantiates a new product.
	 *
	 * @param name String the name
	 * @param price double the price
	 * @param quantity int the quantity
	 * @param owner User the owner
	 * @param description String the description
	 * @param categoryId int the category id
	 */
	public Product(String name, double price, int quantity, User owner,
			String description, int categoryId) {
		this.saveManyToManyAssociations("order");
		this.name = name;
		this.price = price;
		this.quantity = quantity;
		this.owner = owner;
		this.description = description;
		this.categoryId = categoryId;
		this.orderedQuantity = 0;
		this.amount = 0;
		this.deleted = false;

	}
	
	/**
	 * Instantiates a new product.
	 *
	 * @param name String the name
	 * @param price double the price
	 * @param quantity int the quantity
	 * @param owner User the owner
	 * @param description String the description
	 * @param categoryId int the category id
	 */
	public Product(String name, double price, int quantity, User owner,
			String description, int categoryId, boolean sold) {
		this.saveManyToManyAssociations("order");
		this.name = name;
		this.price = price;
		this.quantity = quantity;
		this.owner = owner;
		this.description = description;
		this.categoryId = categoryId;
		this.orderedQuantity = 0;
		this.amount = 0;
		this.deleted = false;
		this.sold=sold;

	}
	
	/**
	 * Instantiates a new product. This is needed for JsonController.
	 *
	 * @param name String the name
	 * @param price double the price
	 * @param quantity int the quantity
	 * @param description String the description
	 * @param image String the image
	 * @param owner User the owner
	 */
	public Product(String name, double price, int quantity, String description,
			String image, User owner) {
		this.name = name;
		this.price = price;
		this.quantity = quantity;
		this.owner = owner;
		this.description = description;
		this.deleted = false;
		this.orderedQuantity = 0;
		this.amount = 0;
	}
	
	/**
	 * Creates the product. This is needed for JsonController.
	 *
	 * @param name String the name
	 * @param price double the price
	 * @param quantity int the quantity
	 * @param description String the description
	 * @param image String the image
	 * @param owner User the owner
	 * @return the product
	 */
	public static Product create(String name, double price, int quantity,
			String description, String image, User owner) {
		Product newProduct = new Product(name, price, quantity, description,
				image, owner);
		return newProduct;
	}

	/**
	 * Creates the product.
	 *
	 * @param name String the name
	 * @param price double the price
	 * @param quantity int the quantity
	 * @param owner User the owner
	 * @param description String the description
	 * @param categoryId int the category id
	 * @param images List<Image> the images
	 */
	public static void create(String name, double price, int quantity,
			User owner, String description, int categoryId, List<Image> images) {

		Product p = new Product(name, price, quantity, owner, description, categoryId,
				images);
		p.save();
		for (Image image : images) {
			image.product = Product.find(p.id);
			image.save();
			Tag.create(p, Category.find(p.categoryId).name);
			Tag.create(p, p.name);
		}
	}

	/**
	 * Creates the product.
	 *
	 * @param name String the name
	 * @param price double the price
	 * @param quantity int the quantity
	 * @param owner User the owner
	 * @param description String the description
	 * @param categoryId int the category id
	 * @return the product
	 */
	public static Product create(String name, double price, int quantity,
			User owner, String description, int id) {
		Product p = new Product(name, price, quantity, owner, description, id);
		p.save();
		return p;

	}

	/**
	 * Finds a product by his id.
	 *
	 * @param id int id of the product
	 * @return product
	 */
	public static Product find(int id) {
		return find.byId(id);
	}

	/**
	 * Gets the ordered quantity.
	 *
	 * @return the ordered quantity
	 */
	public int getOrderedQuantity() {
		return orderedQuantity;
	}

	/**
	 * Sets the ordered quantity.
	 *
	 * @param orderedQuantity the new ordered quantity
	 */
	public void setOrderedQuantity(int orderedQuantity) {
		this.orderedQuantity = orderedQuantity;
		this.update();
	}

	/**
	 * Total price.
	 *
	 * @param product the product
	 * @return the double
	 */
	public static double total(Product product) {
		return product.price * product.getOrderedQuantity();
	}

	/**
	 * All products from database.
	 *
	 * @return the list
	 */
	public static List<Product> productList() {
		return find.all();
	}

	/**
	 * All product by one category.
	 *
	 * @param category String the category
	 * @return the list
	 */
	public static List<Product> listByCategory(String category) {
		int id = findCategory.where().eq("name", category).findUnique().id;
		return find.where().eq("categoryId", id).findList();

	}

	/**
	 * Method which will find product by his id and delete it.
	 *
	 * @param id int the id of the product
	 */
	public static void delete(int id) {
		Product temp = find.byId(id);
		temp.delete();
	}

	/**
	 * Updates the product.
	 *
	 * @param product Product the product
	 */
	public static void update(Product product) {
		product.update();
	}

	/**
	 * All products from one user.
	 *
	 * @param id int the id of the owner
	 * @return the list
	 */
	public static List<Product> myProducts(int id) {

		List<Product> pp = find.where("owner_id = " + id).findList();
		return pp;
	}

	/**
	 * Delete image from database and from folder.
	 *
	 * @param product Product the product
	 */
	public static void deleteImage(Product product) {
		for (Image image : product.images) {
			Image i = Image.find(image.id);

			File f = new File("./public/" + image.image);
			Logger.debug("File for delete: " + f.toString());
			f.delete();
			i.delete();
		}
	}

	public static void deleteImages(Product p) {
		for (String imageUrl : p.image_urls) {
			imageUrl=null;
		}
		for (Image image : p.images) {
			image.delete();
		}
		p.image_urls=null;
		p.image_urls=null;
		p.update();
		}
	
	/**
	 * Gets the ids of the products.
	 *
	 * @param products List<Product> the products
	 * @return the list of ids
	 */
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
	 * Making recommendation list.
	 *
	 * @param user User the user
	 * @return list of products that are recommended by the cartProducts
	 */

	public static List<Product> recommendProducts(User user) {
		List<Product> recommendedProducts = new ArrayList<Product>();
		for (Product productFromCart : Cart.find(user.id).productList) {
			List<Orders> containableOrders = new ArrayList<Orders>();
			for (Orders order : Orders.findOrder.all()) {
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
							if (recommendedProducts.size() < 4) {
								recommendedProducts.add(compare);
							}
						}

					}
				}
			}
		}
		for (Product p : Cart.find(user.id).productList) {
			if (recommendedProducts.contains(p)) {
				recommendedProducts.remove(p);
			}
		}
		return recommendedProducts;
	}

	/**
	 * Find recommendation.
	 *
	 * @param cartProducts List<Product> the cart products
	 * @param allProducts List<Product> the all products
	 * @return the list
	 */
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
	 * Finds the level of similarity between two products.
	 *
	 * @param product Product the product
	 * @param recommend Product the recommend
	 * @return the similarity level
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

	public static Product create(String name, double price, int quantity,
			User owner, String description, int categoryId, boolean sold) {
		Product p = new Product(name, price, quantity, owner, description, categoryId,sold);
		p.save();
		return p;
	}

}
