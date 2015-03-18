package models;

import static org.junit.Assert.*;
import static play.test.Helpers.*;

import java.util.Date;

import org.junit.*;

import play.test.WithApplication;

public class ProductTest extends WithApplication {
	

	@Before
	public void setUp() {
		fakeApplication(inMemoryDatabase());
	}
	
	//check if the product is saved in database
	@Test
	public void testCreate() {
		Product.create("test",100,"lijepo pravo godi",2);
		Product p = Product.find(2);
		assertNotNull(p);
		assertEquals(p.name, "test");
		assertEquals(p.price, 100,Math.abs(100- p.price));
		assertEquals(p.description, "lijepo pravo godi");
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
	
	@Test
	public void deleteTest(){
		Product test = Product.find(1);
		test.delete(1);
		Product testic = Product.find(1);
		assertNull(testic);
	}

}
