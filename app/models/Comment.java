package models;

import java.util.List;

import javax.persistence.*;

import play.data.validation.Constraints.*;
import play.db.ebean.Model;

@Entity
public class Comment extends Model {

	@Id
	public int id;

	@Required
	@MinLength(10)
	@MaxLength(140)
	public String comment;

	@ManyToOne
	public User owner;

	@OneToOne
	public Product product;

	static Finder<Integer, Comment> find = new Finder<Integer, Comment>(
			Integer.class, Comment.class);

	public Comment(String comment, User owner, Product product) {
		this.comment = comment;
		this.owner = owner;
		this.product = product;
	}

	/**
	 * creates new comment and saves it to database
	 * @param comment String comment
	 * @param owner User owner of the comment
	 * @param product Product comment will be connected to this product
	 * @return id of the comment
	 */
	public static int createComment(String comment, User owner, Product product) {
		Comment c = new Comment(comment, owner, product);
		c.save();
		return c.id;
	}

	/**
	 * @return list of all comments
	 */
	public static List<Comment> all() {
		return find.findList();
	}

	/**
	 * @param id int id of the comment
	 * @return comment
	 */
	public static Comment find(int id) {
		return find.byId(id);
	}

	/**
	 * finds a comment by his content
	 * @param comment String content of the comment
	 * @return true or false
	 */
	public static boolean find(String comment) {
		return find.where().eq("comment", comment).findUnique() != null;
	}

	/**
	 * deletes a comment by his id from the site and database
	 * @param id int id of the comment
	 */
	public static void delete(int id) {
		find.byId(id).delete();
	}
}
