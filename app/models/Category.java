package models;

import java.util.List;

import javax.persistence.*;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class Category extends Model {

	@Id
	public int id;

	@Required
	@ManyToOne
	public String name;

	public Category(String name) {
		this.name = name;

	}

	public static void create(String name) {
		new Category(name).save();

	}

	public static Finder<String, Category> find = new Finder<String, Category>(
			String.class, Category.class);

	public static int categoryId(String name) {
		Category found = find.where().eq("name", name).findUnique();
		return found.id;
	}

	public static List list() {
		return find.all();
	}

	static Finder<Integer, Category> findId = new Finder<Integer, Category>(
			Integer.class, Category.class);

	// method which will find id of the category and delete it, else will throw
	// exception,method is used in CategoryApplication
	public static void delete(int id) {
		findId.byId(id).delete();

	}

	public static void update(Category category) {
		category.save();
	}

	public static Category find(int id) {
		return findId.byId(id);
	}

}
