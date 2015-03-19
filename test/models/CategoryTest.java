package models;

import static org.junit.Assert.assertNotNull;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;

import org.junit.Before;
import org.junit.Test;

import play.test.WithApplication;

public class CategoryTest extends WithApplication{
	@Before
	public void setUp() {
		fakeApplication(inMemoryDatabase());
	}
	@Test
	public void testDelete(){
		running(fakeApplication(), new Runnable() {
	           public void run() {
		Category.create("name");
		Category c = Category.find(1);
		assertNotNull(c);
	           
	}
		});
	}
}
