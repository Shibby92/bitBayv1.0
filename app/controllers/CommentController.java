package controllers;

import java.util.List;

import models.*;
import helpers.*;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.*;
import views.html.*;

public class CommentController extends Controller {
	
	/**
	 * Gets info from page where user adds his comment to product
	 * @param id int id of the product
	 * @return the result
	 */
	@Security.Authenticated(UserFilter.class)
	public static Result addNewComment(int id) {
		DynamicForm form = Form.form().bindFromRequest();
		Product p = Product.find(id);
		String comment = form.get("comment");
		Comment.createComment(comment, User.find(session().get("email")), p);
		Logger.info("New comment has been added: " + comment);

		List<Comment> list2 = Comment.all();

		flash("success", "New comment added!");
		return ok(itempage.render(session("email"), Product.find(id),
				FAQ.all(), models.Image.photosByProduct(p), list2, Category.list()));

	}
	
	/**
	 * Deletes comment from the product.
	 *
	 * @param id int id of the comment
	 * @param p_id int id of the product
	 * @return the result
	 */
	@Security.Authenticated(AdminFilter.class)
	public static Result deleteComment(int id, int p_id) {
		Comment.delete(id);
		Logger.warn("Comment with id: " + id + " has been deleted");
		flash("success", "Comment deleted!");
		return redirect("/itempage/" + p_id);

	}

}
