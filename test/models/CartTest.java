package models;

import static org.junit.Assert.*;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;

import org.junit.Before;
import org.junit.Test;

public class CartTest {

	@Before
	public void setUp() {
		fakeApplication(inMemoryDatabase());
	}

	@Test
	public void createCart() {
		new Cart(1, "user@mail.com");
		Cart c = Cart.getCart(1);
		assertNotNull(c);
		assertEquals(c.checkout, 0, Math.abs(0));
		assertEquals(c.size, 0);
		assertEquals(c.userid, 1);
		assertEquals(c.userMail, "user@mail.com");
		Cart.addProduct(new Product("name", 24.0, "description", 1), c);
		assertEquals(c.productList.get(0).name, "name");
		assertEquals(c.productList.get(0).price, 24.0, Math.abs(0));
		assertEquals(c.productList.get(0).description, "description");
		assertEquals(c.productList.get(0).quantity, 1);
		assertEquals(c.checkout, 24.0, Math.abs(0));
		assertEquals(c.size, 1);
	}

	@Test
	public void deleteCart() {
		Cart c = Cart.getCart(1);
		Cart.clear(1);
		assertNull(c);
		for (Product p : c.productList) {
			assertNull(p);
		}
		assertEquals(c.checkout,0,Math.abs(0));
		assertEquals(c.size, 0);
		Product p=new Product("name", 24.0, "description", 1);
		Cart.addProduct(p, c);
		Cart.removeProductFromCart(p.id);
		assertNotNull(c);
		for (Product product: c.productList) {
			assertNull(product);
		}
		assertEquals(c.checkout,0,Math.abs(0));
		assertEquals(c.size, 0);
		

	}
}
