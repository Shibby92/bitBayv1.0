package models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static play.test.Helpers.*;

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
		Product.create("name", 1, 1, null, 1, 2.1, "description", "url");
		Product p = Product.find(1);
		
		assertNotNull(p);
		assertEquals(p.name, "name");
	}

	

}
