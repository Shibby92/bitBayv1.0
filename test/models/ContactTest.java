package models;
import static org.junit.Assert.*;
import static play.test.Helpers.*;

import org.junit.*;

import controllers.UserLoginApplication.Contact;
import play.test.WithApplication;

public class ContactTest extends WithApplication {

	//sets new database for every test
		@Before
		public void setUp() {
			fakeApplication(inMemoryDatabase());
		}
	@Test
	public void isEmpty(){
		Contact test= new Contact("test@gmail.com","message");
		assertNotNull(test.email);
		assertNotNull(test.message);
		assertEquals(test.email.contains("@"), true);
		
	}
}
