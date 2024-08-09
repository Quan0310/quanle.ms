package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.OrderRequest;
import com.example.demo.sercurity.services.UserDetailsImpl;
import com.example.demo.service.OrderService;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
	KafkaTemplate<String, Object> kafkaTemplate;
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest orderRequest, Authentication authentication) {
    	UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
        orderService.placeOrder(orderRequest, userDetailsImpl.getId());
        kafkaTemplate.send("inventory", orderRequest);
        return "Order Placed Successfully";
    }
}