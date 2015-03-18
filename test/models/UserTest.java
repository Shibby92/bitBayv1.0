package models;

import static org.junit.Assert.*;
import static play.test.Helpers.*;
import helpers.HashHelper;

import org.junit.*;

import play.test.WithApplication;

public class UserTest extends WithApplication {

	//sets new database for every test
	@Before
	public void setUp() {
		fakeApplication(inMemoryDatabase());
	}
	
//	//checks if there is a bugg in registring user in database
	@Test
	public void testCreate() {
		User.create("test@gamil.com", "testpass");
		User u = User.find(3);
		assertNotNull(u);
		assertEquals(u.email, "test@gamil.com");
		assertEquals(HashHelper.checkPassword("testpass", u.password),true);
	}
	@Test
	public void testUpdate(){
		User.create("test@gamil.com", "testpass");
		User u = User.find(3);
		u.email="test@gmail.com";
		u.password=HashHelper.createPassword("test");
		u.update();
		assertEquals(u.email,"test@gmail.com");
		assertEquals(HashHelper.checkPassword("test", u.password),true);
	}
	
	@Test
	public void testDelete(){
		User.create("test@gamil.com", "testpass");
		User.delete(3);
		User u = User.find(3);
		assertNull(u);
	}
	@Test
	public void adminTest(){
		User u = User.find(2);
		u.admin=true;
		assertEquals(u.admin,true);
	}
}
