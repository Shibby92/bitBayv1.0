
import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.HTMLUNIT;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;
import models.User;

import org.junit.Before;
import org.junit.Test;

import play.libs.F.Callback;
import play.test.TestBrowser;



/**
*
* Simple (JUnit) tests that can call all parts of a play app.
* If you are interested in mocking a whole application, see the wiki for more details.
*
*/
public class ApplicationTest {
	
	@Before
	public void setUp() {
		fakeApplication(inMemoryDatabase());
	}	
    
    @Test
	public void test() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())),
				HTMLUNIT, new Callback<TestBrowser>() {
					public void invoke(TestBrowser browser) {
						browser.goTo("http://localhost:3333");
						assertThat(browser.pageSource()).contains("BitBay");
						assertThat(browser.pageSource()).contains("Login");
						assertThat(browser.pageSource()).contains("Registration");
						

					}
				});
	}
    
    @Test
	public void testRegistration() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())),
				HTMLUNIT, new Callback<TestBrowser>() {
					public void invoke(TestBrowser browser) {
						browser.goTo("http://localhost:3333/toregister");
						browser.fill("#email").with("test@test.ba");
						browser.fill("#password").with("testPass");
						browser.fill("#confirmPassword").with("testPass");
						browser.submit("#Register");
						User user = User.find("test@test.ba");
						user.verification = true;
						assertThat(browser.pageSource()).contains("BitBay");
						assertThat(browser.pageSource()).contains("mail");						
					}
				});
	}
    
    @Test
	public void testLogin() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())),
				HTMLUNIT, new Callback<TestBrowser>() {
					public void invoke(TestBrowser browser) {
						browser.goTo("http://localhost:3333/logintest");
						browser.fill("#email").with("admin@gmail.com");
						browser.fill("#password").with("admin");
						browser.submit("#nameForm");
						assertThat(browser.pageSource()).contains("BitBay");
						assertThat(browser.pageSource()).contains("Logout");
						
					}
				});
	}
    
    @Test
	public void testLogout() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())),
				HTMLUNIT, new Callback<TestBrowser>() {
					public void invoke(TestBrowser browser) {
						browser.goTo("http://localhost:3333/logintest");
						browser.fill("#email").with("admin@gmail.com");
						browser.fill("#password").with("admin");
						browser.submit("#nameForm");
						browser.goTo("http://localhost:3333/logout");
						assertThat(browser.pageSource()).contains("BitBay");
						assertThat(browser.pageSource()).contains("Login");
						assertThat(browser.pageSource()).contains("Registration");
						
					}
				});
	}
    
    @Test
	public void testAdminPanelIfIsAdmin(){
		running(testServer(3333, fakeApplication(inMemoryDatabase())),
				HTMLUNIT, new Callback<TestBrowser>() {
					public void invoke(TestBrowser browser) {
						browser.goTo("http://localhost:3333/logintest");
						browser.fill("#email").with("admin@gmail.com");
						browser.fill("#password").with("admin");
						browser.submit("#nameForm");
						browser.goTo("http://localhost:3333/profile");
						assertThat(browser.pageSource()).contains("List of users");
						assertThat(browser.pageSource()).contains("Categories");
						assertThat(browser.pageSource()).contains("Users");
					}
		});
						
	}
    @Test
	public void testAdminPanelIfIsNotAdmin(){
		running(testServer(3333, fakeApplication(inMemoryDatabase())),
				HTMLUNIT, new Callback<TestBrowser>() {
					public void invoke(TestBrowser browser) {
						browser.goTo("http://localhost:3333/logintest");
						browser.fill("#email").with("emina.muratovic@bitcamp.ba");
						browser.fill("#password").with("emina");
						browser.submit("#nameForm");
						browser.goTo("http://localhost:3333/profile");
						assertThat(browser.pageSource().contains("Messages"));
					}
		});
						
	}
    

}
