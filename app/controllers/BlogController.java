package controllers;

import helpers.AdminAndBloggerFilter;
import helpers.Session;
import helpers.UserFilter;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import com.google.common.io.Files;

import models.Blog;
import models.User;
import play.Logger;
import play.Play;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import views.html.*;

/**
 * Class BlogController
 * @author nermingraca
 *
 */
public class BlogController extends Controller {

	/**
	 * Method renders page which shows already published blogs
	 * @return result
	 */
	public static Result blogsPage() {
		Logger.info(Play.application().configuration()
				.getString("blogControllerLogger1"));
		String email = session().get("email");
		List<Blog> blogs = Blog.allBlogs();
		return ok(blog.render(email, blogs));
	}

	/**
	 * Adds a new blog
	 * @return reloaded page with a flash whether it has successfully added a new blog or not
	 */
	public static Result addNewBlog() {
		DynamicForm form = Form.form().bindFromRequest();
		try {
			String title = form.get("title");
			String content = form.get("content");
			String imgPath = savePicture();
			User user = Session.getCurrentUser(ctx());
			Blog.createBlog(title, content, imgPath, user.id, user.username);
			Logger.info(Play.application().configuration()
					.getString("blogControllerLogger2")
					+ title);
			flash("success",
					Play.application().configuration()
							.getString("blogControllerFlash1"));
			return redirect("/blog");
		} catch (Exception e) {
			Logger.error(Play.application().configuration()
					.getString("blogControllerLogger3"));
			flash("error",
					Play.application().configuration()
							.getString("blogControllerFlash2"));
			return redirect("/blog");
		}
	}

	/**
	 * Method renders the page in which new blog is created
	 * @return
	 */
	@Security.Authenticated(AdminAndBloggerFilter.class)
	public static Result toAddNewBlog() {
		Logger.info(Play.application().configuration()
				.getString("blogControllerLogger4"));
		String email = session().get("email");
		return ok(newblog.render(email));
	}

	/**
	 * Updating a blog
	 * @param id int Id of the blog
	 * @return reloaded page with a flash whether it has successfully updated a blog or not
	 */
	public static Result updateBlog(int id) {
		DynamicForm form = Form.form().bindFromRequest();
		Blog currentBlog = Blog.findBlogById(id);

		try {
			currentBlog.title = form.get("title");
			currentBlog.content = form.get("content");
			String imgPath = savePicture();
			currentBlog.blogImagePath = imgPath;
			currentBlog.update();
			flash("success",
					Play.application().configuration()
							.getString("blogControllerFlash3"));

			return redirect("/blog");
		} catch (Exception e) {
			Logger.error(Play.application().configuration()
					.getString("blogControllerLogger5"));
			flash("error",
					Play.application().configuration()
							.getString("blogControllerFlash4"));
			return redirect("/blog");
		}

	}

	/**
	 * Goes to updated blog
	 * @param id int ID of the blog
	 * @return rendered page with updated blog
	 */
	@Security.Authenticated(AdminAndBloggerFilter.class)
	public static Result toUpdateBlog(int id) {
		String email = session().get("email");
		Blog currentBlog = Blog.findBlogById(id);
		Logger.error(Play.application().configuration()
				.getString("blogControllerLogger6")
				+ id);
		return ok(updateblog.render(email, currentBlog));
	}

	/**
	 * Method deletes selected blog from the database
	 * @param id int ID of the blog
	 * @return result
	 */
	@Security.Authenticated(AdminAndBloggerFilter.class)
	public static Result deleteBlog(int id) {
		String email = session().get("email");
		Blog.deleteBlog(id);
		Logger.warn(Play.application().configuration()
				.getString("blogControllerLogger7")
				+ id);
		flash("success",
				Play.application().configuration()
						.getString("blogControllerFlash5"));
		List<Blog> blogs = Blog.allBlogs();
		return ok(blog.render(email, blogs));
	}

	/**
	 * saves picture
	 * @return result
	 */
	@Security.Authenticated(UserFilter.class)
	public static String savePicture() {

		String image_url;

		MultipartFormData body = request().body().asMultipartFormData();
		FilePart filePart = body.getFile("image_url");
		if (filePart == null) {
			flash("error",
					Play.application().configuration()
							.getString("blogControllerFlash6"));
			Logger.debug(Play.application().configuration()
					.getString("blogControllerLogger8"));
			return null;
		}

		Logger.debug(Play.application().configuration()
				.getString("blogController9")
				+ filePart.toString()
				+ "\n"
				+ filePart.getContentType()
				+ "\n"
				+ filePart.getKey());

		File image = filePart.getFile();
		String extension = filePart.getFilename().substring(
				filePart.getFilename().lastIndexOf('.'));
		extension.trim();

		if (!extension.equalsIgnoreCase(".jpeg")
				&& !extension.equalsIgnoreCase(".jpg")
				&& !extension.equalsIgnoreCase(".png")) {
			Logger.error(Play.application().configuration()
					.getString("blogControllerLogger10"));
			flash("error",
					Play.application().configuration()
							.getString("blogControllerFlash7"));
			return null;
		}
		double megabiteSyze = (double) ((image.length() / 1024) / 1024);

		if (megabiteSyze > 2) {
			Logger.debug(Play.application().configuration()
					.getString("blogControllerLogger11"));
			flash("error",
					Play.application().configuration()
							.getString("blogControllerFlash8"));
			return null;
		}

		try {
			File profile = new File("./public/images/Productimages/"
					+ UUID.randomUUID().toString() + extension);

			Logger.debug(profile.getPath());
			image_url = "images" + File.separator + "Productimages/"
					+ profile.getName();

			Files.move(image, profile);

		} catch (IOException e) {
			Logger.error(Play.application().configuration()
					.getString("blogControllerLogger12"));
			Logger.debug(e.getMessage());
			return null;
		}
		return image_url;

	}

}
