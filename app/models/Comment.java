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
	
	static Finder<Integer, Comment> find = new Finder<Integer, Comment>(Integer.class, Comment.class);
	
	
	public Comment(String comment, User owner) {
		this.comment = comment;
		this.owner = owner;
	}
	
	public static int createComment(String comment, User owner) {
		Comment c = new Comment(comment, owner);
		c.save();
		return c.id;
	}
	
	public static List<Comment> all() {
		return find.findList();
	}
	
	public static Comment find(int id) {
		return find.byId(id);
	}
	
	public static boolean find(String comment) {
		return find.where().eq("comment", comment).findUnique() != null;
	}
	
	public static void delete(int id) {
		find.byId(id).delete();
	}
}
