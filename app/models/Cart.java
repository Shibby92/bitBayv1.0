package models;

import java.util.*;


import javax.persistence.*;

import play.Logger;
import play.db.ebean.Model;
import play.db.ebean.Model.*;


// TODO: Auto-generated Javadoc
/**
 * The Class Cart.
 */
@Entity
public class Cart extends Model {
	
	/** The find. */
	static Finder<Integer, Cart> find = new Finder<Integer, Cart>(
			Integer.class, Cart.class);
	
	/** The id. */
	@Id
	public int id;

	/** The product list. */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "cart")
	public List<Product> productList;

	/** The user id. */
	@OneToOne
	public int userId;
	
	/** The user mail. */
	@OneToOne
	public String userMail;

	/** The checkout. */
	public double checkout;
	
	/** The size. */
	public int size;
	
	/** The shipping address. */
	public String shippingAddress;
	

	/** The find cart. */
	static Finder<Integer,Cart> findCart=new Finder<Integer,Cart>(Integer.class,Cart.class);

	/**
	 * Instantiates a new cart.
	 *
	 * @param userId int the user id
	 * @param userMail String the user mail
	 */
	public Cart(int userId, String userMail) {
		this.userId = userId;
		this.userMail = userMail;
		this.checkout = 0;
		this.size = 0;
		User u = User.find(userId);
		String shipAd = u.user_address;
		this.shippingAddress = shipAd;
	}
	
	/**
	 * Instantiates a new cart.
	 */
	public Cart() {
	}

	/**
	 * Adds the product to cart.
	 *
	 * @param product Product the product
	 * @param cart Cart the cart
	 */
	public static void addProduct(Product product, Cart cart) {
		if (cart.productList == null) {
			cart.productList = new LinkedList<Product>();
		}
		cart.productList.add(product);
		product.cart = cart;
		cart.checkout += product.price * product.getOrderedQuantity();
		cart.size = cart.size + product.getOrderedQuantity();
		cart.save();
		cart.update();
		product.update();
	}
	
	/**
	 * Empties the cart.
	 * 
	 * @param cart Cart the cart
	 */
	public static void nullCart(Cart cart) {
		cart.checkout = 0;
		cart.size = 0;
		Iterator<Product> productIterator = cart.productList.iterator();
		while (productIterator.hasNext()) {
			Product p = productIterator.next();
			productIterator.remove();
			cart.productList.remove(p);
		}
		cart.update();
	}
	
	/**
	 * Adds the quantity to the cart.
	 * 
	 * @param product Product the product
	 * @param cart Cart the cart
	 * @param newQuantity int the new quantity
	 */
	public static void addQuantity(Product product, Cart cart, int newQuantity) {
		if (cart.productList == null) {
			cart.productList = new LinkedList<Product>();
		}
		cart.checkout += product.price * newQuantity;
		cart.size = cart.size + newQuantity;
		cart.save();
		cart.update();
		product.update();
	}
	
	/**
	 * Gets the products from the cart.
	 * 
	 * @param id int the id
	 * @return list of products
	 */
	public static List<Product> getProducts(int id) {
		return find.where().eq("userid", id).findUnique().productList;

	}

	/**
	 * Gets the cart by user id.
	 * 
	 * @param userId int the user id
	 * @return the cart
	 */
	public static Cart getCartbyUserId(int userId) {
		Logger.debug("User id u cartu "+userId);
		return find.where().eq("user_id", userId).findUnique();
	}

	/**
	 * Gets the cart by user email.
	 * 
	 * @param email String the email
	 * @return the cart
	 */
	public static Cart getCartbyUserEmail(String email){
		return find.where().eq("userMail", email).findUnique();
	}
	
	/**
	 * Finds the cart by its id.
	 * 
	 * @param id int the id
	 * @return the cart
	 */
	public static Cart find(int id) {
		return findCart.byId(id);
	}

	/**
	 * Clears the cart by its id.
	 * 
	 * @param id int the id of the cart
	 */
	public static void clear(int id) {
		Cart cart = getCartbyUserId(id);
		for (Product p : cart.productList) {
			p.cart = null;
			p.setOrderedQuantity(0);
		}
		cart.productList.clear();
		cart.checkout = 0;
		cart.size = 0;

		cart.update();

	}

	/**
	 * Removes the product from cart.
	 * 
	 * @param id int the id
	 * @return the cart
	 */
	public static Cart removeProductFromCart(int id) {
		Product productToDelete = Product.find.byId(id);
		Cart cart = productToDelete.cart;
		cart.productList.remove(productToDelete);
		cart.checkout -= productToDelete.price;
		productToDelete.cart = null;
		cart.update();
		productToDelete.update();
		return cart;
	}

}
