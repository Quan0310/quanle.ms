package com.example.demo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient("auth-service")
public interface  AuthClient {
	 @RequestMapping(method = RequestMethod.GET, value = "/ofc/ud")
	 String getUserDetails(@RequestHeader("Authorization") String authHeader);
}
