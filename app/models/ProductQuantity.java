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
	
	public static int getProductQuantity(int id){
		ProductQuantity temp= find.where().eq("productId", id).findUnique();
		return temp.quantity;
		
	}
	
	

}
