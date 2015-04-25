package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;

@Entity
public class ProductQuantity extends Model{
	
	public static Finder <Integer,ProductQuantity> find= new Finder<Integer,ProductQuantity>(Integer.class,ProductQuantity.class);
	@Id
	public int id;
	
	public int productId;
	public int quantity;
	
	@ManyToOne
	Orders order;

	public ProductQuantity(int productId, int quantity) {
		this.productId = productId;
		this.quantity = quantity;
	}
	public ProductQuantity(int productId, int quantity,Orders order) {
		this.productId = productId;
		this.quantity = quantity;
		this.order=order;
	}
	
	public static int getProductQuantity(int productId,int orderId){
		ProductQuantity temp= find.where().eq("productId", productId).eq("ORDER_ID",orderId).findUnique();
		return temp.quantity;
	}
	
	

}
