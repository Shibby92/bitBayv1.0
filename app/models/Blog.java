package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
/**
 * Class Blog
 * @author nermingraca
 *
 */
@Entity
public class Blog extends Model {
	
	@Id
	public int id;
	
	@Required
	public String title;
	
	@Required
	public String content;
	
	@Required
	public String blogImagePath;

	/**
	 * Constructor of object Blog with three parameters
	 * @param title
	 * @param content
	 * @param blogImagePath
	 */
	public Blog(String title, String content, String blogImagePath) {
		
		this.title = title;
		this.content = content;
		this.blogImagePath = blogImagePath;
	}
	
	/**
	 * Constructor of object Blog with two parameters
	 * @param title
	 * @param content
	 */
	public Blog(String title, String content) {
		
		this.title = title;
		this.content = content;
	}
	
	/**
	 * Method createBlog creates new object of Blog and saves it into the database
	 * @param title
	 * @param content
	 * @param blogImagePath
	 */
	public static void createBlog(String title, String content, String blogImagePath) {
		new Blog(title, content, blogImagePath).save();
	}
	
	/**
	 * Method createBlog creates new object of Blog and saves it into the database
	 * @param title
	 * @param content
	 */
	public static void createBlog(String title, String content) {
		new Blog(title, content).save();
	}
	
	//Finder
	public static Finder<Integer, Blog> find = new Finder<Integer, Blog>(Integer.class, Blog.class);
	
	/**
	 * Method allBlogs return List of all Blog instances in database
	 * @return
	 */
	public static List<Blog> allBlogs() {
		List<Blog> blogs = find.all();
		return blogs;
	}
	
	/**
	 * Method findBlogById finds and returns instance of Blog found by given id
	 * @param id
	 * @return
	 */
	public static Blog findBlogById(int id) {
		return find.byId(id);
	}
	
	/**
	 * Method deleteBlog deletes instance of blog which was found by given id
	 * @param id
	 */
	public static void deleteBlog(int id) {
		find.byId(id).delete();
	}

}
