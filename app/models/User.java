package models;


import helpers.HashHelper;
import helpers.MailHelper;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.avaje.ebean.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;

import play.data.DynamicForm;
import play.data.Form;
import play.data.format.Formats.DateTime;
import play.data.validation.Constraints.*;
import play.db.ebean.Model;
import play.db.ebean.Model.Finder;
import play.db.ebean.*;



/**
 * Creates a user 
 * Checks if the user is already registered 
 * Finds user by his id 
 * @author eminamuratovic
 *
 */
@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@userId")
public class User extends Model {
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="owner")
	//@JsonManagedReference
	public List<Product> products;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="receiver")
	public List<Message> msgs;

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
	@MaxLength(50)
	public String password;
	
	@Required
	@MinLength(6)
	@MaxLength(15)
	public String username;
	
	@DateTime(pattern="yyyy-dd-mm")
	public Date birth_date;
	
	@MaxLength(40)
	public String user_address;
	
	@MaxLength(40)
	@Required
	public String shipping_address;
	
	@MaxLength(15)
	public String city;
	
	@Required
	@MaxLength(1)
	public String gender;
	
	public boolean admin;
	
	public boolean verification = false;
	
	public String confirmation;
	
	public boolean hasAdditionalInfo;
	
	public Cart userCart;

	public double rating;
	
	public int numberOfRatings;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "buyer")
	public List<Orders> orderList;
	
	@OneToMany(cascade= CascadeType.ALL, mappedBy = "seller")
	public List<Orders> soldOrders;

	public static Finder<Integer, User> find = new Finder<Integer, User>(Integer.class, User.class);
	public static Finder<Integer, User> findUser = new Finder<Integer, User>(Integer.class, User.class);


	/**
	 * creates a user
	 * @param username String email of the user
	 * @param password String password of the user
	 */
	public User(String email, String password) {
		this.email = email;
		this.password = password;
		this.admin = false;
		this.hasAdditionalInfo = false;
		this.numberOfRatings = 0;
		//this.userCart=new Cart(this.id,email);
	}
	
	public User(String email, String password, String confirmation) {
		this.email = email;
		this.password = password;
		this.admin = false;
		this.confirmation = confirmation;
		this.hasAdditionalInfo = false;
		//this.userCart=new Cart(this.id,email);

	}
	
	public User(String email, String password, boolean admin, boolean verification) {
		this.email = email;
		this.password = password;
		this.admin = admin;
		this.verification = verification;
		this.hasAdditionalInfo = false;
		this.numberOfRatings = 0;
	//	this.userCart=new Cart(this.id,email);

		
	}

	public User() {
		
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
	
	public static User createUser(String email, String password) {
		if (existsEmail(email))
			return null;
	User user=	new User(email, HashHelper.createPassword(password));
	user.save();
		return user;
	}
	public static boolean create(String email, String password, String confirmation) {
		if (existsEmail(email))
			return false;
		User user=new User(email, HashHelper.createPassword(password), confirmation);
		user.save();
		new Cart(user.id,user.email).save();
		return true;
	}
	
	
	
	public static void create(User user) {
		user.save();
		new Cart(user.id,user.email).save();
		
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
	
	//checks if there is the same username in database
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
	 * 
	 * @return all admins in our database
	 */
	public static List<User>admins(){
		return find.where().eq("admin", true).findList();
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
	public static synchronized boolean confirm(User u) {
		
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
	
	//when admin edits users email address it sends verification mail on that address
	public static void editEmailVerification(int id) throws MalformedURLException {
		DynamicForm form = Form.form().bindFromRequest();
		User u = User.find(id);
		u.email = form.get("email");
		u.confirmation = UUID.randomUUID().toString();
		u.verification = false;
		
		String urlS = "http://localhost:9000" + "/" + "confirm/" + u.confirmation;
		URL url = new URL(urlS);
		MailHelper.send(u.email, url.toString()); 
		
		u.update();
	
	}
	
	//updates user with his additional info
	public static boolean AdditionalInfo(String email, String username, Date birth_date, String shipping_address, String user_address, String gender, String city) {
		User u = User.find(email);
		if(existsUsername(username) && !u.find(email).username.equals(username))
			return false;
		u.username = username;
		u.birth_date = birth_date;
		u.shipping_address = shipping_address;
		u.user_address = user_address;
		u.gender = gender;
		u.city = city;
		u.update();
		return true;
	}
	

	public static List<User> findAll() {
		return find.all();
	}

	public static void addSoldOrder(User seller,Orders temp) {
		seller.soldOrders.add(temp);
		seller.update();
	}



}
