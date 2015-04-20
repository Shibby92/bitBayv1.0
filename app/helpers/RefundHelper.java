package helpers;

import play.libs.mailer.Email;
import play.libs.mailer.MailerPlugin;

public class RefundHelper {

	public static void send(String emailTo, String message) {
		Email mail = new Email();
		mail.setSubject("New message from bitBay user!");
		mail.setFrom("Bitbay.ba <bit.play.test@gmail.com>");
		mail.addTo(emailTo);

		mail.setBodyText(message);
		mail.setBodyHtml(String
				.format("<html><body><strong> Here is the token from your order:</strong>: <p> %s </p> </body></html>",
						message));
		MailerPlugin.send(mail);
	}
}