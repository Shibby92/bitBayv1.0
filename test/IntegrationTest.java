/*import models.User;

import org.junit.*;

import play.mvc.*;
import play.test.*;
import play.libs.F.*;
import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;
import static org.fluentlenium.core.filter.FilterConstructor.*;
import static org.junit.Assert.assertEquals;

public class IntegrationTest {

    //test registration
    @Test
    public void testRegitration() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {
                browser.goTo("http://localhost:3333/nouser#toregister");
                browser.fill("#usernamesignup").with("testtest");
                browser.fill("#passwordsignup").with("testpassword");
                browser.submit("#registrationForm");
                User u = User.find(1);
                assertEquals(u.username, "test");

            }
        });
    }
    
    //test login
    @Test 
    public void testLogin() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {
                browser.goTo("http://localhost:3333/nouser");
                browser.fill("#username").with("testteest");
                browser.fill("#password").with("testpassword");
                browser.submit("#loginForm");
                //assertequals to something
               
            }
        });
    }

}
*/