package models;

<<<<<<< HEAD

import helpers.HashHelper;

=======
>>>>>>> 859d6eeae94c6ae588bb01980640adb81d0da47c
import java.security.*;
import java.util.List;

import javax.persistence.*;

import play.data.validation.Constraints.*;
import play.db.ebean.Model;

/**
 * Creates a user 
 * Checks if the user is already registered 
<<<<<<< HEAD
 * Finds user by his id
=======
 * Finds user by his id 
>>>>>>> registerButtonHikmet
 * @author eminamuratovic
 *
 */
@Entity
public class User extends Model {
	
	@OneToMany(mappedBy="owner")
	public List<Product> products;

	@Id
	public int id;

	@Required
	@MinLength(5)
	@Email
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
		this.password = hashPw(password);
	}

	/**
	 * Creates a user with username and password
	 * Checks if the username already exists
	 * @param username String username of the user
	 * @param password String password of the user
	 * @param username String username of the user
	 * @param password String password of the user
	 * @return true or false(if the user is registered)
	 */
	public static boolean create(String username, String password) {
		if (existsUsername(username))
			return false;
		new User(username, HashHelper.createPassword(password)).save();
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

	/**
	 * Checking for user's username and password
	 * @param username String The username of the user
	 * @param password String Password of the user
	 * @return true or false
	 */
	public static boolean checkLogin(String username, String password) {
		if (find.where().eq("username", username).findList().isEmpty()) {
			return false;
		} else {
			User foundUser = find.where().eq("username", username).findUnique();
			return HashHelper.checkPassword(password, foundUser.password);
		}
	}
	
	/**
	 * finds user with his username
	 * @param username String username
	 * @return user by that username
	 */
	public static User find(String username) {
		return find.where().eq("username", username).findUnique();
	}

}
