package controllers;

import java.util.List;

import models.Blog;
import models.FAQ;
import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;
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
		return TODO;
	}
	
	public static Result toAddNewBlog() {
		Logger.info("Opened page for adding new Blog");
		String email = session().get("email");
		return ok(newblog.render(email));
	}
	
	public static Result updateBlog(int id) {
		return TODO;
	}
	
	public static Result toUpdateBlog(int id) {
		return TODO;
	}
	
	public static Result deleteBlog(int id) {
		String email = session().get("email");
		Blog.deleteBlog(id);
		Logger.warn("Blog with id: " + id + " has been deleted");
		flash("success", "Blog deleted!");
		List<Blog> blogs = Blog.allBlogs();
		return ok(blog.render(email, blogs));
	}

}
