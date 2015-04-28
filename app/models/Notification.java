package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;

@Entity
public class Notification extends Model {
	
	public static Finder <Integer, Notification> finder= new Finder<Integer,Notification>(Integer.class,Notification.class);

	@Id
	public int id;

	@ManyToOne
	public User seller;

	public int orderId;

	public boolean isUnchecked;

	public Notification(User seller, Orders order) {
		this.seller = seller;
		this.orderId = order.id;
		this.isUnchecked = true;
	}

	public static void createNotification(User seller, Orders order) {
		Notification n = new Notification(seller, order);
		n.save();
	}

}