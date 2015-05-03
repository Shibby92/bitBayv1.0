package models;

import java.util.List;

import javax.persistence.*;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

// TODO: Auto-generated Javadoc
/**
 * The Class Category.
 */
@Entity
public class Category extends Model {

	/** The id. */
	@Id
	public int id;

	/** The name. */
	@Required
	@ManyToOne
	public String name;
	
	/** The find. */
	static Finder<String, Category> find = new Finder<String, Category>(
			String.class, Category.class);
	
	/** The find category. */
	public static Finder<String, Category> findCategory = new Finder<String, Category>(
			String.class, Category.class);

	/** The find id. */
	static Finder<Integer, Category> findId = new Finder<Integer, Category>(
			Integer.class, Category.class);

	/**
	 * Instantiates a new category.
	 *
	 * @param name String the name of category
	 */
	public Category(String name) {
		this.name = name;
	}

	/**
	 * Creates the category.
	 *
	 * @param name String the name of category
	 */
	public static void create(String name) {
		new Category(name).save();
	}

	/**
	 * Finds category id by its name.
	 *
	 * @param name String the name of the category
	 * @return the id
	 */
	public static int categoryId(String name) {
		Category found = find.where().eq("name", name).findUnique();
		return found.id;
	}

	/**
	 * List of all categories in database.
	 *
	 * @return the list of categories
	 */
	public static List<Category> list() {
		return find.all();
	}

	/**
	 * Deletes category by its id.
	 *
	 * @param id int the id of category
	 */
	public static void delete(int id) {
		findId.byId(id).delete();

	}

	/**
	 * Updates the category.
	 *
	 * @param category String name of the category
	 */
	public static void update(Category category) {
		category.save();
	}

	/**
	 * Finds category by its id.
	 *
	 * @param id int the id
	 * @return the category
	 */
	public static Category find(int id) {
		return findId.byId(id);
	}

	/**
	 * Finds category by its name.
	 *
	 * @param name String the name of category
	 * @return the category
	 */
	public static Category findByName(String name) {
		return find.where().eq("Name", name).findUnique();
	}

}
