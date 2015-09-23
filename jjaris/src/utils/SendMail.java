package utils;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public final class SendMail {
	private SendMail() {
		// TODO Auto-generated constructor stub
	}
	
	
	public static void SendEmail(String to,String subject, String boddy) {
		Properties props = System.getProperties();
		props.setProperty("mail.smtp.host", "localhost");
		Session session = Session.getDefaultInstance(System.getProperties());
		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);
			// Set From: header field of the header.
			message.setFrom(new InternetAddress("web@gmail.com"));
			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			// Set Subject: header field
			message.setSubject("This is the Subject Line!");
			// Now set the actual message
			message.setText("This is the actual message");
			// Send message
			Transport.send(message);
			System.out.println("Sent message successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}

		
		
	}


