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
	
	static Finder<String, Category> find = new Finder<String, Category>(
			String.class, Category.class);
	public static Finder<String, Category> findCategory = new Finder<String, Category>(
			String.class, Category.class);

	static Finder<Integer, Category> findId = new Finder<Integer, Category>(
			Integer.class, Category.class);

	public Category(String name) {
		this.name = name;
	}

	/**
	 * creates category
	 * saves it into database
	 * @param name String name of the category
	 */
	public static void create(String name) {
		new Category(name).save();
	}

	/**
	 * finds category by its name
	 * @param name String name of the category
	 * @return category
	 */
	public static int categoryId(String name) {
		Category found = find.where().eq("name", name).findUnique();
		return found.id;
	}

	/**
	 * gets all categories from database
	 * @return list
	 */
	public static List<Category> list() {
		return find.all();
	}

	/**
	 * deletes category from the site and from the database
	 * @param id int id of the category
	 */
	public static void delete(int id) {
		findId.byId(id).delete();

	}

	/**
	 * updates the category
	 * @param category String category
	 */
	public static void update(Category category) {
		category.save();
	}

	/**
	 * finds category by id
	 * @param id int id of the category
	 * @return category
	 */
	public static Category find(int id) {
		return findId.byId(id);
	}

	/**
	 * finds category by its name
	 * @param name String name of the category
	 * @return category
	 */
	public static Category findByName(String name) {
		return find.where().eq("Name", name).findUnique();
	}

}
