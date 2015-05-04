package models;

import javax.persistence.*;

import play.db.ebean.Model;

// TODO: Auto-generated Javadoc
/**
 * The Class ProductQuantity.
 */
@Entity
public class ProductQuantity extends Model {

	/** The find. */
	public static Finder<Integer, ProductQuantity> find = new Finder<Integer, ProductQuantity>(
			Integer.class, ProductQuantity.class);
	
	/** The id. */
	@Id
	public int id;

	/** The product id. */
	public int productId;
	
	/** The quantity. */
	public int quantity;

	/** The order. */
	@ManyToOne
	Orders order;

	/**
	 * Instantiates a new product quantity.
	 *
	 * @param productId int the product id
	 * @param quantity int the quantity
	 */
	public ProductQuantity(int productId, int quantity) {
		this.productId = productId;
		this.quantity = quantity;
	}

	/**
	 * Instantiates a new product quantity.
	 *
	 * @param productId the product id
	 * @param quantity the quantity
	 * @param order the order
	 */
	public ProductQuantity(int productId, int quantity, Orders order) {
		this.productId = productId;
		this.quantity = quantity;
		this.order = order;
	}

}
