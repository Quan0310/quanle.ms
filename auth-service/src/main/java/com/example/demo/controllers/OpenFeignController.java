package com.example.demo.controllers;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Artist;
import com.example.demo.security.jwt.JwtUtils;
import com.example.demo.security.services.UserDetailsDTO;
import com.example.demo.security.services.UserDetailsImpl;
import com.example.demo.security.services.UserDetailsServiceImpl;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/ofc")
public class OpenFeignController {
	@Autowired
	JwtUtils jwtUtils;
	@Autowired
	UserDetailsServiceImpl detailsServiceImpl;
	@GetMapping("/ud")
	public UserDetailsDTO getUserDetail(Authentication authentication) {
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//		return userDetails;
		return new UserDetailsDTO(userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));
	}
	
	 @GetMapping("/user-details")
	    public ResponseEntity<?> getUserDetails(@RequestHeader("Authorization") String authHeader) {
	        String jwt = jwtUtils.parseJwt(authHeader);
	        if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
	            String username = jwtUtils.getUserNameFromJwtToken(jwt);
	            UserDetails userDetails = detailsServiceImpl.subLoadUserByUsername(username);
	            return ResponseEntity.ok(userDetails);
	        }
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	  
	    }
	 @GetMapping("/user-detailss")
	    public String getUserDetailss(@RequestHeader("Authorization") String authHeader) {
	        
	        return authHeader;
	    }
	 
	 @GetMapping("/testof")
	 public Artist artist() {
		 return new Artist("Taylor Swift", "female");
	 }
}
