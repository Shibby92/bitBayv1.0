package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

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
	 * @param content the content
	 * @param sender the sender
	 * @param receiver the receiver
	 * @param p the p
	 * @param subject the subject
	 */
	public Message(String content, User sender, User receiver, Product p, String subject) {
		this.content = content;
		this.sender = sender;
		this.receiver = receiver;
		this.product = p;
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
	 * Creates the.
	 *
	 * @param content the content
	 * @param sender the sender
	 * @param reciever the reciever
	 * @param subject the subject
	 * @return the message
	 */
	public static Message create(String content, User sender, User reciever, String subject) {
		Message msg = new Message(content, sender, reciever, subject);
		msg.save();
		return msg;
	}
	
	/**
	 * Creates the report.
	 *
	 * @param content the content
	 * @param sender the sender
	 * @param reciever the reciever
	 * @param p the p
	 * @param subject the subject
	 * @return the message
	 */
	public static Message createReport(String content, User sender, User reciever, Product p, String subject) {
		Message msg = new Message(content, sender, reciever, p, subject);
		msg.save();
		return msg;
	}
	
	/**
	 * Delete.
	 *
	 * @param id the id
	 */
	public static void delete(int id) {
		find.byId(id).delete();
	}
	
	/**
	 * All.
	 *
	 * @param receiver the receiver
	 * @return the list
	 */
	public static List<Message> all(User receiver) {
		List<Message> msg = find.where("receiver_id = " + receiver.id).findList();
		return msg;
	}
	
	/**
	 * Find.
	 *
	 * @param id the id
	 * @return the message
	 */
	public static Message find(int id){
		return find.byId(id);
	}

}
