package utils;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
public class SendMail {
    public void sendMail(String m_from,String m_to,String m_subject,String m_body){
      try {
          Session m_Session;
          Message m_simpleMessage;
          InternetAddress m_fromAddress;
          InternetAddress m_toAddress;
          Properties m_properties;

          m_properties     = new Properties();
          m_properties.put("mail.smtp.host", "smtp.gmail.com"); 
          m_properties.put("mail.smtp.socketFactory.port", "465");
          m_properties.put("mail.smtp.socketFactory.class",
                                     "javax.net.ssl.SSLSocketFactory");
          m_properties.put("mail.smtp.auth", "true");
          m_properties.put("mail.smtp.port", "465");

          m_Session=Session.getDefaultInstance(m_properties,new Authenticator() {
               protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("xxxxx","yyyyy"); // username and the password
               }
          });

          m_simpleMessage  =   new MimeMessage(m_Session);
          m_fromAddress    =   new InternetAddress(m_from);
          m_toAddress      =   new InternetAddress(m_to);
          m_simpleMessage.setFrom(m_fromAddress);
          m_simpleMessage.setRecipient(RecipientType.TO, m_toAddress);
          m_simpleMessage.setSubject(m_subject);

          m_simpleMessage.setContent(m_body, "text/html");       
          //m_simpleMessage.setContent(m_body,"text/plain");

          Transport.send(m_simpleMessage);
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
  }

  public static void main(String[] args) {
      SendMail send_mail    =   new SendMail();
      String empName = "Antony Raj S";
      String title ="<b>Hi !"+empName+"</b>";
      send_mail.sendMail("anto@gmail.com", "anthony@slingmedia.com", "Please apply for leave for the following dates", title+"<br>by<br><b>HR<b>");
    }
}