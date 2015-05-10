package controllers;

import helpers.*;

import java.text.*;
import java.util.*;

import nl.bitwalker.useragentutils.UserAgent;
import models.*;
import models.Notification;
import play.*;
import play.data.*;
import play.mvc.*;

import com.paypal.api.payments.*;
import com.paypal.base.rest.*;

import views.html.*;

public class PayPallController extends Controller {
	
	/**
	 * Tries to buy product with paypall.
	 * 
	 * @return the result
	 */
	@Security.Authenticated(UserFilter.class)
	public static Result purchaseProcessing() {
		String email = session().get("email");
		System.out.println(email + " in purchProc");
		try {
			String total = String.valueOf(Cart.getCartbyUserEmail(session()
					.get("email")).checkout);
			String accessToken = new OAuthTokenCredential(
					Play.application().configuration().getString("payPalPublicKey"),
					Play.application().configuration().getString("payPalSecretKey"))
					.getAccessToken();

			Map<String, String> sdkConfig = new HashMap<String, String>();
			sdkConfig.put("mode", "sandbox");
			APIContext apiContext = new APIContext(accessToken);
			apiContext.setConfigurationMap(sdkConfig);
			Amount amount = new Amount();
			if (Double.parseDouble(total) % 10 == 0) {
				amount.setTotal(total + "0");
			} else {
				amount.setTotal(total);
			}
			amount.setCurrency("USD");
			Transaction transaction = new Transaction();
			String stringCart = cartToString(Cart.getCartbyUserEmail(session()
					.get("email")));
			transaction.setDescription(stringCart);
			transaction.setAmount(amount);
			List<Transaction> transactions = new ArrayList<Transaction>();
			transactions.add(transaction);
			Payer payer = new Payer();
			payer.setPaymentMethod("paypal");
			Payment payment = new Payment();
			payment.setIntent("sale");
			payment.setPayer(payer);
			payment.setTransactions(transactions);
			RedirectUrls redirectUrls = new RedirectUrls();
			
			UserAgent userAgent = UserAgent.parseUserAgentString(Http.Context.current().request().getHeader("User-Agent"));
			String deviceType = userAgent.getOperatingSystem().getDeviceType().toString();
			if (deviceType.equals("MOBILE") || deviceType.equals("TABLET")) {
				redirectUrls.setCancelUrl("http://192.168.0.128:9000/orderfail");
				redirectUrls.setReturnUrl("http://192.168.0.128:9000/orderconfirmmobile/" + email);
			} else {
				redirectUrls.setCancelUrl("http://localhost:9000/orderfail");
				redirectUrls.setReturnUrl("http://localhost:9000/orderconfirm");
			}
			payment.setRedirectUrls(redirectUrls);
			Payment createdPayment = payment.create(apiContext);
			Iterator<Links> itr = createdPayment.getLinks().iterator();
			while (itr.hasNext()) {
				Links link = itr.next();
				if (link.getRel().equals("approval_url")) {
					return redirect(link.getHref());
				}
			}

		} catch (PayPalRESTException e) {

			Logger.warn(e.getMessage());
		}

		return TODO;
	}

	/**
	 * Turns the cart to string and adds it to paypall order so user can see what he had ordered.
	 *
	 * @param cart Cart the cart
	 * @return the string
	 */
	@Security.Authenticated(UserFilter.class)
	public static String cartToString(Cart cart) {

		StringBuilder sb = new StringBuilder();
		sb.append(play.i18n.Messages.get("PaypalControllerCTS1"));
		for (Product product : cart.productList) {
			sb.append(product.name + " (" + product.price + "0 $) x "
					+ product.orderedQuantity + ", ");
		}
		sb.append(play.i18n.Messages.get("PaypalControllerCTS2") + cart.checkout + "0 $");
		if (sb.length() > 127) {
			sb.delete(0, sb.length());
			sb.append(play.i18n.Messages.get("PaypalControllerCTS1"));
			for (Product product : cart.productList) {
				sb.append(product.name + " x " + product.orderedQuantity + ", ");
			}
			sb.append(play.i18n.Messages.get("PaypalControllerCTS3") + cart.checkout + "0 $");
		}
		if (sb.length() > 127) {
			sb.delete(0, sb.length());
			sb.append(play.i18n.Messages.get("PaypalControllerCTS1"));
			sb.append(play.i18n.Messages.get("PaypalControllerCTS3") + cart.checkout + "0 $");
		}
		return sb.toString();

	}

	/**
	 * Order confirm.
	 *
	 * @return the result
	 */
	//@Security.Authenticated(UserFilter.class)
	public static Result orderConfirm() {
		String email = session().get("email");
		System.out.println(email);
		String paymentID = null;
		String payerID = null;
		String token = null;
		Cart cart = Cart.getCartbyUserEmail(email);
		try {
			DynamicForm paypalReturn = Form.form().bindFromRequest();
			paymentID = paypalReturn.get("paymentId");
			payerID = paypalReturn.get("PayerID");

			token = paypalReturn.get("token");
			String accessToken = new OAuthTokenCredential(
					Play.application().configuration().getString("payPalPublicKey"),
					Play.application().configuration().getString("payPalSecretKey"))
					.getAccessToken();
			Map<String, String> sdkConfig = new HashMap<String, String>();
			sdkConfig.put("mode", "sandbox");
			APIContext apiContext = new APIContext(accessToken);
			apiContext.setConfigurationMap(sdkConfig);
			Payment payment = Payment.get(accessToken, paymentID);

		} catch (PayPalRESTException e) {
			Logger.warn(e.getMessage());
		}
		return ok(confirmorder.render(paymentID, payerID, token, email, cart,
				FAQ.all()));
	}
	
