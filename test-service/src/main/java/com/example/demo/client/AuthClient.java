package com.example.demo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.model.Artist;
import com.example.demo.sercurity.services.UserDetailsDTO;
import com.example.demo.sercurity.services.UserDetailsImpl;

@FeignClient(value = "auth", url = "http://localhost:8100")
public interface  AuthClient {
	 @RequestMapping(method = RequestMethod.GET, value = "/ofc/ud")
	 String getUserDetails(@RequestHeader("Authorization") String authHeader);
	 
	 @RequestMapping(method = RequestMethod.GET, value = "/api/test/all")
	    String test();
	 
	 @RequestMapping(method = RequestMethod.GET, value = "/ofc/user-detailss")
	    String getUserDetailss(@RequestHeader("Authorization") String authHeader);
	 
	 
	 @RequestMapping(method = RequestMethod.GET, value = "/ofc/testof")
	    Artist testof();
}
