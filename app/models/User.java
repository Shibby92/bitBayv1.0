package models;

import helpers.HashHelper;
import helpers.MailHelper;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import play.db.ebean.Model.Finder;

import javax.persistence.*;

import play.data.DynamicForm;
import play.data.Form;
import play.data.format.Formats.DateTime;
import play.data.validation.Constraints.*;
import play.db.ebean.*;

// TODO: Auto-generated Javadoc
/**
 *The Class User
 *
 * @author eminamuratovic
 */
@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@userId")
public class User extends Model {
	
	/** The list of products. */
	@OneToMany(cascade=CascadeType.ALL, mappedBy="owner")
	public List<Product> products;
	
	/** The list of messages. */
	@OneToMany(cascade=CascadeType.ALL, mappedBy="receiver")
	public List<Message> msgs;

	/** The id. */
	@Id
	public int id;

	/** The email. */
	@Required
	@MinLength(5)
	@Column(unique = true)
	@MaxLength(10)
	@Email
	public String email;

	/** The password. */
	@Required
	@MinLength(5)
	@MaxLength(50)
	public String password;
	
	/** The username. */
	@Required
	@MinLength(6)
	@MaxLength(15)
	public String username;
	
	/** The birth_date. */
	@DateTime(pattern="yyyy-dd-mm")
	public Date birth_date;
	
	/** The user_address. */
	@MaxLength(40)
	public String user_address;
	
	/** The shipping_address. */
	@MaxLength(40)
	@Required
	public String shipping_address;
	
	/** The city. */
	@MaxLength(15)
	public String city;
	
	/** The gender. */
	@Required
	@MaxLength(1)
	public String gender;
	
	/** The admin. */
	public boolean admin;
	
	/** The blogger. */
	public boolean blogger;
	
	/** The verification. */
	public boolean verification = false;
	
	/** The confirmation. */
	public String confirmation;
	
	/** The has additional info. */
	public boolean hasAdditionalInfo;
	
	/** The user cart. */
	public Cart userCart;

	/** The rating. */
	public double rating;
	
	/** The number of ratings. */
	public int numberOfRatings;
	
