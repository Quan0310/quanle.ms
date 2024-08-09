package com.example.demo.sercurity.services;

import java.util.List;

public class UserDetailsDTO {
	 private Long id;
	    private String username;
	    private String email;
	    private List<String> authorities; 

	    // Constructor
	    public UserDetailsDTO() {}

	    public UserDetailsDTO(Long id, String username, String email, List<String> authorities) {
	        this.id = id;
	        this.username = username;
	        this.email = email;
	        this.authorities = authorities;
	    }

	    // Getters and Setters
	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public String getUsername() {
	        return username;
	    }

	    public void setUsername(String username) {
	        this.username = username;
	    }

	    public String getEmail() {
	        return email;
	    }

	    public void setEmail(String email) {
	        this.email = email;
	    }

	    public List<String> getAuthorities() {
	        return authorities;
	    }

	    public void setAuthorities(List<String> authorities) {
	        this.authorities = authorities;
	    }

	    @Override
	    public String toString() {
	        return "UserDetailsDTO [id=" + id + ", username=" + username + ", email=" + email + ", authorities="
	                + authorities + "]";
	    }
}
