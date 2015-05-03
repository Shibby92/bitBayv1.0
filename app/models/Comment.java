package models;

import java.util.List;

import javax.persistence.*;

import play.Play;
import play.data.validation.Constraints.*;
import play.db.ebean.Model;

// TODO: Auto-generated Javadoc
/**
 * The Class Comment.
 */
@Entity
public class Comment extends Model {

	/** The id. */
	@Id
	public int id;

	/** The comment. */
	@Required
	@MinLength(10)
	@MaxLength(140)
	public String comment;

	/** The owner. */
	@ManyToOne
	public User owner;

	/** The product. */
	@OneToOne
	public Product product;

	/** The find. */
	static Finder<Integer, Comment> find = new Finder<Integer, Comment>(
			Integer.class, Comment.class);

	/**
	 * Instantiates a new comment.
	 *
	 * @param comment String the comment
	 * @param owner User the owner
	 * @param product Product the product
	 */
	public Comment(String comment, User owner, Product product) {
		this.comment = comment;
		this.owner = owner;
		this.product = product;
	}

	/**
	 * Creates the comment.
	 *
	 * @param comment String the comment
	 * @param owner User the owner
	 * @param product Product the product
	 * @return the id of the comment
	 */
	public static int createComment(String comment, User owner, Product product) {
		Comment c = new Comment(comment, owner, product);
		c.save();
		return c.id;
	}

	/**
	 * All comments in database.
	 *
	 * @return the list
	 */
	public static List<Comment> all() {
		return find.findList();
	}

	/**
	 * Finds the comment by its id.
	 *
	 * @param id int the id
	 * @return the comment
	 */
	public static Comment find(int id) {
		return find.byId(id);
	}

	/**
	 * Deletes the comment by its id.
	 *
	 * @param id int the id
	 */
	public static void delete(int id) {
		find.byId(id).delete();
	}
}
