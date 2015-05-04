package models;

import java.util.List;

import javax.persistence.*;

import play.data.validation.Constraints.*;
import play.db.ebean.Model;


// TODO: Auto-generated Javadoc
/**
 * The Class Message.
 */
@Entity
public class Message extends Model{

	/** The id. */
	@Id
	public int id;

	/** The content. */
	@Required
	@MinLength(10)
	@MaxLength(240)
	public String content;
	
	/** The sender. */
	@ManyToOne
	public User sender;
	
	/** The receiver. */
	@ManyToOne
	public User receiver;
	
	/** The product. */
	@ManyToOne
	public Product product;
	
	/** The subject. */
	public String subject;
	
	
	/** The find. */
	public static Finder<Integer, Message> find = new Finder<Integer, Message>(Integer.class, Message.class);
	
	/**
	 * Instantiates a new message.
	 *
	 * @param content the content
	 * @param sender the sender
	 * @param receiver the receiver
	 * @param subject the subject
	 */
	public Message(String content, User sender, User receiver, String subject) {
		this.content = content;
		this.sender = sender;
		this.receiver = receiver;
		this.subject = subject;

	}

	/**
	 * Instantiates a new message.
	 *
	 * @param content String the content
	 * @param sender User the sender
	 * @param receiver User the receiver
	 * @param product Product product
	 * @param subject String the subject
	 */
	public Message(String content, User sender, User receiver, Product product,
			String subject) {
		this.content = content;
		this.sender = sender;
		this.receiver = receiver;
		this.product = product;
		this.subject = subject;
		
	}
	
	/**
	 * Instantiates a new message.
	 */
	public Message() {
		this.content = "no content";
		this.sender = null;
		this.receiver = null;
	}



	/**
	 * Creates the message.
	 *
	 * @param content String the content
	 * @param sender User the sender
	 * @param receiver User the receiver
	 * @param subject String the subject
	 * @return the message
	 */
	public static Message create(String content, User sender, User reciever,
			String subject) {
		Message msg = new Message(content, sender, reciever, subject);
		msg.save();
		return msg;
	}

	/**
	 * Creates the report for one product.
	 *
	 * @param content String the content
	 * @param sender User the sender
	 * @param receiver User the receiver
	 * @param product Product product
	 * @param subject String the subject
	 * @return the message
	 */
	public static Message createReport(String content, User sender,
			User reciever, Product product, String subject) {
		Message msg = new Message(content, sender, reciever, product, subject);
		msg.save();
		return msg;
	}

	/**
	 * Deletes a message from database.
	 *
	 * @param id int the id of the message
	 */
	public static void delete(int id) {
		find.byId(id).delete();
	}

	/**
	 * All messages from one user.
	 *
	 * @param receiver User the receiver
	 * @return the list
	 */
	public static List<Message> all(User receiver) {
		List<Message> msg = find.where("receiver_id = " + receiver.id)
				.findList();
		return msg;
	}


	/**
	 * Finds a message by its id.
	 *
	 * @param id int the id
	 * @return the message
	 */
	public static Message find(int id) {
		return find.byId(id);
	}

}
