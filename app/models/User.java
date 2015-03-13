package models;


import helpers.HashHelper;

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
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="owner")
	public List<Product> products;

	@Id
	public int id;

	@Required
	@MinLength(5)
	@Column(unique = true)
	@MaxLength(10)
	@Email
	public String email;

	@Required
	@MinLength(5)
	@MaxLength(100)
	public String password;
	
	public boolean admin;
	
	public boolean verification = false;
	
	public String confirmation;

	static Finder<Integer, User> find = new Finder<Integer, User>(
			Integer.class, User.class);

	/**
	 * creates a user
	 * @param username String email of the user
	 * @param password String password of the user
	 */
	public User(String email, String password) {
		this.email = email;
		this.password = password;
		this.admin = false;
	}
	
	public User(String email, String password, String confirmation) {
		this.email = email;
		this.password = password;
		this.admin = false;
		this.confirmation = confirmation;
	}
	
	public User(String email, String password, boolean admin, boolean verification) {
		this.email = email;
		this.password = password;
		this.admin = admin;
		this.verification = verification;
	}

	/**
	 * Creates a user with email and password
	 * Checks if the email already exists
	 * @param username String email of the user
	 * @param password String password of the user
	 * @param username String email of the user
	 * @param password String password of the user
	 * @return true or false(if the user is registered)
	 */
	public static boolean create(String email, String password) {
		if (existsEmail(email))
			return false;
		new User(email, HashHelper.createPassword(password)).save();
		return true;
	}
	
	public static boolean create(String email, String password, String confirmation) {
		if (existsEmail(email))
			return false;
		new User(email, HashHelper.createPassword(password), confirmation).save();
		return true;
	}
	
	public static void create(User user) {
		new User(user.email, user.password, user.admin, user.verification).save();
	}

	/**
	 * checks if the email is already in database
	 * @param username String email of the user
	 * @return true or false(if the email is already in database)
	 */
	public static boolean existsEmail(String email) {
		if (find.where().eq("email", email).findList().isEmpty()) {
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
	 * finds a user with his email
	 * @param username String email of the user
	 * @return the user
	 */
	public static User find(String email) {
		return find.where().eq("email", email).findUnique();
	}

	/**
	 * Checking for user's email and password
	 * @param username String The email of the user
	 * @param password String Password of the user
	 * @return true or false
	 */
	public static boolean checkLogin(String email, String password) {
			User foundUser = find.where().eq("email", email).findUnique();
			return HashHelper.checkPassword(password, foundUser.password) && foundUser.verification == true;
	}
	
	/**
	 * @return list of all logged in users
	 */
	public static List<User> all() {
		return find.all();
	}
	
	/**
	 * finds a user by his confirmation string
	 * @param confirmation String confirmation string
	 * @return the user
	 */
	public static User findByConfirmation(String confirmation) {
		return find.where().eq("confirmation", confirmation).findUnique();
	}
	
	/**
	 * confirms that there is a user 
	 * adds verification = true in model
	 * saves the user
	 * @param u User
	 * @return true or false
	 */
	public static boolean confirm(User u) {
        if (u == null) {
            return false;
        }
       
        u.verification = true;
        u.confirmation = null;
        u.save();
        return true;
    }
	
	public static void delete(int id){
		find.byId(id).delete();
		
	}
	public static void update(User user) {
		user.save();
	}


}
