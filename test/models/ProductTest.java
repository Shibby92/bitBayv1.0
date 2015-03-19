package models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static play.test.Helpers.*;
import models.*;
import static org.fest.assertions.Assertions.*;

import org.junit.*;

import play.test.WithApplication;

public class ProductTest extends WithApplication {
	
	@Before
	public void setUp() {
		fakeApplication(inMemoryDatabase());
	}
	
	//check if the product is saved in database
	/*@Test
	public void testCreate() {
		
		Product.create("name", 1, "owner", null, 1, 2.1, "description", "url");
		Product p = Product.find(1);
		
		assertNotNull(p);
		assertEquals(p.name, "name");
	}*/
	
	@Test
	public void testDelete(){
		running(fakeApplication(), new Runnable() {
	           public void run() {
		Product.create("name", 24, "description", 1);
		Product.delete(1);
		Product p = Product.find(1);
		assertNotNull(p);
	           
	}
		});
	}
		

	

}
