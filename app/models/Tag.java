package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;

// TODO: Auto-generated Javadoc
/**
 * The Class Tag.
 */
@Entity
public class Tag extends Model {

	/** The id. */
	@Id
	public int id;

	/** The product. */
	@ManyToOne
	public Product product;

	/** The tag. */
	public String tag;

	/**
	 * Instantiates a new tag.
	 *
	 * @param product Product the product
	 * @param tag String the tag
	 */
	public Tag(Product product, String tag) {
		this.product = product;
		this.tag = tag;
	}

	/**
	 * Creates the tag.
	 *
	 * @param product Product the product
	 * @param tag String the tag
	 */
	public static void create(Product product, String tag) {
		Tag t = new Tag(product, tag);
		t.save();

	}
}
