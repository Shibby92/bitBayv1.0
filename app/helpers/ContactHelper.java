package helpers;

import models.Message;
import models.User;
import play.libs.mailer.Email;
import play.libs.mailer.MailerPlugin;

public class ContactHelper {

	public static void send(String emailFrom, String emailTo, String message) {
		Email mail = new Email();
		mail.setSubject("New message from bitBay user!");
		mail.setFrom("Bitbay.ba <bit.play.test@gmail.com>");
		mail.addTo(emailFrom);
		mail.addTo(emailTo);
		
		mail.setBodyText(message);
		mail.setBodyHtml(String.format("<html><body><strong> %s </strong>: <p> %s </p> </body></html>", emailFrom, message));
		MailerPlugin.send(mail);
	}
	
	public static void sendToPage(String emailFrom, String emailTo, String message) {
		Message.create(message, User.find(emailFrom), User.find(emailTo));
	}
}
