package controllers;

import models.Report;
import helpers.UserFilter;
import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

public class ReportController extends Controller {
	
	/**
	 * deletes message from his mail
	 * @param id int id of the message
	 * @return result
	 */
	@Security.Authenticated(UserFilter.class)
	public static Result deleteReport(int id) {

		Logger.warn("Report with product id: " + Report.find(id).reportedProduct.id + " has been deleted");
		Report.delete(id);
		flash("success", "Report deleted!");
		return redirect("/profile");

	}

}
