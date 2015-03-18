package models;

import static org.junit.Assert.*;
import static play.test.Helpers.*;


import org.junit.*;

import play.test.WithApplication;

public class CategoryTest extends WithApplication {

	//sets new database for every test
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
	
}