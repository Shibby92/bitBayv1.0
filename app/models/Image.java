package models;

import java.util.*;

import javax.persistence.*;

import play.db.ebean.Model;

@Entity
public class Image extends Model {
	@Id
	public int id;
	
	public String path;
	public String savePath;
	
	@ManyToOne
	public Product product;
	
	
	static Finder<Integer, Image> find = new Finder<Integer, Image>(Integer.class, Image.class);
	
	public Image(String path,String savePath, Product product){
		this.path = path;
		this.product = product;
		this.savePath = savePath;
	}
	
	
	public static void create(String path,String savePath, Product cp){
		new Image(path,savePath, cp).save();
	}
	
	public static void delete(int id){
		find.byId(id).delete();
	}
	
	public static Image find(int id){
		return find.byId(id);
	}
	

	public static int photoStackLength(Product c){
		List<Image> photos =  find.all();
		int counter = 0;
		for(Image p: photos){
			if(p.product.id == c.id){
				counter ++;
			}
		}
		return counter;
	}
	
	public static List<Image> photosByProduct(Product c){
		List<Image> allPhotos = find.all();
		List<Image> byProduct = new ArrayList<Image>();
		for(Image p: allPhotos){
			if(p.product.id == c.id){
				byProduct.add(p);
			}
		}
		return byProduct;
	}


}
