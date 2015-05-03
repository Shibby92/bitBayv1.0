package models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;

import play.db.ebean.Model;

// TODO: Auto-generated Javadoc
/**
 * The Class OrderDetails helps connect model Orders and Product.
 */
@Entity
@Embeddable
public class OrderDetails extends Model {

	/** The order_id. */
	public int order_id;

	/** The product_id. */
	public int product_id;

}
