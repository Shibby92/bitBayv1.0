package controllers;

import models.*;
import helpers.*;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.db.ebean.Model.Finder;
import play.mvc.*;
import views.html.*;

public class CartController extends Controller {
	
	/** The find. */
	static Finder<Integer, Product> find = new Finder<Integer, Product>(
			Integer.class, Product.class);
	

	/**
	 * Opens a page with all products from one cart.
	 *
	 * @param id int id of the cart
	 * @return the result
	 */
	@Security.Authenticated(UserFilter.class)
	public static Result cartPage(int id) {
		String email = session().get("email");
		return ok(cartpage.render(email, Cart.getCartbyUserId(id), FAQ.all()));
	}
	

	/**
	 * Gets info from one product and adds it in cart.
	 *
	 * @param id int id of the product
	 * @return the result
	 */
	@Security.Authenticated(UserFilter.class)
	public static Result productToCart(int id) {
		int orderedTotalQta = 0;
		String email = session().get("email");
		Product p = find.byId(id);
		if (session().isEmpty()) {
			flash("guest", "Please log in to buy stuff!");
			return redirect("/login");
		}
		int userid = User.find.where().eq("email", session().get("email"))
				.findUnique().id;
		Cart cart = Cart.getCartbyUserEmail(email);

		Logger.info(String.valueOf(userid));
		DynamicForm form = Form.form().bindFromRequest();
		int orderedQuantity = Integer.valueOf(form.get("orderedQuantity"));
		if (orderedQuantity < 1) {
			flash("minQty", "Ordered quantity must be at least 1!");
			p.setOrderedQuantity(p.getOrderedQuantity());

			return redirect("/itempage/" + id);
		}
		orderedTotalQta = orderedQuantity + p.getOrderedQuantity();
		if (orderedTotalQta > p.quantity) {
			flash("excess",
					"You cannot order quantity that exceeds one available on stock!");
			p.setOrderedQuantity(p.getOrderedQuantity());

			return redirect("/itempage/" + id);
		} else {
			p.setOrderedQuantity(orderedTotalQta);
			p.amount = p.price * p.getOrderedQuantity();
			Logger.info(String.valueOf("Ordered: " + orderedQuantity));
			p.update();
			p.save();
			if (cart.productList.contains(p)) {
				Cart.addQuantity(p, cart, orderedQuantity);
				return redirect("/cartpage/" + userid);

			} else {
				Cart.addProduct(p, cart);
				Logger.info(String.valueOf("Naruceno posle: "
						+ p.orderedQuantity));
				return redirect("/cartpage/" + userid);
			}
		}
	}
	
	/**
	 * Change ordered quantity.
	 *
	 * @param id int the id of the product
	 * @return the result
	 */
	public static Result changeOrderedQty(int id) {
		Product p = find.byId(id);
		if (session().isEmpty()) {
			flash("guest", "Please log in to buy stuff!");
			return redirect("/login");
		}
		int userid = User.find.where().eq("email", session().get("email"))
				.findUnique().id;
		DynamicForm form = Form.form().bindFromRequest();
		int orderedQuantity = Integer
				.valueOf(form.get("changeOrderedQuantity"));
		if (orderedQuantity < 1) {
			flash("minQty", "Ordered quantity must be at least 1!");
			p.setOrderedQuantity(p.getOrderedQuantity());
			return redirect("/cartpage/" + userid);
		}
		p.setOrderedQuantity(orderedQuantity);
		p.amount = p.price * p.getOrderedQuantity();
		Logger.info(String.valueOf("Ordered: " + orderedQuantity));
		p.update();
		p.save();
		return redirect("/cartpage/" + userid);

	}
	
