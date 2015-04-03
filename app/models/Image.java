package models;

import java.util.*;

import javax.persistence.*;

import play.db.ebean.Model;

@Entity
public class Image extends Model {
	
	public static List<String> image_url;
	public static int product_id;
	
	public Image(List<String> image_url, int product_id){
		this.image_url = image_url;
		this.product_id = product_id;
	}
	
	

}
