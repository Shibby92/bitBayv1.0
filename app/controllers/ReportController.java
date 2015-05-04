package controllers;

import helpers.*;
import models.*;
import play.Logger;
import play.mvc.*;
import views.html.*;

// TODO: Auto-generated Javadoc
/**
 * The Class ReportController.
 */
public class ReportController extends Controller {

	/**
	 * Deletes report.
	 *
	 * @param id int the id of the report
	 * @return the result
	 */
	@Security.Authenticated(UserFilter.class)
	public static Result deleteReport(int id) {

		Logger.warn("Report with product id: " + Report.find(id).reportedProduct.id + " has been deleted");
		Report.delete(id);
		flash("success", "Report deleted!");
		return redirect("/profile");

	}

}
