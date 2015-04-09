package models;

import javax.persistence.Embeddable;
import javax.persistence.Entity;

import play.db.ebean.Model;

@Entity
@Embeddable
public class productList_order extends Model{

	public int order_id;
	
	public int product_id;
	
	

}
