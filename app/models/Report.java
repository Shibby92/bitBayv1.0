package models;

import java.util.*;

import javax.persistence.*;

import play.db.ebean.Model;

@Entity
public class Report extends Model {
	
	@Id
	public int id;

	public Product reportedProduct;
	
	public User reporter;
	
	public String message;

	public static Finder<Integer, Report> find = new Finder<Integer, Report>(Integer.class, Report.class);
	
	public Report(Product reportedProduct, User reporter, String message) {
		this.reportedProduct = reportedProduct;
		this.reporter = reporter;
		this.message = message;
	}
	
	public static void report(Product reportedProduct, User reporter, String message) {
		new Report(reportedProduct, reporter, message).save();
	}
	
	public static List<Report> all() {
		List<Report> all = find.all();
		if(all == null) {
			all = new ArrayList<Report>();
		}
		return all;
	}
	
	public static List<Report> findByProduct(Product product) {
		List<Report> reportsByProduct = find.where().eq("reportedProduct",product).findList();
		if(reportsByProduct == null)
			reportsByProduct = new ArrayList<Report>();
		return reportsByProduct;
	}
	

	
	
}
