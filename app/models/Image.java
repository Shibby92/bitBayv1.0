package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

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

	/** The image. */
	public String image;

	/** The product. */
	@ManyToOne
	public Product product;

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

}
