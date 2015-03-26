package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.ebean.Model;

@Entity
public class Orders extends Model {

	@Id
	public int id;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
	public List<Product> productList;
	
	@ManyToOne
	public User buyer;
	
	public double price;

	public Orders(List<Product> productList){
		this.productList=productList;
		this.price=999;
	}
	static Finder<Integer,Orders> findOrder=new Finder<Integer,Orders>(Integer.class,Orders.class);
	public static Orders find(int id) {
		
		return findOrder.byId(id);
	}
	public static void create(Orders orders) {
		orders.save();
		
	}

}
