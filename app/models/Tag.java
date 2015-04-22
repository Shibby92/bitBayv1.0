package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;

@Entity
public class Tag extends Model{
	
	@Id
	public int id;
	
	@ManyToOne
	public Product product;

	public String tag;
	
	public Tag(Product product, String tag){
		this.product=product;
		this.tag=tag;
	}
	public static void create(Product product, String tag){
		Tag t =new Tag(product,tag);
		t.save();
		
	}
}
