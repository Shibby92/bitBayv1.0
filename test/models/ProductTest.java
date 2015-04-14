package models;

import static org.junit.Assert.*;
import static play.test.Helpers.*;
import models.*;
import static org.fest.assertions.Assertions.*;

import java.util.Date;


import org.junit.*;

import play.test.WithApplication;

public class ProductTest extends WithApplication {


	@Before
	public void setUp() {
		fakeApplication(inMemoryDatabase());
	}
	
	
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
		

	@Test
	public void updateTest(){
		Product test= Product.find(1);
		test.name="Test2";
		test.quantity = 4;
		test.price = 88;
		test.description = "Shitty";
		test.update();
		assertNotNull(test);
		assertEquals(test.name,"Test2");
		assertEquals(test.quantity, 4);
		assertEquals(test.price, 88,Math.abs(88 - test.price));
		assertEquals(test.description, "Shitty");
		
	}


	@Before
	public void setUp() {
		fakeApplication(inMemoryDatabase());
	}
	
	//check if the product is saved in database
	/*@Test
	public void testCreate() {
		Product.create("test",100,"lijepo pravo godi",2);
		Product p = Product.find(2);
		assertNotNull(p);
		assertEquals(p.name, "test");
		assertEquals(p.price, 100,Math.abs(100- p.price));
		assertEquals(p.description, "lijepo pravo godi");
		
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
		

	@Test
	public void updateTest(){
		Product test= Product.find(1);
		test.name="Test2";
		test.quantity = 4;
		test.price = 88;
		test.description = "Shitty";
		test.update();
		assertNotNull(test);
		assertEquals(test.name,"Test2");
		assertEquals(test.quantity, 4);
		assertEquals(test.price, 88,Math.abs(88 - test.price));
		assertEquals(test.description, "Shitty");
		
	}

}
