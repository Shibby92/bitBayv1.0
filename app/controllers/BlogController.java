package controllers;

import helpers.AdminAndBloggerFilter;
import helpers.AdminFilter;
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
	 * @return
	 */
	public static Result blogsPage() {
		Logger.info("Opened Blogs page");
		String email = session().get("email");
		List<Blog> blogs = Blog.allBlogs();
		return ok(blog.render(email, blogs));
	}
	
	public static Result addNewBlog() {
		DynamicForm form = Form.form().bindFromRequest();
		try {
			String title = form.get("title");
			String content = form.get("content");
			String imgPath = savePicture();
			User user = Session.getCurrentUser(ctx());
			Blog.createBlog(title, content, imgPath, user.id);
			Logger.info("New Blog added with title: " + title);
			flash("success", "New blog added!");
			return redirect("/blog");
		} catch (Exception e) {
			Logger.error("Error in addNewBlog");
			flash("error", "There has been an error in adding Blog!");
			return redirect("/blog");
		}
	}
	
	/**
	 * Method renders the page in which new blog is created
	 * @return
	 */
	@Security.Authenticated(AdminAndBloggerFilter.class)
	public static Result toAddNewBlog() {
		Logger.info("Opened page for adding new Blog");
		String email = session().get("email");
		return ok(newblog.render(email));
	}
	
	public static Result updateBlog(int id) {
		DynamicForm form = Form.form().bindFromRequest();
		Blog currentBlog = Blog.findBlogById(id);
		
		try {
			currentBlog.title = form.get("title");
			currentBlog.content = form.get("content");
			String imgPath = savePicture();
			currentBlog.blogImagePath = imgPath;
			currentBlog.update();
			flash("success", "Successful update!");
			
			return redirect("/blog");
		} catch (Exception e) {
			Logger.error("Error in updating Blog");
			flash("error", "There has been an error in updating Blog!");
			return redirect("/blog");
		}

	}
	
	@Security.Authenticated(AdminAndBloggerFilter.class)
	public static Result toUpdateBlog(int id) {
		String email = session().get("email");
		Blog currentBlog = Blog.findBlogById(id);
		return ok(updateblog.render(email, currentBlog));
	}
	
	/**
	 * Method deletes selected blog from the database
	 * @param id
	 * @return
	 */
	@Security.Authenticated(AdminAndBloggerFilter.class)
	public static Result deleteBlog(int id) {
		String email = session().get("email");
		Blog.deleteBlog(id);
		Logger.warn("Blog with id: " + id + " has been deleted");
		flash("success", "Blog deleted!");
		List<Blog> blogs = Blog.allBlogs();
		return ok(blog.render(email, blogs));
	}
	
	/**
	 * saves picture on given product
	 * @param id int id of the product
	 * @return result 
	 */
	@Security.Authenticated(UserFilter.class)
	public static String savePicture() {
		
		String image_url;
		
		MultipartFormData body = request().body().asMultipartFormData();
		FilePart filePart = body.getFile("image_url");
		if (filePart == null) {
			flash("error", "You need to upload image!");
			Logger.debug("File part is null");
			return null;
		}

		Logger.debug("Filepart: " + filePart.toString());
		Logger.debug("Content type: " + filePart.getContentType());
		Logger.debug("Key: " + filePart.getKey());
		
		File image = filePart.getFile();
		String extension = filePart.getFilename().substring(
				filePart.getFilename().lastIndexOf('.'));
		extension.trim();

		if (!extension.equalsIgnoreCase(".jpeg")
				&& !extension.equalsIgnoreCase(".jpg")
				&& !extension.equalsIgnoreCase(".png")) {
					Logger.error("Image type not valid");
					flash("error", "Image type not valid");
					return null;
		}
		double megabiteSyze = (double) ((image.length() / 1024) / 1024);
		
		if (megabiteSyze > 2) {
			Logger.debug("Image size not valid ");
			flash("error", "Image size not valid");
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
