package models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static play.test.Helpers.*;

import java.util.Date;

import org.junit.*;

import play.test.WithApplication;

public class ProductTest extends WithApplication {
	
	@Before
	public void setUp() {
		fakeApplication(inMemoryDatabase());
	}
//	
//	//check if the product is saved in database
//	@Test
//	public void testCreate() {
//		Product.create("name", 1, 1, null, 1, 2.1, "description", "url");
//		Product p = Product.find(1);
//		
//		assertNotNull(p);
//		assertEquals(p.name, "name");
//	}
//
//	
//
	@Test
	public void updateTest(){
		User u=new User("test@gmail.com", "test123");
		Product.create("ProductName",1,u,new Date(1,1,2015),1,20.0,"Test cool","testURL");
		Product test= Product.find(2);
		test.name="Test2";
		test.category_id= 2;
		test.owner = u;
		test.created = new Date(2,1,2015);
		test.quantity = 4;
		test.price = 88.0;
		test.description = "Shitty";
		test.image_url = "updatedURL";
		test.update();
		assertNotNull(test);
		assertEquals(test.name,"Test2");
		assertEquals(test.category_id, 2);
		assertEquals(test.owner, u);
		assertEquals(test.created, new Date(2,1,2015));
		assertEquals(test.quantity, 4);
		assertEquals(test.price, 88.0);
		assertEquals(test.description, "Shitty");
		assertEquals(test.image_url, "updatedURL");
		
	}
}
