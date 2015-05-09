package models;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;

import org.junit.Before;
import org.junit.Test;

import play.test.WithApplication;
import controllers.UserLoginApplication.Contact;

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
