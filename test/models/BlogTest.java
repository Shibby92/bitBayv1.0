package models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;

import org.junit.Before;
import org.junit.Test;

import play.test.WithApplication;

public class BlogTest extends WithApplication {
	
	@Before
	public void setUp() {
		fakeApplication(inMemoryDatabase());
	}
	
	@Test
	public void createBlog() {
		Blog.createBlog("title", "content", "noimg.jpg");
		Blog blog = Blog.findBlogById(2);
		assertNotNull(blog);
		assertEquals(blog.title, "title");
		assertEquals(blog.content, "content");
		assertEquals(blog.blogImagePath, "noimg.jpg");
	}
	
	@Test
	public void deleteBlog() {
		Blog.createBlog("title", "content", "noimg.jpg");
		Blog blog = Blog.findBlogById(2);
		assertNotNull(blog);
		Blog.deleteBlog(2);
		blog = Blog.findBlogById(2);
		assertNull(blog);
	}
	
	@Test
	public void findNonExisting() {
		Blog blog = Blog.findBlogById(1000);
		assertNull(blog);
	}
	
	@Test
	public void editBlog() {
		Blog.createBlog("title", "content", "noimg.jpg");
		Blog blog = Blog.findBlogById(2);
		assertNotNull(blog);
		assertEquals(blog.title, "title");
		assertEquals(blog.content, "content");
		assertEquals(blog.blogImagePath, "noimg.jpg");
		blog.title = "title2";
		blog.content = "content2";
		blog.blogImagePath = "noimg2.jpg";
		blog.update();
		assertEquals(blog.title, "title2");
		assertEquals(blog.content, "content2");
		assertEquals(blog.blogImagePath, "noimg2.jpg");
	}

}
