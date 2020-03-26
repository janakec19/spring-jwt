package com.test.jwt.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.test.jwt.model.AuthRequest;
import com.test.jwt.model.AuthResponse;
import com.test.jwt.service.MyUserDetailsService;
import com.test.jwt.util.JwtUtil;

@RestController
public class AppRestApi {
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private MyUserDetailsService userDetailsService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@GetMapping("/")
	public String getMessage() {
		return "Hello Welcome !!! ";
	}
	
	@PostMapping("/authenticate")
	public ResponseEntity<?> authenticate(@RequestBody AuthRequest request) {
		try {
		authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		}
		catch (BadCredentialsException e) {
			throw e;
		}
		UserDetails user=userDetailsService.loadUserByUsername(request.getUsername());
		String jwtToken=jwtUtil.generateToken(user);
		return ResponseEntity.ok(new AuthResponse(jwtToken));
	}
	
	@GetMapping("/admin")
	public String getAdminMessage() {
		return "Hello Welcome Admin !!! ";
	}
	@GetMapping("/user")
	public String getUserMessage() {
		return "Hello Welcome User !!! ";
	}

}
