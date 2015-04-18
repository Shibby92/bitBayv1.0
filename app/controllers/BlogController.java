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
		return TODO;
	}
	
	public static Result updateBlog(int id) {
		return TODO;
	}
	
	public static Result toUpdateBlog(int id) {
		return TODO;
	}
	
	public static Result deleteBlog(int id) {
		return TODO;
	}

}
