package helpers;

import models.Message;
import models.Product;
import models.User;
import play.libs.mailer.Email;
import play.libs.mailer.MailerPlugin;

public class ContactHelper {

	public static void send(String emailFrom, String emailTo, String message) {
		Email mail = new Email();
		mail.setSubject("New message from bitBay.com!");
		mail.setFrom("Bitbay.ba <bit.play.test@gmail.com>");
		mail.addTo(emailFrom);
		mail.addTo(emailTo);
		
		mail.setBodyText(message);
		mail.setBodyHtml(String.format("<html><body><strong> %s </strong>: <p> %s </p> </body></html>", emailFrom, message));
		MailerPlugin.send(mail);
	}
	
	public static void sendToPage(String emailFrom, String emailTo, String message, String subject) {
		Message.create(message, User.find(emailFrom), User.find(emailTo), subject);
	}
	
	public static void send(String emailFrom, String emailTo, String message, Product p) {
		Email mail = new Email();
		mail.setSubject("Report message for product by id: " + p.id + " !");
		mail.setFrom("Bitbay.ba <bit.play.test@gmail.com>");
		mail.addTo(emailFrom);
		mail.addTo(emailTo);
		
		mail.setBodyText(message);
		mail.setBodyHtml(String.format("<html><body><strong> %s </strong>: <p> %s </p> </body></html>", emailFrom, message));
		MailerPlugin.send(mail);
	}
	
	public static void sendToPage(String emailFrom, String emailTo, String message, Product p, String subject) {
		Message.createReport(message, User.find(emailFrom), User.find(emailTo), p, subject);
	}
}
