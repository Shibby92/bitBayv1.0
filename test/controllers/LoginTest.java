package controllers;

import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.fakeGlobal;
import static play.test.Helpers.inMemoryDatabase;
import models.User;

import org.junit.Before;

import play.test.WithApplication;

public class LoginTest extends WithApplication {
	
	@Before
	public void setup() {
		fakeApplication(inMemoryDatabase(), fakeGlobal());
		User.create("test@mail.com", "123456", "test");
	}

}
