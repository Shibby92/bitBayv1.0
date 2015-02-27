package models;

import javax.persistence.*;

import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

/**
 * Creates a user
 * Checks if the user is already registered
 * Finds user by his id
 * @author eminamuratovic
 *
 */
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

	/**
	 * creates a user
	 * @param username String username of the user
	 * @param password String password of the user
	 */
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	/**
	 * Creates a user with username and password
	 * @param username String username of the user
	 * @param password String password of the user
	 * @return true or false(if the user is registered)
	 */
	public static boolean create(String username, String password) {
		if(existsUsername(username))
			return false;
		new User(username, password).save();
		return true;

	}

	/**
	 * checks if the username is already in database
	 * @param username String username of the user
	 * @return true or false(if the username is already in database)
	 */
	public static boolean existsUsername(String username) {
		if (find.where().eq("username", username).findList().isEmpty()) {
			return false;
		}
		return true;
	}
	
	/**
	 * finds a user by his id
	 * @param id int id of the user
	 * @return user
	 */
	public static User find(int id) {
		return find.byId(id);
	}
	
}
