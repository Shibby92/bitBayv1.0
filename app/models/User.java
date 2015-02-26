package models;

import javax.persistence.*;

import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;


@Entity
public class User extends Model {

	@Id
	public int id;

	@Required
	@MinLength(5)
	public String username;

	@Required
	@MinLength(5)
	public String password;

	static Finder<Integer, User> find = new Finder<Integer, User>(
			Integer.class, User.class);

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public static void create(String username, String password) {
		new User(username, password).save();

	}

	public static void create(User u) {
		u.save();
	}

	/**
	 * Da li je username vec iskoristen
	 * 
	 * @param username
	 *            Username koji se provjerava
	 * @return true/false ovisno o ishodu
	 */
	public static boolean existsUsername(String username) {
		if (find.where().eq("username", username).findList().isEmpty()) {
			return false;
		}
		return true;
	}
}
