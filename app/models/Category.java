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

	public Category( String name) {
		this.name = name;

	}

	public static void create(String name) {
		new Category(name).save();

	}
	static Finder <String,Category> find= new Finder<String,Category> (String.class,Category.class);

	public static int categoryId(String name) {
		Category found=find.where().eq("name", name).findUnique();
		return found.id;
	}
	public static List list(){
		return find.all();
	}

}
