package controllers;

import helpers.*;
import helpers.Session;

import java.io.*;
import java.util.*;

import models.*;
import play.*;
import play.data.*;
import play.mvc.Http.*;
import play.mvc.*;
import views.html.*;
import play.mvc.Http.MultipartFormData.*;

import com.google.common.io.Files;


// TODO: Auto-generated Javadoc
/**
 * The Class BlogController.
 */
public class BlogController extends Controller {

	
	/**
	 * Blogs page.
	 *
	 * @return the result
	 */
	public static Result blogsPage() {
		
		String email = session().get("email");
		List<Blog> blogs = Blog.allBlogs();
		if(session().get("email") == null)
			Logger.info("Guest has opened blog page");
		else
			Logger.info("User " + session().get("email") + " has opened blog page");
		return ok(blog.render(email, blogs));
	}


	/**
	 * Adds the new blog.
	 *
	 * @return the result
	 */
	public static Result addNewBlog() {
		DynamicForm form = Form.form().bindFromRequest();
		try {
			String title = form.get("title");
			String content = form.get("content");
			String imgPath = savePicture();
			User user = Session.getCurrentUser(ctx());
			Blog.createBlog(title, content, imgPath, user.id, user.username);
			Logger.info("New Blog added with title: " + title);
			flash("success",
					play.i18n.Messages.get
							("blogControllerFlash1"));
			return redirect("/blog");
		} catch (Exception e) {
			Logger.error("Error in adding new blog: " + e.getMessage());
			flash("error",
					play.i18n.Messages.get
							("blogControllerFlash2"));
			return redirect("/blog");
		}
	}

	
	/**
	 * To add new blog.
	 *
	 * @return the result
	 */
	@Security.Authenticated(AdminAndBloggerFilter.class)
	public static Result toAddNewBlog() {
		Logger.info("Opened page for adding new Blog");
		String email = session().get("email");
		return ok(newblog.render(email));
	}

	
	/**
	 * Update blog.
	 *
	 * @param id int the id
	 * @return the result
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
					play.i18n.Messages.get
							("blogControllerFlash3"));

			return redirect("/blog");
		} catch (Exception e) {
			Logger.error("Error in updating Blog" + e.getMessage());
			flash("error",
					play.i18n.Messages.get
							("blogControllerFlash4"));
			return redirect("/blog");
		}

	}

	
	/**
	 * Updates blog.
	 *
	 * @param id int the id of the blog
	 * @return the result
	 */
	@Security.Authenticated(AdminAndBloggerFilter.class)
	public static Result toUpdateBlog(int id) {
		String email = session().get("email");
		Blog currentBlog = Blog.findBlogById(id);
		Logger.error("Updated blog has been shown with id: "
				+ id);
		return ok(updateblog.render(email, currentBlog));
	}


	/**
	 * Deletes blog.
	 *
	 * @param id int the id of the blog
	 * @return the result
	 */
	@Security.Authenticated(AdminAndBloggerFilter.class)
	public static Result deleteBlog(int id) {
		String email = session().get("email");
		Blog.deleteBlog(id);
		Logger.warn("Blog has been deleted with id: "
				+ id);
		flash("success",
				play.i18n.Messages.get
						("blogControllerFlash5"));
		List<Blog> blogs = Blog.allBlogs();
		return ok(blog.render(email, blogs));
	}


	/**
	 * Saves picture.
	 *
	 * @return image url
	 */
	@Security.Authenticated(UserFilter.class)
	public static String savePicture() {

		String image_url;

		MultipartFormData body = request().body().asMultipartFormData();
		FilePart filePart = body.getFile("image_url");
		if (filePart == null) {
			flash("error",
					play.i18n.Messages.get
							("blogControllerFlash6"));
			Logger.debug("File part is null");
			return null;
		}

		Logger.debug("Filepart, content type and key: \n"
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
			Logger.error("Image type not valid");
			flash("error",
					play.i18n.Messages.get
							("blogControllerFlash7"));
			return null;
		}
		double megabiteSyze = (double) ((image.length() / 1024) / 1024);

		if (megabiteSyze > 2) {
			Logger.debug("Image size not valid");
			flash("error",
					play.i18n.Messages.get
							("blogControllerFlash8"));
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
			Logger.error("Failed to move file");
			Logger.debug(e.getMessage());
			return null;
		}
		return image_url;

	}

}
