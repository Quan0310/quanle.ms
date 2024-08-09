package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.sercurity.services.UserDetailsImpl;


@RestController
@RequestMapping("/api")
public class Controller {
//	@GetMapping("/all")
//	String test(Authentication authentication) {
//		return "quan";
//	}
	@GetMapping("/all")
	public ResponseEntity<?> userAccess(Authentication authentication) {
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		return ResponseEntity.ok(userDetails);
//    return "User Content.";
	}
}
