package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class Report extends Model {
	
	@Id
	public int id;

	@ManyToOne
	public Product reportedProduct;
	
	@ManyToOne
	public User reporter;
	
	@ManyToOne
	@Required
	@MinLength(10)
	@MaxLength(240)
	public String message;

	public static Finder<Integer, Report> find = new Finder<Integer, Report>(Integer.class, Report.class);
	
	public Report(Product reportedProduct, User reporter, String message) {
		this.reportedProduct = reportedProduct;
		this.reporter = reporter;
		this.message = message;
	}
	
	public static Report report(Product reportedProduct, User reporter, String message) {
		Report r = new Report(reportedProduct, reporter, message);
		r.save();
		return r;
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
	
	public static int sizeReportsByProduct(Product product) {
		List<Report> reportsByProduct = find.where().eq("reportedProduct",product).findList();
		if(reportsByProduct == null)
			reportsByProduct = new ArrayList<Report>();
		return reportsByProduct.size();
	}
		
	public static Report find(int id) {
		return find.byId(id);
	}
	
	public static void delete(int id){
		List<Report> all = Report.findByProduct(Report.find(id).reportedProduct);
		for(Report report: all)
			report.delete();
	}
	

	
	
}
