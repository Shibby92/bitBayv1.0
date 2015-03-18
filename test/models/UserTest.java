package models;

import static org.junit.Assert.*;
import static play.test.Helpers.*;

import org.junit.*;

import play.test.WithApplication;

public class UserTest extends WithApplication {
//
//	//sets new database for every test
	@Before
	public void setUp() {
		fakeApplication(inMemoryDatabase());
	}
//	
////	//checks if there is a bugg in registring user in database
//	@Test
//	public void testCreate() {
//		User.create("test", "testpass");
//		User u = User.find(1);
//		
//		assertNotNull(u);
//		assertEquals(u.username, "test");
//		assertEquals(u.password, "testpass");
//	}
	
	@Test
	public void testExistsUsername() {
		User u = new User("test@gmail.com", "testtest");
		u.username = "Test";
		u.save();
		User u1 = new User("test1@gmail.com", "testtest");
		u1.username = "Test";
		boolean result = User.existsUsername(u1.username);
		assertTrue(result);
	}
	
	@Test
	public void testAdditionalInfo() {
		User u = new User("test@gmail.com", "testtest");
		
	}
	
}
