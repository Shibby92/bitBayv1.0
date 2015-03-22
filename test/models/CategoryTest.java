package models;


import static org.junit.Assert.*;
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

	//adding new category
	@Test
	public void addCategoryTest(){
		Category.create("Test");
		Category test= Category.find(9);
		assertNotNull(test);
		assertEquals(test.name,"Test");
		
	}
	
	//updating category
	@Test
	public void updateTest(){
		Category.create("Test");
		Category test= Category.find(9);
		test.name="Test2";
		test.update();
		assertNotNull(test);
		assertEquals(test.name,"Test2");
	}
	
	@Test
	public void deleteTest(){
		Category.create("Test");
		Category testic = Category.find(9);
		assertNotNull(testic);
		Category.delete(9);
		Category test = Category.find(9);
		assertNull(test);
	}
	
}

