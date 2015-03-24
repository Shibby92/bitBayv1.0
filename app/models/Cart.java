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
	static Finder <Integer,Cart> find= new Finder<Integer,Cart>(Integer.class,Cart.class);
	@Id
	public int id;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="cart")
	public LinkedList<Product> productList;

	@OneToOne
	public int userid;
	
	static int checkout=0;
	
	public Cart(int userid) {
		this.userid = userid;
		productList= new LinkedList<Product>();
	}
	public Cart(Product product){
		productList.add(product);
		checkout+=product.price;
	}
	public static void create(int id){
		new Cart(id).save();
	}
	public static void productToCart(Product product,int id){
		Cart c=find.where().eq("userid", id).findUnique();
		c.productList.add(product);
	}
	

}
