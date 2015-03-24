package models;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import play.Logger;
import play.db.ebean.Model;

@Entity
public class Cart extends Model {
	static Finder<Integer, Cart> find = new Finder<Integer, Cart>(
			Integer.class, Cart.class);
	@Id
	public int id;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "cart")
	public List<Product> productList;

	@OneToOne
	public int userid;

	public double checkout;

	public Cart(int id) {
		this.userid = id;
		this.checkout = 0;
	}

	public static void addProduct(Product product, Cart cart) {
		if (cart.productList == null) {
			cart.productList = new LinkedList<Product>();
		}
		cart.productList.add(product);
		product.cart = cart;
		cart.checkout += product.price;
		cart.update();
		product.update();
	}

	public static List getProducts(int id) {
		return find.where().eq("userid", id).findUnique().productList;

	}

	public static Cart getCart(int id) {
		return find.where().eq("userid", id).findUnique();
	}
}
