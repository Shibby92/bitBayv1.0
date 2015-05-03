package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.paypal.api.payments.Links;

import play.db.ebean.Model;
import models.Product;

;

// TODO: Auto-generated Javadoc
/**
 * The Class Orders.
 */
@Entity
public class Orders extends Model {

	/** The id. */
	@Id
	public int id;

	/** The product list. */
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "OrderDetails", joinColumns = { @JoinColumn(name = "orderId", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "productId", referencedColumnName = "id") })
	public List<Product> productList = new ArrayList<Product>();

	/** The buyer. */
	@ManyToOne
	public User buyer;

	/** The price. */
	public double price;

	/** The token. */
	public String token;

	/** The shipping address. */
	public String shippingAddress;

	/** The product quantity. */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
	public List<ProductQuantity> productQuantity;

	/** The order date. */
	public String orderDate;
	
	/** The find order. */
	static Finder<Integer, Orders> findOrder = new Finder<Integer, Orders>(
			Integer.class, Orders.class);

	/**
	 * Instantiates a new order.
	 *
	 * @param cart Cart the cart
	 * @param buyer User the buyer
	 * @param token String the token
	 */
	public Orders(Cart cart, User buyer, String token) {
		if (cart.productList != null) {
			play.Logger.debug(cart.productList.get(0).name);
			this.productList = new ArrayList<Product>();
			for (int i = 0; i < cart.productList.size(); i++) {
				this.productList.add(cart.productList.get(i));
			}
			price = cart.checkout;
			this.shippingAddress = cart.shippingAddress;
			this.token = token;
			this.buyer = buyer;
			this.orderDate = "1/1/2014";
		}
	}
	
	/**
	 * Instantiates a new orders.
	 *
	 * @param product the product
	 */
	public Orders(Product product) {
		this.productList.add(product);
	}

	/**
	 * Finds order by its id.
	 *
	 * @param id int the id of the orfer
	 * @return the order
	 */
	public static Orders find(int id) {

		return findOrder.byId(id);
	}

	/**
	 * Creates the order and saves it in database.
	 *
	 * @param order Orders the order
	 * @param price double the price of the order
	 */
	public static void create(Orders order, double price) {
		order.price = price;
		order.save();
	}

	/**
	 * Price from seller.
	 *
	 * @param order Orders the order
	 * @param user User the user
	 * @return the price
	 */
	public static double priceFromSeller(Orders order, User user) {
		double sum = 0;
		for (Product p : order.productList) {
			if (p.owner.email.equals(user.email)) {
				sum += p.price;
			}
		}
		return sum;
	}

}
