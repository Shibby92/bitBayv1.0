package models;

import java.util.ArrayList;
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

import com.paypal.api.payments.Links;

import play.db.ebean.Model;

@Entity
public class Orders extends Model {

	@Id
	public int id;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "OrderDetails", joinColumns = { @JoinColumn(name = "orderId", referencedColumnName = "id") },
	inverseJoinColumns = { @JoinColumn(name = "productId", referencedColumnName = "id") })
	public List<Product> productList= new ArrayList<Product>();

	@ManyToOne
	public User buyer;

	public double price;
	
	public String token;
	
	@ManyToOne
	public User seller;
	
	public boolean notification;
	
	@OneToMany (cascade = CascadeType.ALL, mappedBy = "order")
	public List<ProductQuantity> pQ;

	public static Finder<Integer,Orders> find=new Finder<Integer,Orders>(Integer.class,Orders.class);
	public Orders(List<Product> productList){
		this.productList=productList;
	}

	public Orders(Cart cart, User buyer, String token) {
		if (cart.productList != null) {
			play.Logger.debug(cart.productList.get(0).name);
			this.productList = new ArrayList<Product>();
			for (int i = 0; i < cart.productList.size(); i++) {
				this.productList.add(cart.productList.get(i));
			}
			price = cart.checkout;
			this.token = token;
			this.buyer = buyer;
		}
	}

	public Orders() {
		// TODO Auto-generated constructor stub
	}

	static Finder<Integer,Orders> findOrder=new Finder<Integer,Orders>(Integer.class,Orders.class);
	public static Orders find(int id) {

		return findOrder.byId(id);
	}

	public static void create(Orders orders) {
		orders.save();

	}
	// Constructor made for easier testing
	public Orders (Product product){
		this.productList.add(product);
	}
	public static int notificationCounter(List<Orders>ol){
		int counter=0;
		for(Orders o : ol){
			if(o.notification)
				counter++;
		}
		return counter;
	}

}
