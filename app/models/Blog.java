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

// TODO: Auto-generated Javadoc
/**
 * The Class Blog.
 */
@Entity
public class Blog extends Model {
	
	/** The id. */
	@Id
	public int id;
	
	/** The title. */
	@Required
	public String title;
	
	/** The content. */
	@Required
	@Column(columnDefinition = "TEXT")
	public String content;
	
	/** The blog image path. */
	@Required
	public String blogImagePath;
	
	/** The date. */
	public String date;
	
	/** The user id. */
	public int userId;
	
	/** The posted by. */
	public String postedBy;

	/** The find. */
	public static Finder<Integer, Blog> find = new Finder<Integer, Blog>(Integer.class, Blog.class);

	
	/**
	 * Instantiates a new blog.
	 *
	 * @param title String the title
	 * @param content String the content
	 * @param blogImagePath String the blog image path
	 * @param userId int the user id
	 * @param postedBy String email of the user who posted the blog
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
	 * Creates the blog.
	 *
	 * @param title String the title
	 * @param content String the content
	 * @param blogImagePath String the blog image path
	 * @param userId int the user id
	 * @param postedBy String email of the user who posted the blog
	 */
	public static void createBlog(String title, String content, String blogImagePath, int userId, String postedBy) {
		new Blog(title, content, blogImagePath, userId, postedBy).save();
	}
	
	/**
	 * All blogs.
	 *
	 * @return the list with all blogs
	 */
	public static List<Blog> allBlogs() {
		List<Blog> blogs = find.all();
		Collections.reverse(blogs);
		return blogs;
	}
	

	/**
	 * Find blog by his id.
	 *
	 * @param id int the id
	 * @return the blog
	 */
	public static Blog findBlogById(int id) {
		return find.byId(id);
	}

	/**
	 * Delete blog by his id.
	 *
	 * @param id int the id
	 */
	public static void deleteBlog(int id) {
		find.byId(id).delete();
	}
	

	/**
	 * Makes the date of publishing some blog.
	 *
	 * @return date
	 */
	public static String date() {
		Date date = Calendar.getInstance().getTime();
		DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd hh:mm");
		return formatter.format(date);
	}

}
