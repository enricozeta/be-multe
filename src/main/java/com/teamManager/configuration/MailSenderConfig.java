package com.teamManager.configuration;

import java.util.Properties;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

/**
 * The Class MailSenderConfig.
 */
@Service("mailSender")
public class MailSenderConfig {

	/**
	 * Gets the java mail sender.
	 *
	 * @return the java mail sender
	 */
	public JavaMailSender getJavaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.live.com");
		mailSender.setPort(587);

		mailSender.setUsername("enrico_04@hotmail.it");
		mailSender.setPassword("password");

		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.debug", "true");

		return mailSender;
	}
}
