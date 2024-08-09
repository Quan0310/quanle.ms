package com.example.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.demo.model.MessageDTO;

@Service
public class MessageService {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	EmailService emailService;
	
	@KafkaListener(id="notificationGroup", topics= "notification")
	public void listen(String email) {
		logger.info("Received: ",email);
		emailService.sendEmail(email);
	}
}
