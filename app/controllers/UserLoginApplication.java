package controllers;

import helpers.*;

import java.util.List;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.databind.JsonNode;

import models.*;
import play.Logger;
import play.Play;
import play.data.*;
import play.data.validation.Constraints.Email;
import play.data.validation.Constraints.Required;
import play.libs.F.Function;
import play.libs.F.Promise;
import play.libs.ws.WS;
import play.libs.ws.WSResponse;
import play.mvc.*;
import views.html.*;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;

/**
 * Controls the login application Redirects on the pages when needed When the
 * user registers, he gets redirected to page with ads If the user is already
 * registered, then he gets redirected to LOG IN page
 * 
 * @author eminamuratovic
 *
 */
public class UserLoginApplication extends Controller {
	static Form<User> loginUser = new Form<User>(User.class);



	static Form <Contact> contactForm= new Form<Contact>(Contact.class);
	

	// main page
	// login page
	public static Result homePage() {
		String email = session().get("email");

		if(session().get("email") == null)
			Logger.info("Homepage has been opened by guest");
		else
			Logger.info("Homepage has been opened by user with email: " + session().get("email"));
		
		return ok(homePage.render(email,Category.list(),Product.productList(), FAQ.all()));

	}

	// tries to log user to page
	// if the user can log, he gets redirected to index page
	// if the user is not in database, he gets redirected to register page
	public static Result login() {
		DynamicForm form = Form.form().bindFromRequest();
		
		String email = form.get("email");
		String password = form.get("password");

		if (User.existsEmail(email)) {
			if (User.checkLogin(email, password)) {
				
				session("email", email);

				Logger.info("User with email: "+ email + " has logged in.");
				
				if(User.find(email).hasAdditionalInfo)

					return redirect("/homepage");

				return redirect("/additionalinfo");
			} else {
				return redirect("/login");
			}

		}

		flash("error", "Email does not exist!");
		Logger.error("User has entered wrong email");
		return redirect("/toregister");
	}


	// tries to register user
	// if there is already user with the same username he gets redirected to
	// login page
	// if the user gets registered, he gets a verification email on his email
	// address
	@SuppressWarnings("static-access")
	public static Result register() throws MalformedURLException {
		
		DynamicForm form = loginUser.form().bindFromRequest();	
			String email = form.get("email");
			String password = form.get("password");
			String passconfirm = form.get("confirm_pass");
			if(!password.equals(passconfirm)) {
				Logger.error("User has entered unmatching passwords");
				flash("error","Passwords are not the same!");
				return ok(toregister.render(loginUser, email, FAQ.all()));
			}
			String confirmation = UUID.randomUUID().toString();
			User u = new User(email, password, confirmation);
			if (User.create(email, password, confirmation)) {
			String urlS = "http://localhost:9000" + "/" + "confirm/" + confirmation;
			URL url = new URL(urlS);
			MailHelper.send(email, url.toString()); 
			Logger.info("User with email: " + email + " has registered");
			flash("validate", "Please check your email");
			
			return redirect("/login");
		}else {
			Logger.error("User has entered invalid email");
			flash("error", "There is already a user with that email!");
			return ok(toregister.render(loginUser, email, FAQ.all()));
		}

	}

	// goes to page where the user can be registered
	public static Result toRegister() {
		String email = session().get("email");
		Logger.info("Page for registration has been opened");

		return ok(toregister.render(loginUser, email, FAQ.all()));
	}




