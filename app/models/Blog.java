package models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
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
	@Column(columnDefinition = "TEXT")
	public String content;
	
	@Required
	public String blogImagePath;
	
	public String date;
	
	public int userId;
	
	public String postedBy;

	/**
	 * Constructor of object Blog with three parameters
	 * @param title
	 * @param content
	 * @param blogImagePath
	 */
	public Blog(String title, String content, String blogImagePath, int userId, String postedBy) {
		
		this.title = title;
		this.content = content;
		this.blogImagePath = blogImagePath;
		this.date = date();
		this.userId = userId;
		this.postedBy = postedBy;
	}
	
	/**
	 * Method createBlog creates new object of Blog and saves it into the database
	 * @param title
	 * @param content
	 * @param blogImagePath
	 */
	public static void createBlog(String title, String content, String blogImagePath, int userId, String postedBy) {
		new Blog(title, content, blogImagePath, userId, postedBy).save();
	}
	
	//Finder
	public static Finder<Integer, Blog> find = new Finder<Integer, Blog>(Integer.class, Blog.class);
	
	/**
	 * Method allBlogs return List of all Blog instances in database
	 * @return
	 */
	public static List<Blog> allBlogs() {
		List<Blog> blogs = find.all();
		Collections.reverse(blogs);
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
	
	/**
	 * Method returns current date in String value
	 * @return
	 */
	public static String date() {
		Date date = Calendar.getInstance().getTime();
		DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd hh:mm");
		return formatter.format(date);
	}

}
