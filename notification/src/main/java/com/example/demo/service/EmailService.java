package com.example.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.demo.model.MessageDTO;

@Service
public class EmailService {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private JavaMailSender mailSender;

	@Async
	public void sendEmail(String email) {
		try {
			logger.info("Sending email...");
			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(email);
			message.setSubject("Register");
			message.setText("\r\n"
					+ "Registered account "+email+  " successfully");
			mailSender.send(message);
			logger.info("Email sent success...");
		} catch (Exception e) {
			logger.info("Email sent with error: "+e.getMessage());
		}
	}
}
