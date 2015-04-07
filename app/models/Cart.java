package models;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import play.Logger;
import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

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
	
	@OneToOne
	public String userMail;

	public double checkout;
	
	public int size;

	public Cart(int id,String userMail) {
		this.userid = id;
		this.userMail=userMail;
		this.checkout = 0;
		this.size=0;
		//productList=new LinkedList<Product>();
	}

	public Cart() {
		// TODO Auto-generated constructor stub
	}

	public static void addProduct(Product product, Cart cart) {
		if (cart.productList == null) {
			cart.productList = new LinkedList<Product>();
		}
		cart.productList.add(product);
		Logger.info("nalazim se u add product");
		product.cart = cart;
		cart.checkout += product.price*product.getOrderedQuantity();
		cart.size=cart.size+product.getOrderedQuantity();
		cart.save();
		cart.update();
		product.update();
	}
	
	public static void addQuantity(Product product, Cart cart,int newQuantity) {
		if (cart.productList == null) {
			cart.productList = new LinkedList<Product>();
		}
		Logger.info("nalazim se u add quantity to cart");
		//product.cart = cart;
		cart.checkout+= product.price*newQuantity	;
		cart.size=cart.size+newQuantity;
		cart.save();
		cart.update();
		product.update();
	}

	
	public static List<Product> getProducts(int id) {
		return find.where().eq("userid", id).findUnique().productList;

	}

	public static Cart getCart(int user_id) {
		return find.where().eq("userid", user_id).findUnique();
	}
	
	public static Cart getCart(String email){
		return find.where().eq("userMail", email).findUnique();
	}
	
	static Finder<Integer,Cart> findCart=new Finder<Integer,Cart>(Integer.class,Cart.class);
	public static Cart find(int id) {
		
		return findCart.byId(id);
	}

	public static void clear(int id) {
		Cart cart=getCart(id);
		cart.productList.clear();
		cart.checkout=0;
		cart.size=0;
		//cart.update();
		
	}
	
	/*public static int getSize(Cart cart){
		int cartSize=0;
		for (Iterator<Product> iterator = cart.productList.iterator(); iterator.hasNext() ;){
			Product p=iterator.next();
			cartSize=cartSize+p.getOrderedQuantity();
		}
		return cartSize;
	}*/
}
