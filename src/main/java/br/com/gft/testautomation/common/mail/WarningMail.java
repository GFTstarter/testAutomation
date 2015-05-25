package br.com.gft.testautomation.common.mail;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

/** Class responsible for creating the message that will be sent via email using the
 * MailSender class of Spring Framework. */
public class WarningMail {

	//Create a new MailSender attribute and set to this class
	private MailSender mailSender;
	
	public void setMailSender(MailSender mailSender){
		this.mailSender = mailSender;
	}
	
	/** Method that send the email by creating a new SimpleMailMessage and using the 
	 * send method of the MailSender. Received four strings: from who the message comes,
	 * to who the message went, the message subject and the message itself */
	public void sendMail(String from, String to, String subject, String msg){
		
		SimpleMailMessage message = new SimpleMailMessage();
		
		message.setFrom(from);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(msg);
		mailSender.send(message);
	}
}