	/**
	 * We return whatever the promise returns, so the return value is changed from Result to Promise<Result>
	 * @return the contact page with a message indicating if the email has been sent.
	 */
	public static Promise<Result> contact() {
		 final String userEmail = session().get("email");
		//need this to get the google recapctha value
		 final DynamicForm temp = DynamicForm.form().bindFromRequest();
		
		/* send a request to google recaptcha api with the value of our secret code and the value
		 * of the recaptcha submitted by the form */
		Promise<Result> holder = WS
				.url("https://www.google.com/recaptcha/api/siteverify")
				.setContentType("application/x-www-form-urlencoded")
				.post(String.format(
						"secret=%s&response=%s",
						// get the API key from the config file
						Play.application().configuration()
								.getString("recaptchaKey"),
						temp.get("g-recaptcha-response")))
				.map(new Function<WSResponse, Result>() {
					// once we get the response this method is loaded
					public Result apply(WSResponse response) {
						// get the response as JSON
						JsonNode json = response.asJson();
						System.out.println(json);
						System.out.println(temp.get("g-recaptcha-response"));
						Form<Contact> submit = Form.form(Contact.class)
								.bindFromRequest();

						// check if value of success is true
						if (json.findValue("success").asBoolean() == true
								&& !submit.hasErrors()) {

							final String email= temp.get("email");
							final String message= temp.get("message");
							List<User> admins=User.admins();
							for(User admin : admins){
								ContactHelper.send(email, admin.email, message);
							}
							flash("success", "Message sent!");
							if(session().get("email") == null)
								Logger.info("Guest has sent message to admin");
							else
								Logger.info("User with email: " + session().get("email") + " has sent message to admin");
							return redirect("/contactpage");
						} else {
							if(session().get("email") == null)
								Logger.info("Guest did not confirm its humanity");
							else
								Logger.info("User with email: " + session().get("email") + " did not confirm its humanity");
							flash("error", "You have to confirm that you are not a robot!");
							return ok(contact.render(userEmail, FAQ.all() ));


						}
					}
				});
		// return the promisse
		return holder;
	}


	public static Result toLogin() {

		String email = session().get("email");
		Logger.info("Opened page for login");
		
		return ok(logintest.render(email, FAQ.all()));
	}
	
	
	public static Result logOut(){
		Logger.warn("User with email: " + session().get("email") + " has logged out");

		session().clear();
		return redirect("/");
	}

	public static Result contactPage() {
		String email = session().get("email");
		if(session().get("email") == null)
			Logger.info("Guest has opened contact us page");
		else
			Logger.info("User with email: " + session().get("email") + " has opened contact us page");
		return ok(contact.render(email, FAQ.all()));

	}

	// avoiding model creation for contact form
	public static class Contact {
		@Required
		@Email
		public String email;
		@Required
		public String message;

		public Contact() {
		}

		public Contact(String email, String message) {
			this.email = email;
			this.message = message;
		}

	}
	/*********************************************************************/
	/*********************** PAYPAL SECTION ******************************/