	/**
	 * Adds the quantity to product.
	 *
	 * @param pId int the id of the product
	 * @return the result
	 */
	public static Result addQty(int pId){
		String email = session().get("email");
		Cart cart = Cart.getCartbyUserEmail(email);
		Product p = find.byId(pId);
		if (session().isEmpty()) {
			flash("guest", "Please log in to buy stuff!");
			return redirect("/login");
		}
		int userid = User.find.where().eq("email", session().get("email"))
				.findUnique().id;
		int totalOrderedQty=p.getOrderedQuantity()+1;
		if (totalOrderedQty > p.quantity) {
			flash("excess",
					"You cannot order quantity that exceeds one available on stock!");
			p.setOrderedQuantity(p.getOrderedQuantity());

			return redirect("/cartpage/" + userid);
		}
		Logger.info("User " + email + " ordered product id: " + pId + " with quantity " + totalOrderedQty);
		cart.checkout=cart.checkout-p.price*p.getOrderedQuantity();
		cart.size=cart.size-p.orderedQuantity;
		p.setOrderedQuantity(totalOrderedQty);
		cart.size=cart.size+p.getOrderedQuantity();
		cart.checkout=cart.checkout+p.price*p.getOrderedQuantity();
		cart.update();
		return redirect("/cartpage/" + userid);
	}
	
	
	/**
	 * Subtract quantity.
	 *
	 * @param pId int the id of the product
	 * @return the result
	 */
	public static Result subtractQty(int pId){
		String email = session().get("email");
		Cart cart = Cart.getCartbyUserEmail(email);
		Product p = find.byId(pId);
		if (session().isEmpty()) {
			flash("guest", "Please log in to buy stuff!");
			return redirect("/login");
		}
		int userid = User.find.where().eq("email", session().get("email"))
				.findUnique().id;
		int totalOrderedQty=p.getOrderedQuantity()-1;
		if (totalOrderedQty < 1) {
			flash("minQty",
					"Ordered quantity must be at least 1!");
			p.setOrderedQuantity(p.getOrderedQuantity());
			return redirect("/cartpage/" + userid);
		}
		cart.checkout=cart.checkout-p.price*p.getOrderedQuantity();
		cart.size=cart.size-p.orderedQuantity;
		p.setOrderedQuantity(totalOrderedQty);
		cart.size=cart.size+p.getOrderedQuantity();
		cart.checkout=cart.checkout+p.price*p.getOrderedQuantity();
		cart.update();
		return redirect("/cartpage/" + userid);
	}
	
	
	/**
	 * Change quantity of the product.
	 *
	 * @param pId int the id of the product
	 * @param cId int the id of the cart
	 * @return the result
	 */
	public static Result changeQty(int pId, int cId){
		Product p = find.byId(pId);
		if (session().isEmpty()) {
			flash("guest", "Please log in to buy stuff!");
			return redirect("/login");
		}
		int userid = User.find.where().eq("email", session().get("email"))
				.findUnique().id;
		int totalOrderedQty=p.getOrderedQuantity()+1;
		p.setOrderedQuantity(totalOrderedQty);
		return redirect("/cartpage/" + userid);
	}
	
	/**
	 * Change shipping address.
	 *
	 * @param id int the id of the cart
	 * @return the result
	 */
	public static Result changeShippingAddress (int id){
		DynamicForm form= Form.form().bindFromRequest();
		String shipA=form.get("shippingAddress");
		Cart c=Cart.find(id);
		c.shippingAddress=shipA;
		c.update();
		Logger.info("User " + session().get("email") + " has changed his shipping address to " + shipA); 
		flash("shipSuccess", "Shipping address successfully changed!");
		return ok(cartpage.render(session().get("email"),Cart.find(id),FAQ.all()));
	}

	/**
	 * Deletes a product from the cart.
	 *
	 * @param id int id of the product
	 * @return result
	 */
	@Security.Authenticated(UserFilter.class)
	public static Result deleteProductFromCart(int id) {
		String email = session().get("email");
		Product productDel = find.byId(id);
		Cart cart = productDel.cart;

		cart.productList.remove(productDel);
		cart.update();
		cart.save();
		if (cart.productList.size() < 1) {
			cart.checkout = 0;
			cart.size = 0;
		} else {
			cart.checkout = cart.checkout - productDel.price
					* productDel.getOrderedQuantity();
			if (cart.checkout < 0)
				cart.checkout = 0;
			cart.size = cart.size - productDel.getOrderedQuantity();
		}
		cart.update();
		cart.save();
		productDel.cart = null;
		productDel.setOrderedQuantity(0);
		productDel.update();
		productDel.save();

		return ok(cartpage.render(email, cart, FAQ.all()));

	}

}
