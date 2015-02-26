import org.junit.*;

import play.mvc.*;
import play.test.*;
import play.libs.F.*;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;

import static org.fluentlenium.core.filter.FilterConstructor.*;

public class IntegrationTest {

    /**
     * add your integration test here
     * in this example we just check if the welcome page is being shown
     */
    @Test
    public void test() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {
                browser.goTo("http://localhost:3333");
                assertThat(browser.pageSource()).contains("Welcome to bitBay!");
            }
        });
    }
    
    @Test
    public void testRegitration() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {
                browser.goTo("http://localhost:3333/#toregister");
                browser.fill("#usernamesignup").with("test");
                browser.fill("#emailsignup").with("test@bitcamp.ba");
                browser.fill("#passwordsignup").with("testpassword");
                browser.submit("#registrationForm");
                assertThat(browser.pageSource()).contains("Registration successful!");
            }
        });
    }
  

}