	/**
	 * Order confirm.
	 *
	 * @return the result
	 */
	//@Security.Authenticated(UserFilter.class)
	public static Result orderConfirmMobile(String email) {
		System.out.println(email + " in mobile purch proc");
		session("email", email);
		String paymentID = null;
		String payerID = null;
		String token = null;
		Cart cart = Cart.getCartbyUserEmail(email);
		try {
			DynamicForm paypalReturn = Form.form().bindFromRequest();
			paymentID = paypalReturn.get("paymentId");
			payerID = paypalReturn.get("PayerID");

			token = paypalReturn.get("token");
			String accessToken = new OAuthTokenCredential(
					Play.application().configuration().getString("payPalPublicKey"),
					Play.application().configuration().getString("payPalSecretKey"))
					.getAccessToken();
			Map<String, String> sdkConfig = new HashMap<String, String>();
			sdkConfig.put("mode", "sandbox");
			APIContext apiContext = new APIContext(accessToken);
			apiContext.setConfigurationMap(sdkConfig);
			Payment payment = Payment.get(accessToken, paymentID);

		} catch (PayPalRESTException e) {
			Logger.warn(e.getMessage());
		}
		return ok(confirmordermobile.render(paymentID, payerID, token, email, cart,
				FAQ.all()));
	}

	/**
	 * Order success.
	 *
	 * @param paymentId int the payment id
	 * @param payerId int the payer id
	 * @param token String the token
	 * @return the result
	 */
	//@Security.Authenticated(UserFilter.class)
	public static Result orderSuccess(String paymentId, String payerId,
			String token) {
		String email = session().get("email");
		try {
			DynamicForm paypalReturn = Form.form().bindFromRequest();
			String paymentID = paymentId;
			String payerID = payerId;
			String accessToken = new OAuthTokenCredential(
					Play.application().configuration().getString("payPalPublicKey"),
					Play.application().configuration().getString("payPalSecretKey"))
					.getAccessToken();
			Map<String, String> sdkConfig = new HashMap<String, String>();
			sdkConfig.put("mode", "sandbox");
			APIContext apiContext = new APIContext(accessToken);
			apiContext.setConfigurationMap(sdkConfig);
			Payment payment = Payment.get(accessToken, paymentID);
			PaymentExecution paymentExecution = new PaymentExecution();
			paymentExecution.setPayerId(payerID);
			Payment newPayment = payment.execute(apiContext, paymentExecution);
			User user = User.find(session().get("email"));
			Orders order = new Orders(Cart.getCartbyUserEmail(user.email),
					user, token);
			order.orderDate = getDate();
			user.orderList.add(order);
			Iterator<Product> itr = order.productList.iterator();
			while (itr.hasNext()) {
				Product p = itr.next();
				p.order.add(order);
				p.cart = null;
			}
			List<User> sellers = new ArrayList<User>();
			Orders userOrder = user.orderList.get(user.orderList.size() - 1);
			for (Product p : userOrder.productList) {
				if (!sellers.contains(p.owner)) {
					sellers.add(p.owner);
				}
			}

			for (Product product : order.productList) {
				if (product.getOrderedQuantity() >= product.quantity)
					product.sold = true;
				int leftQuantity = product.quantity
						- product.getOrderedQuantity();
				ProductQuantity temp = new ProductQuantity(product.id,
						product.getOrderedQuantity());
				order.productQuantity.add(temp);
				product.quantity = leftQuantity;
				product.cart = null;
				product.setOrderedQuantity(0);
				order.update();
			}
			Cart.clear(user.id);
			user.save();
			for (User seller : sellers) {
				new Notification(seller, order).save();
				seller.update();
			}
		} catch (PayPalRESTException e) {
			Logger.warn(e.getMessage());
		}
		UserAgent userAgent = UserAgent.parseUserAgentString(Http.Context.current().request().getHeader("User-Agent"));
		String deviceType = userAgent.getOperatingSystem().getDeviceType().toString();
		if (deviceType.equals("MOBILE") || deviceType.equals("TABLET")) {
			return ok(orderpagemobile.render(email,
					User.find(session().get("email")).orderList, FAQ.all()));
		} else {
			return ok(orderpage.render(email,
					User.find(session().get("email")).orderList, FAQ.all()));
		}

		
	}
	
	/**
	 * Order fail.
	 *
	 * @return the result
	 */
	@Security.Authenticated(UserFilter.class)
	public static Result orderFail() {
		String email = session().get("email");
		User user = User.find(email);
		int userid = user.id;
		Cart cart = Cart.getCartbyUserEmail(email);
		Cart.clear(userid);
		Logger.info("Transaction has been canceled by user "
				+ session().get("email"));
		flash("failBuy", play.i18n.Messages.get("PaypalControllerFlash1"));
		return ok(orderresult.render(email, FAQ.all()));
	}

	/**
	 * Refund order.
	 *
	 * @param id the id
	 * @return the result
	 */
	@Security.Authenticated(UserFilter.class)
	public static Result refundOrder(int id) {
		RefundHelper.send(Orders.find(id).buyer.email, Orders.find(id).token);
		Logger.info("Token has been sent to users email: "
				+ session().get("email"));
		String href = "/orderpage/" + Orders.find(id).buyer.id;
		flash("refund", play.i18n.Messages.get("PaypalControllerFlash2"));
		return redirect(href);

	}
	

	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public static String getDate() {
		Date date = Calendar.getInstance().getTime();
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		return formatter.format(date);

	}

}