	public static Result purchaseProcessing() {

		try {
			String total=String.valueOf(Cart.getCart(session().get("email")).checkout);
			String accessToken = new OAuthTokenCredential(
					"AbijjyL8ZwCwdnVyiqJbpiNz9oIxovkOnp5T3vM97TLWOfdY-YKthB4geUI-ftm-Bqxo5awhkAmiNAZb",
					"EJtniUjUuTaw7SryBqatAtIs96Bzs9hklRejABEyVwYhI0eF0cQyWIahIWnA3giEmLza6-GrK81r42Ai"
					//"AYZe2FNhJ97hr8qBLfXk7TUerHmzqWeDlrS23g5CIEWRXZ_nwuiw-bffSb85AfdezwLcf1tcv7P4hOUe",
				//	"EFggF9gtemTL-YvgplaYzS4TyBwAnHkYIYg785IM9uzTWEAaVDa_9gX1qAGa7GrzZ6iQrMi1A9HsTfVe"
					)
					.getAccessToken();

			Map<String, String> sdkConfig = new HashMap<String, String>();
			sdkConfig.put("mode", "sandbox");
			APIContext apiContext = new APIContext(accessToken);
			apiContext.setConfigurationMap(sdkConfig);
			Amount amount = new Amount();
			amount.setTotal(total+"0");
			amount.setCurrency("USD");
			Transaction transaction = new Transaction();
			transaction.setDescription("Order via bitBay");
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
			redirectUrls.setCancelUrl("http://localhost:9000/orderfail");
			redirectUrls.setReturnUrl("http://localhost:9000/orderconfirm");
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

	public static Result orderConfirm(){
		String email = session().get("email");
		String paymentID = null;
		String payerID = null;
		String token=null;
	
		try{
			DynamicForm paypalReturn = Form.form().bindFromRequest();
			//String paymentID = paypalReturn.get("paymentId");
			 paymentID = paypalReturn.get("paymentId");
			 payerID = paypalReturn.get("PayerID");
			 
			 token = paypalReturn.get("token");
			String accessToken = new OAuthTokenCredential(
					"AbijjyL8ZwCwdnVyiqJbpiNz9oIxovkOnp5T3vM97TLWOfdY-YKthB4geUI-ftm-Bqxo5awhkAmiNAZb",
					"EJtniUjUuTaw7SryBqatAtIs96Bzs9hklRejABEyVwYhI0eF0cQyWIahIWnA3giEmLza6-GrK81r42Ai"
					//"AYZe2FNhJ97hr8qBLfXk7TUerHmzqWeDlrS23g5CIEWRXZ_nwuiw-bffSb85AfdezwLcf1tcv7P4hOUe",
					//"EFggF9gtemTL-YvgplaYzS4TyBwAnHkYIYg785IM9uzTWEAaVDa_9gX1qAGa7GrzZ6iQrMi1A9HsTfVe"
					)
					.getAccessToken();
			Map<String, String> sdkConfig = new HashMap<String, String>();
			sdkConfig.put("mode", "sandbox");
			APIContext apiContext = new APIContext(accessToken);
			apiContext.setConfigurationMap(sdkConfig);
//			
			Payment payment= Payment.get(accessToken, paymentID);
			User user=User.find(session().get("email"));
			Orders order= new Orders(Cart.getCart(session().get("email")),user,token);
			order.save();
			user.orderList.add(order);
			user.update();
			Cart.clear(user.id);
			Iterator<Product> itr = order.productList.iterator();
			while (itr.hasNext()) {
				Product product=itr.next();
				product.order=order;
				product.sold=true;
				product.update();
				
			}
			//Cart.clear(temp.id);
		} catch (PayPalRESTException e) {
			// TODO Auto-generated catch block
			Logger.warn(e.getMessage());
			}
		
		return ok(confirmorder.render(paymentID,payerID,token,email,User.find(session().get("email")).orderList,  FAQ.all()));	
		}
	
	public static Result orderSuccess(String paymentId,String payerId,String token) {
		String email = session().get("email");
		try{
		DynamicForm paypalReturn = Form.form().bindFromRequest();
		//String paymentID = paypalReturn.get("paymentID");
	String paymentID=paymentId;
	String payerID = payerId;
		String ttoken = token;
		String accessToken = new OAuthTokenCredential(
				"AbijjyL8ZwCwdnVyiqJbpiNz9oIxovkOnp5T3vM97TLWOfdY-YKthB4geUI-ftm-Bqxo5awhkAmiNAZb",
				"EJtniUjUuTaw7SryBqatAtIs96Bzs9hklRejABEyVwYhI0eF0cQyWIahIWnA3giEmLza6-GrK81r42Ai"
				//"AYZe2FNhJ97hr8qBLfXk7TUerHmzqWeDlrS23g5CIEWRXZ_nwuiw-bffSb85AfdezwLcf1tcv7P4hOUe",
				//"EFggF9gtemTL-YvgplaYzS4TyBwAnHkYIYg785IM9uzTWEAaVDa_9gX1qAGa7GrzZ6iQrMi1A9HsTfVe"
				)
				.getAccessToken();
		Map<String, String> sdkConfig = new HashMap<String, String>();
		sdkConfig.put("mode", "sandbox");
		APIContext apiContext = new APIContext(accessToken);
		apiContext.setConfigurationMap(sdkConfig);
		Payment payment= Payment.get(accessToken, paymentID);
		PaymentExecution paymentExecution=new PaymentExecution();
		paymentExecution.setPayerId(payerID);
		Payment newPayment=payment.execute(apiContext, paymentExecution);
		User currUser=User.find(session().get("email"));
		List<Orders> userOrders=currUser.orderList;
		for(Orders order:userOrders){
			for(Product product:order.productList){
				int leftQuantity=product.getQuantity()-product.getOrderedQuantity();
				product.setQuantity(leftQuantity);
				product.setOrderedQuantity(0);
			}
		}
	} catch (PayPalRESTException e) {
		// TODO Auto-generated catch block
		Logger.warn(e.getMessage());}
		
		return ok(orderpage.render(email,User.find(session().get("email")).orderList,  FAQ.all()));
	}

	public static Result orderFail() {
		//return ok(creditresult.render("nije proslo"));
		flash("failBuy", "Transaction canceled!");
		return ok(orderresult.render());

	}
	public static Result refundOrder(int id){
		RefundHelper.send(Orders.find(id).buyer.email, Orders.find(id).token);
		
		
		String href="/orderpage/"+Orders.find(id).buyer.id;
		flash("refund","Token has been sent to your email!");
		return redirect(href);
		
	}
	
	/*******************************************************************/
}
