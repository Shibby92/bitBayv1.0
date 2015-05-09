package models;

import java.util.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.imageio.ImageIO;
import play.Logger;
import play.Play;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import com.cloudinary.Cloudinary;
import com.google.common.io.Files;
import com.cloudinary.*;
import java.io.IOException;
import java.util.*;

import javax.persistence.*;

import com.cloudinary.Cloudinary;
import javax.persistence.*;

import play.db.ebean.Model;

// TODO: Auto-generated Javadoc
/**
 * The Class Image.
 */
@Entity
public class Image extends Model {

	/** The id. */
	@Id
	public int id;

public String image;
	
	@ManyToOne
	public Product product;

	public String public_id;
	
	public String secretImageUrl;

	
	private static	 String cloudName = Play.application().configuration().getString("cloudName");
	private static  String apiKey = Play.application().configuration()
			.getString("cloudKey");
  	private static String apiSecret = Play.application().configuration()
			.getString("cloudSecretKey");
	/** The find. */
	static Finder<Integer, Image> find = new Finder<Integer, Image>(
			Integer.class, Image.class);

	/**
	 * Instantiates a new image.
	 *
	 * @param image String the image
	 * @param product Product the product
	 */
	public Image(String image, Product product) {
		this.image = image;
		this.product = product;
	}

	/**
	 * Instantiates a new image.
	 */
	public Image() {
		this.image = null;
		this.id = 0;
	}

	/**
	 * Creates the image and saves it in database.
	 *
	 * @param image String the image
	 * @param product Product the product
	 */
	public static void create(String image, Product product) {
		new Image(image, product).save();
	}
	
	/**
	 * Deletes the image from database.
	 *
	 * @param id int the id
	 */
	public static void delete(int id) {
		find.byId(id).delete();
	}

	/**
	 * Find image by its id.
	 *
	 * @param id int the id
	 * @return the image
	 */
	public static Image find(int id) {
		return find.byId(id);
	}

	/**
	 * Save image to database.
	 *
	 * @param img Image the image
	 */
	public static void saveImg(Image img) {
		img.save();
	}
	
	/**
	 * List of photos by one product.
	 *
	 * @param product Product the product
	 * @return the list
	 */
	public static List<Image> photosByProduct(Product product) {
		List<Image> allPhotos = find.all();
		List<Image> byProduct = new ArrayList<Image>();
		for (Image p : allPhotos) {
			if (p.product.id == product.id) {
				byProduct.add(p);
			}
		}
		return byProduct;
	}
	
	public static Map getconfigMap(){
	  	Map<String, String> config = new HashMap<String, String>();
		config.put("cloud_name", cloudName);
		config.put("api_key", apiKey);
		config.put("api_secret", apiSecret);
		return  config;
		}
	  	
	  	static Map configMap=getconfigMap();
	  	public static Cloudinary cloudinary = new Cloudinary(configMap);
	
	public static Image uploadCreate(File image) {
@SuppressWarnings("rawtypes")
		Map uploadResult = null;
		try {
			uploadResult = cloudinary.uploader().upload(image, Cloudinary.emptyMap());
		} catch (IOException e) {
			Logger.error(e.getMessage());
			e.printStackTrace();
		}
		Image img = new Image();
		img.public_id = (String) uploadResult.get("public_id");
		Logger.debug(img.public_id);
		img.image = (String) uploadResult.get("url");
		Logger.debug(img.image);
		img.secretImageUrl = (String) uploadResult.get("secure_url");
		Logger.debug(img.secretImageUrl);
		//img.image=img.image_url;
		img.save();
		return img;

	}
	
	public static Image create(String public_id, String image_url, Product p){
		Image i = new Image();
		i.public_id = public_id;
		i.image = image_url;
		i.product=p;
		i.save();
		p.images.add(i);
		p.save();
		return i;
	}
	
	public String getUrl(int width, int height){
				String url = cloudinary.url().format("jpg")
				  .transformation(new Transformation().width(width).height(height))
				  .generate(public_id);
			return url;
	}

}
