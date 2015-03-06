package models;

import java.util.Date;
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

	public Category(int id, String name) {

		this.id = id;
		this.name = name;

	}

	public static void create(int id, String name, Date created) {
		new Category(id, name).save();

	}
	static Finder <String,Category> find= new Finder<String,Category> (String.class,Category.class);

	public static int categoryId(String name) {
		Category found=find.where().eq("name", name).findUnique();
		return found.id;
	}

}
