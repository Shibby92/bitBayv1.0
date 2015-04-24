package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import play.db.ebean.Model;

@Entity
public class Notification extends Model {

	@Id
	public int id;

	@ManyToOne
	public User seller;

	public Orders order;

	public boolean isUnchecked;

	public Notification(User seller, Orders order) {
		this.seller = seller;
		this.order = order;
		this.isUnchecked = true;
	}

	public static void createNotification(User seller, Orders order) {
		Notification n = new Notification(seller, order);
		n.save();
	}

}
