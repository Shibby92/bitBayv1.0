package models;

import java.util.*;

import javax.persistence.*;

import play.db.ebean.Model;

@Entity
public class Image extends Model {
	
	@Id
	public int id;
	
	public String image;
	
	@ManyToOne
	public Product product;

	static Finder<Integer, Image> find = new Finder<Integer, Image>(Integer.class, Image.class);
	
	public Image(String image, Product product){
		this.image = image;
		this.product = product;
	}
	
	public Image() {
		this.image = null;
		this.id = 0;
	}
	
	public static void create(String image, Product product){
		new Image(image, product).save();
	}
	
	public static Image createI(String image, Product product){
		Image i = new Image(image, product);
		i.save();
		return i;
	}
	
	public static void delete(int id){
		find.byId(id).delete();
	}
	
	public static Image find(int id){
		return find.byId(id);
	}
	
	public static void saveImg(Image img) {
		img.save();
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
	
	public static List<Image> photosByProduct(Product p){
		List<Image> allPhotos = find.all();
		List<Image> byProduct = new ArrayList<Image>();
		for(Image img: allPhotos){
		//	if(img!=null && p!=null && img.product!=null){
			if(img.product.id == p.id ){
				byProduct.add(img);
		//	}
			}
		}
		return byProduct;
	}


}
