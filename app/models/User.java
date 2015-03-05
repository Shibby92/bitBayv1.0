package models;

import java.security.*;
import java.util.List;

import javax.persistence.*;

import play.data.validation.Constraints.*;
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
		new User(username, hashPw(password)).save();
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
	 * Hashes the password in SimpleMD5
	 * @param passwordToHash String Password that needs to be hashed
	 * @return Hashed password
	 */
	public static String hashPw(String passwordToHash) {
		String generatedPassword = null;
		try {
			// Create MessageDigest instance for MD5
			MessageDigest md = MessageDigest.getInstance("MD5");
			// Add password bytes to digest
			md.update(passwordToHash.getBytes());
			// Get the hash's bytes
			byte[] bytes = md.digest();
			// This bytes[] has bytes in decimal format;
			// Convert it to hexadecimal format
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)
						.substring(1));
			}
			// Get complete hashed password in hex format
			generatedPassword = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return generatedPassword;
	}


	/**
	 * Checking for user's usename and password
	 * @param username String The username of the user
	 * @param password String Password of the user
	 * @return true or false
	 */
	public static boolean checkLogin(String username, String password) {
		String hashedPw = hashPw(password);

		if (find.where().eq("username", username).findList().isEmpty()) {
			return false;
		} else {
			User foundUser = find.where().eq("username", username).findUnique();
			if (foundUser.password.equals(hashedPw)) {
				return true;
			}
		}
		return false;
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
