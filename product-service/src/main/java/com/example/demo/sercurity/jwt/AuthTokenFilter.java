package com.example.demo.sercurity.jwt;

import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.client.AuthClient;
import com.example.demo.sercurity.services.UserDetailsDTO;
import com.example.demo.sercurity.services.UserDetailsImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthTokenFilter extends OncePerRequestFilter {
	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private AuthClient authClient;

	private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		try {
			String jwt = request.getHeader("Authorization");
			System.out.println("jwwt: "+ jwt);
			ObjectMapper objectMapper = new ObjectMapper();
			String userDetailsString = authClient.getUserDetails(jwt);

			UserDetailsDTO userDetailsDTO = objectMapper.readValue(userDetailsString, UserDetailsDTO.class);
			Collection<? extends GrantedAuthority> authorities = userDetailsDTO.getAuthorities().stream()
					.map(SimpleGrantedAuthority::new).collect(Collectors.toList());
			UserDetails userDetails = new UserDetailsImpl(userDetailsDTO.getId(), userDetailsDTO.getUsername(),
					userDetailsDTO.getEmail(), null, authorities);
			System.out.println("userDetails: " + userDetails.toString());
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
					null, userDetails.getAuthorities());
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

			SecurityContextHolder.getContext().setAuthentication(authentication);

		} catch (Exception e) {
			logger.error("Cannot set user authentication: {}", e.getMessage());
		}

		filterChain.doFilter(request, response);
	}

	private String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");

		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.substring(7, headerAuth.length());
		}

		return null;
	}
}
