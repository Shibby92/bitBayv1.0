package models;

import java.util.*;

import javax.persistence.*;


import play.db.ebean.Model;

@Entity
public class Notification extends Model {
	
	@Id
	public int id;

	@ManyToOne
	public User owner;
	
	@OneToOne
	public User buyer;
	
	@OneToOne
	public Product product;
	
	public Notification(User owner, User  buyer, Product product){
		this.owner = owner;
		this.buyer = buyer;
		this.product = product;
	}
	
	
	public static void createNotification(User owner, User buyer, Product product){
		new Notification(owner, buyer, product).save();
	}
	
	static Finder<Integer, Notification> find = new Finder<Integer, Notification>(Integer.class, Notification.class);
	
	public static void clearNotifications(int id){
		Notification n = find.byId(id);
		n.delete();
	}
	
	public static List<Notification> byOwner(String email){
		User owner = User.find(email);
		List<Notification> list = find.where().eq("owner", owner).findList();
		if(list == null)
			list = new ArrayList<Notification>();
		for(Notification n: list){
			System.out.println("Product names in notif... "+n.product.name);
		}
		return list;
			
	}
}
