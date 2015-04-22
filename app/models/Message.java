package models;

import java.util.List;

import javax.persistence.*;

import play.data.validation.Constraints.*;
import play.db.ebean.Model;

@Entity
public class Message extends Model{
	
	@Id
	public int id;
	
	@Required
	@MinLength(10)
	@MaxLength(240)
	public String content;
	
	@ManyToOne
	public User sender;
	
	@ManyToOne
	public User receiver;
	
	@ManyToOne
	public Product product;
	
	public String subject;
	
	
	public static Finder<Integer, Message> find = new Finder<Integer, Message>(Integer.class, Message.class);
	
	public Message(String content, User sender, User receiver, String subject) {
		this.content = content;
		this.sender = sender;
		this.receiver = receiver;
		this.subject = subject;
		
	}
	
	public Message(String content, User sender, User receiver, Product p, String subject) {
		this.content = content;
		this.sender = sender;
		this.receiver = receiver;
		this.product = p;
		this.subject = subject;
		
	}
	
	public Message() {
		this.content = "no content";
		this.sender = null;
		this.receiver = null;
	}
	
	public static Message create(String content, User sender, User reciever, String subject) {
		Message msg = new Message(content, sender, reciever, subject);
		msg.save();
		return msg;
	}
	
	public static Message createReport(String content, User sender, User reciever, Product p, String subject) {
		Message msg = new Message(content, sender, reciever, p, subject);
		msg.save();
		return msg;
	}
	
	public static void delete(int id) {
		find.byId(id).delete();
	}
	
	public static List<Message> all(User receiver) {
		List<Message> msg = find.where("receiver_id = " + receiver.id).findList();
		return msg;
	}
	
	public static Message find(int id){
		return find.byId(id);
	}

}
