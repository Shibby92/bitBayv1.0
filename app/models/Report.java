package models;

import java.util.*;

import javax.persistence.*;

import play.data.validation.Constraints.*;
import play.db.ebean.Model;

// TODO: Auto-generated Javadoc
/**
 * The Class Report.
 */
@Entity
public class Report extends Model {

	/** The id. */
	@Id
	public int id;

	/** The reported product. */
	@ManyToOne
	public Product reportedProduct;

	/** The reporter. */
	@ManyToOne
	public User reporter;

	/** The message. */
	@ManyToOne
	@Required
	@MinLength(10)
	@MaxLength(240)
	public String message;

	/** The find. */
	public static Finder<Integer, Report> find = new Finder<Integer, Report>(
			Integer.class, Report.class);

	/**
	 * Instantiates a new report.
	 *
	 * @param reportedProduct Product the reported product
	 * @param reporter User the reporter
	 * @param message String the message
	 */
	public Report(Product reportedProduct, User reporter, String message) {
		this.reportedProduct = reportedProduct;
		this.reporter = reporter;
		this.message = message;
	}

	/**
	 * Creates a report and saves it in database.
	 *
	 * @param reportedProduct Product the reported product
	 * @param reporter User the reporter
	 * @param message String the message
	 * @return the report
	 */
	public static Report report(Product reportedProduct, User reporter,
			String message) {
		Report r = new Report(reportedProduct, reporter, message);
		r.save();
		return r;
	}

	/**
	 * All reports form database.
	 *
	 * @return the list
	 */
	public static List<Report> all() {
		List<Report> all = find.all();
		if (all == null) {
			all = new ArrayList<Report>();
		}
		return all;
	}

	/**
	 * Find report by product.
	 *
	 * @param product Product the product
	 * @return the list
	 */
	public static List<Report> findByProduct(Product product) {
		List<Report> reportsByProduct = find.where()
				.eq("reportedProduct", product).findList();
		if (reportsByProduct == null)
			reportsByProduct = new ArrayList<Report>();
		return reportsByProduct;
	}

	/**
	 * Find report by id.
	 *
	 * @param id int the id of report
	 * @return the report
	 */
	public static Report find(int id) {
		return find.byId(id);
	}

	/**
	 * Deletes report by its id.
	 *
	 * @param id int the id of report
	 */
	public static void delete(int id) {
		List<Report> all = Report
				.findByProduct(Report.find(id).reportedProduct);
		for (Report report : all)
			report.delete();
	}

}
