package helpers;

import play.libs.mailer.Email;
import play.libs.mailer.MailerPlugin;

public class MailHelper {

	public static void send(String email, String message) {
		Email mail = new Email();
		mail.setSubject("Bitbay.ba verification mail!");
		mail.setFrom("Bitbay.ba <bit.play.test@gmail.com>");
		mail.addTo("Bitter Contact <bit.play.test@gmail.com>");
		mail.addTo(email);
		
		mail.setBodyText(message);
		mail.setBodyHtml(String.format("<html><body><strong> %s </strong>: <p> %s </p> </body></html>", email, message));
		MailerPlugin.send(mail);
	}
}
