package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

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
	
	public String shippingAddress;
	
	@OneToMany (cascade = CascadeType.ALL, mappedBy = "order")
	public List<ProductQuantity> pQ;
	
	public String orderDate;

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
			this.shippingAddress=cart.shippingAddress;
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
	public Orders(Orders userOrder) {
		this.buyer=userOrder.buyer;
		
		this.orderDate=userOrder.orderDate;
		this.pQ=userOrder.pQ;
		this.price=userOrder.price;
		this.productList=userOrder.productList;
		
		this.shippingAddress=userOrder.shippingAddress;
		this.token=userOrder.token;
	}

//	public static int notificationCounter(List<Orders>ol){
//		int counter=0;
//		for(Orders o : ol){
//			if(o.notification)
//				counter++;
//		}
//		return counter;
//	}
	public static double priceFromSeller(Orders order, User user){
		double sum=0;
		for(Product p: order.productList){
			if(p.owner.email.equals(user.email)){
				sum+=p.price;
			}
		}
		return sum;
	}

}
