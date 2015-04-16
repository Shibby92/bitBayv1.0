package controllers;

import helpers.HashHelper;
import models.*;
import org.junit.*;
import play.test.*;
import play.libs.F.*;
import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;

public class SearchTest {

	/**
	 * Test for Search we fill search form with some products
	 */
	@Test
	public void testSearch() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())),
				HTMLUNIT, new Callback<TestBrowser>() {
					public void invoke(TestBrowser browser) {
						browser.goTo("http://localhost:3333");
						browser.fill("#q").with("Galaxy s6");
						assertThat(browser.pageSource())
								.contains(
										"Detailed: item info, Product: Identifiers, Brand: Samsung, Carrier: Unlocked, Family Line: Samsung Galaxy S6, Type: Smartphone, Digital Camera: Yes, Email Access: Yes, Battery Capacity: 2550 mAh, Display Technology: Quad HD Super AMOLED ");
					}
				});
	}

	/**
	 * Test for search with filter, this method search by filterprice
	 */
	@Test
	public void testFilterPrice() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())),
				HTMLUNIT, new Callback<TestBrowser>() {
					public void invoke(TestBrowser browser) {
						browser.goTo("http://localhost:3333");
						browser.fill("#min").with("1");
						browser.fill("#max").with("2001");
						browser.submit("#priceSubmit");
						assertThat(browser.pageSource())
								.doesNotContain(
										"Detailed: item info, Product: Identifiers, Brand: Samsung, Carrier: Unlocked, Family Line: Samsung Galaxy S6, Type: Smartphone, Digital Camera: Yes, Email Access: Yes, Battery Capacity: 2550 mAh, Display Technology: Quad HD Super AMOLED ");
						assertThat(browser.pageSource()).contains("2000$");
					}
				});
	}

	@Test
    public void testFilterCategory() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {
                browser.goTo("http://localhost:3333");
                browser.click("#category");
                browser.submit("#categorySubmit");
            	assertThat(browser.pageSource())
				.doesNotContain(
						"Detailed: item info, Product: Identifiers, Brand: Samsung, Carrier: Unlocked, Family Line: Samsung Galaxy S6, Type: Smartphone, Digital Camera: Yes, Email Access: Yes, Battery Capacity: 2550 mAh, Display Technology: Quad HD Super AMOLED ");
		assertThat(browser.pageSource()).doesNotContain("2000$");
		 assertThat(browser.pageSource()).contains("Mobile Phone"); 
		
            }
        });
    }
	
	
}
