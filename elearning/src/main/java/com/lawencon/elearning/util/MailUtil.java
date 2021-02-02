package com.lawencon.elearning.util;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.lawencon.elearning.helper.MailHelper;

@Component
public class MailUtil {
	
	public void sendMail(MailHelper mailHelper) throws Exception {
		JavaMailSender mailSender = new JavaMailSenderImpl();
		
		MimeMessage message = mailSender.createMimeMessage();
		
	    MimeMessageHelper helper = new MimeMessageHelper(message, true);
	    
	    helper.setFrom(mailHelper.getFrom());
	    helper.setTo(mailHelper.getTo());
	    helper.setSubject(mailHelper.getSubject());
	    helper.setText(mailHelper.getText(), true);
	    
	    mailSender.send(message);
	}
}
