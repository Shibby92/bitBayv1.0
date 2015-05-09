package helpers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import models.User;
import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security;
import sun.misc.BASE64Decoder;


/**
 * class for security purposes. It uses class BASE64Decoder for decoding request.
 * It is also used for getting user from passed context object
 * @author nerminvucinic
 *
 */
public class ServiceAuth extends Security.Authenticator {

	
	/**
	 * first gets authorization string from the request headers, then decodes it using class BASE64Decoder and assign it to the new string,
	 * gets the parameters from that string, compares them with appropriate values from database, and if matched, returns email of the user
	 */
	@Override
	public String getUsername(Context ctx) {
		
		String[] auth = ctx.request().headers().get("Authorization");
		if (auth == null)
			return null;

		auth[0] = auth[0].replace("Basic ", "");
		BASE64Decoder decoder = new BASE64Decoder();
		String basic = null;
		try {
			basic = new String(decoder.decodeBuffer(auth[0]), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(basic == null)
			return null;
		
		String[] userPass = basic.split(":");
		
		if (userPass.length != 2)
			return null;

		String email = userPass[0];
		String pass = userPass[1];
		System.out.println("Username: " + email + " Password: " + pass);
		if (!User.checkLogin(email, pass)) {
			return null;
		}

		return email;
	}

	
	/**
	 * returns user that is currently in session based on passed context object
	 * @param ctx http context object
	 * @return current user
	 */
	public User getCurrentUser(Context ctx) {
		String email = getUsername(ctx);
		return User.find(email);
	}

	
	/**
	 * returns badRequest result object in case of unauthorized user access attempt
	 */
	@Override
	public Result onUnauthorized(Context ctx) {
		return badRequest("Permission denied!");
	}

}
