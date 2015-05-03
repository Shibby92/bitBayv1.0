package models;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import play.Logger;
import play.data.validation.Constraints.*;
import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

/**
 * Creates product
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

	public int categoryId;

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

	@OneToMany(cascade = CascadeType.ALL)
	public List<Image> images;

	public boolean deleted;

	@ManyToMany(mappedBy = "productList", cascade = CascadeType.ALL)
	public List<Orders> order = new ArrayList<Orders>();

	public boolean sold;

	public int orderedQuantity;

	public double amount;
	
	public String imageUrl;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
	public List<Tag> tags;

	public static Finder<Integer, Product> find = new Finder<Integer, Product>(
			Integer.class, Product.class);
	static Finder<String, Category> findCategory = new Finder<String, Category>(
			String.class, Category.class);

	public Product(String name, double price, int quantity, User owner,
			String description, int id, List<Image> images) {
		this.name = name;
		this.price = price;
		this.owner = owner;
		this.description = description;
		this.categoryId = id;
		this.images = images;
		this.sold = false;
		this.quantity = quantity;
		this.orderedQuantity = 0;
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
		this.categoryId = id;
		this.orderedQuantity = 0;
		this.amount = 0;
		this.deleted = false;

	}
	
	public static void create(String name, double price, int quantity,
			User owner, String description, int id, List<Image> images) {

		Product p = new Product(name, price, quantity, owner, description, id,
				images);
		p.save();
		for (Image image : images) {
			image.product = Product.find(p.getId());
			image.save();
			Tag.create(p, Category.find(p.categoryId).name);
			Tag.create(p, p.name);
		}
	}

	public static Product create(String name, double price, int quantity,
			User owner, String description, int id) {
		Product p = new Product(name, price, quantity, owner, description, id);
		p.save();
		return p;

	}

	/**
	 * finds a product by his id
	 * @param id int id of the product
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
		return find.where().eq("categoryId", id).findList();

	}

	
	/**
	 * method which will find id of the product and delete it
	 * @param id
	 */
	public static void delete(int id) {
			Product temp = find.byId(id);
			temp.delete();
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
	 * @param cartProducts Products that are going to be bought
	 * @param allProducts All products from the database
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
	 * @param product Product to be compared
	 * @param recommend Product to be compared
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
