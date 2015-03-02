package models;

import javax.persistence.*;

import play.db.ebean.Model;

public class Category extends Model {
	
	@Id
	public int id;

	public static boolean categoryExists(String name) {
		return false;
	}
	
	public static int categoryId(String name) {
		return 0;
	}
	
	 
}
