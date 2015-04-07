package models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.paypal.api.payments.Links;
import com.sun.media.jfxmedia.logging.Logger;

import play.db.ebean.Model;

@Entity
public class Orders extends Model {

	@Id
	public int id;
	
	@ManyToMany(mappedBy="order",cascade=CascadeType.ALL)
	public List<Integer> productList;
	
	@ManyToOne
	public User buyer;
	
	public double price;
	
	public String token;
	
	
	public Orders(List<Integer> productList){
		this.productList=productList;
		
	}
	public Orders(Cart cart, User buyer, String token) {
		if (cart.productList!=null){
			play.Logger.debug(cart.productList.get(0).name);
			this.productList= new ArrayList<Integer>();
		for(int i=0;i<cart.productList.size();i++ ){
			this.productList.add(cart.productList.get(i).id);
		}
		price=cart.checkout;
		this.token=token;
		this.buyer=buyer;
		this.saveManyToManyAssociations("productList");
	}
	}

	static Finder<Integer,Orders> findOrder=new Finder<Integer,Orders>(Integer.class,Orders.class);
	public static Orders find(int id) {
		
		return findOrder.byId(id);
	}
	public static void create(Orders orders) {
		orders.save();
		
	}

}
