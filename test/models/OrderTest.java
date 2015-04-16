package models;

import static org.junit.Assert.*;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;

import org.junit.Before;
import org.junit.Test;


public class OrderTest {
	@Before
	public void setUp() {
		fakeApplication(inMemoryDatabase());
	}
	
	@Test
	public void createOrder(){
	new Orders (new Product("name", 24.0, "description", 1)).save();
	Orders o = Orders.find(1);
	assertNotNull(o);
	assertEquals(o.productList.get(0).name,"name");
	assertEquals(o.productList.get(0).price,24.0,Math.abs(24.0-o.productList.get(0).price));
	assertEquals(o.productList.get(0).description,"description");
	assertEquals(o.productList.get(0).quantity,1);
	o.buyer= new User("buyer@mail.com","123123");
	o.notification=true;
	o.price=999.0;
	o.token="TOKEN";
	o.seller= new User("seller@mail.com","123123");
	o.pQ.add(new ProductQuantity(1,1));
	assertEquals(o.buyer.email,"buyer@mail.com");
	assertEquals(o.buyer.password,"123123");
	assertEquals(o.notification,true);
	assertEquals(o.price,999.0,Math.abs(999.0-o.price));
	assertEquals(o.token,"TOKEN");
	assertEquals(o.seller.email,"seller@mail.com");
	assertEquals(o.seller.password,"123123");
	assertEquals(o.pQ.get(0).productId,1);
	assertEquals(o.pQ.get(0).quantity,1);
	
	}
}
