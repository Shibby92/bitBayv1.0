package models;

import javax.persistence.*;

import play.db.ebean.Model;

// TODO: Auto-generated Javadoc
/**
 * The Class Notification.
 */
@Entity
public class Notification extends Model {

	/** The finder. */
	public static Finder<Integer, Notification> finder = new Finder<Integer, Notification>(
			Integer.class, Notification.class);

	/** The id. */
	@Id
	public int id;

	/** The seller. */
	@ManyToOne
	public User seller;

	/** The order id. */
	public int orderId;

	/** The is unchecked. */
	public boolean isUnchecked;

	/**
	 * Instantiates a new notification.
	 *
	 * @param seller User the seller
	 * @param order Orders the order
	 */
	public Notification(User seller, Orders order) {
		this.seller = seller;
		this.orderId = order.id;
		this.isUnchecked = true;
	}

	/**
	 * Creates the notification.
	 *
	 * @param seller User the seller
	 * @param order Orders the order
	 */
	public static void createNotification(User seller, Orders order) {
		Notification n = new Notification(seller, order);
		n.save();
	}

}