	/** The order list. */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "buyer")
	public List<Orders> orderList;
	
	/** The sold orders. */
	public List<Orders> soldOrders;
	
	/** The notification. */
	@OneToMany (cascade = CascadeType.ALL, mappedBy = "seller")
	public List <Notification> notification; 

	/** The find. */
	public static Finder<Integer, User> find = new Finder<Integer, User>(Integer.class, User.class);


	/**
	 * Creates a user.
	 *
	 * @param email String the email
	 * @param password String password of the user
	 */
	public User(String email, String password) {
		this.email = email;
		this.password = password;
		this.admin = false;
		this.blogger = false;
		this.hasAdditionalInfo = false;
		this.numberOfRatings = 0;
	}
	
	/**
	 * Instantiates a new user.
	 *
	 * @param email the email
	 * @param password the password
	 * @param confirmation the confirmation
	 */
	public User(String email, String password, String confirmation) {
		this.email = email;
		this.password = password;
		this.admin = false;
		this.blogger = false;
		this.confirmation = confirmation;
		this.hasAdditionalInfo = false;

	}
	
	/**
	 * Instantiates a new user.
	 *
	 * @param email the email
	 * @param password the password
	 * @param admin the admin
	 * @param verification the verification
	 */
	public User(String email, String password, boolean admin, boolean verification) {
		this.email = email;
		this.password = password;
		this.admin = admin;
		this.blogger = false;
		this.verification = verification;
		this.hasAdditionalInfo = false;
		this.numberOfRatings = 0;

		
	}

	/**
	 * Creates a user with email and password
	 * Checks if the email already exists.
	 *
	 * @param email String the email
	 * @param password String password of the user
	 * @return true or false(if the user is registered)
	 */
	public static boolean create(String email, String password) {
		if (existsEmail(email))
			return false;
		new User(email, HashHelper.createPassword(password)).save();
		return true;
	}
	
	/**
	 * Creates the user.
	 *
	 * @param email String the email
	 * @param password String the password
	 * @return the user
	 */
	public static User createUser(String email, String password) {
		if (existsEmail(email))
			return null;
	User user=	new User(email, HashHelper.createPassword(password));
	user.save();
		return user;
	}
	
	/**
	 * Creates the user.
	 *
	 * @param email String the email
	 * @param password String the password
	 * @param confirmation String the confirmation of registration
	 * @return true, if successful
	 */
	public static boolean create(String email, String password, String confirmation) {
		if (existsEmail(email))
			return false;
		User user=new User(email, HashHelper.createPassword(password), confirmation);
		user.save();
		new Cart(user.id,user.email).save();
		return true;
	}
	
	
	
	/**
	 * Creates the use.
	 *
	 * @param user User the user
	 */
	public static void create(User user) {
		user.save();
		new Cart(user.id,user.email).save();
		
	}

	/**
	 * Checks if the email is already in database.
	 *
	 * @param email String the email
	 * @return true or false(if the email is already in database)
	 */
	public static boolean existsEmail(String email) {
		if (find.where().eq("email", email).findList().isEmpty()) {
			return false;
		}
		return true;
	}
	
	/**
	 * Checks if the username exists.
	 *
	 * @param username String the username
	 * @return true, if successful
	 */
	public static boolean existsUsername(String username) {
		if (find.where().eq("username", username).findList().isEmpty()) {
			return false;
		}
		return true;
	}

	/**
	 * Finds a user by his id.
	 *
	 * @param id int id of the user
	 * @return user
	 */
	public static User find(int id) {
		return find.byId(id);
	}
	
	/**
	 * Finds a user with his email.
	 *
	 * @param email String the email
	 * @return the user
	 */
	public static User find(String email) {
		return find.where().eq("email", email).findUnique();
	}

	/**
	 * Checking for user's email and password.
	 *
	 * @param email String the email
	 * @param password String String Password of the user
	 * @return true or false
	 */
	public static boolean checkLogin(String email, String password) {
			User foundUser = find.where().eq("email", email).findUnique();
			return HashHelper.checkPassword(password, foundUser.password) && foundUser.verification == true;
	}
	
	/**
	 * All users in database.
	 *
	 * @return list of all logged in users
	 */
	public static List<User> all() {
		return find.all();
	}
	
	/**
	 * Admins.
	 *
	 * @return all admins in our database
	 */
	public static List<User> admins(){
		return find.where().eq("admin", true).findList();
	}
	
	/**
	 * Finds a user by his confirmation string.
	 *
	 * @param confirmation String confirmation string
	 * @return the user
	 */
	public static User findByConfirmation(String confirmation) {
		return find.where().eq("confirmation", confirmation).findUnique();
	}
	
	/**
	 * Confirms that there is a user, adds verification = true in model and saves the user.
	 *
	 * @param user User
	 * @return true or false
	 */
	public static synchronized boolean confirm(User user) {
		
        if (user == null) {
            return false;
        }
   
        user.verification = true;
        user.confirmation = null;
        user.save();
            
        return true;
    }
	
	/**
	 * Deletes user by his id.
	 *
	 * @param id int the id if the user
	 */
	public static void delete(int id){
		find.byId(id).delete();
		
	}
	
	/**
	 * Edits the email verification.
	 * When admin edits users email address it sends verification mail on that address
	 *
	 * @param id int the id of the user
	 * @throws MalformedURLException the malformed url exception
	 */
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
	
	/**
	 * Additional info of the user.
	 *
	 * @param email String the email
	 * @param username String the username
	 * @param birth_date Date the birth_date
	 * @param shipping_address String the shipping_address
	 * @param user_address String the user_address
	 * @param gender String the gender
	 * @param city String the city
	 * @return true, if successful
	 */
	public static boolean AdditionalInfo(String email, String username, Date birth_date, String shipping_address, String user_address, String gender, String city) {
		User u = User.find(email);
		if(existsUsername(username) && !find(email).username.equals(username))
			return false;
		u.username = username;
		u.birth_date = birth_date;
		u.shipping_address = shipping_address;
		u.user_address = user_address;
		u.gender = gender;
		u.city = city;
		Cart c=Cart.find(u.id);
		c.shippingAddress=shipping_address;
		c.update();
		u.update();
		return true;
	}
	
	/**
	 * Gets all products that one user had sold.
	 *
	 * @param id int id of the user
	 * @return list of products
	 */
	public static List<Product> mySoldProducts(int id) {
		List<Product> pr = new ArrayList<Product>();
		for(Product p: User.find(id).products) {
			if(p.sold == true) {
				pr.add(p);
			}	
		}
		return pr;
	}

	/**
	 * Adds the sold order to list and updates seller.
	 *
	 * @param seller User the seller
	 * @param temp the temp
	 */
	public static void addSoldOrder(User seller,Orders temp) {
		seller.soldOrders.add(temp);
		seller.update();
	}

	/**
	 * Gets the unchecked notifications.
	 *
	 * @param id int the id of the user
	 * @return the unchecked notifications
	 */
	public static List<Orders> getUncheckedNotifications(int id) {
		User u = User.find(id);
		List<Orders> unchecked= new ArrayList<Orders>();
		for(Notification notification: u.notification){
			if(notification.isUnchecked){
				unchecked.add(Orders.find(notification.orderId));
			}
		}
		return unchecked;
	}



}
