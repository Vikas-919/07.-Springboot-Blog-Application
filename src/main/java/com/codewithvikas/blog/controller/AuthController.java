package com.codewithvikas.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithvikas.blog.payload.JWTAuthResponse;
import com.codewithvikas.blog.payload.LoginDto;
import com.codewithvikas.blog.payload.RegisterDto;
import com.codewithvikas.blog.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	private AuthService authService;
	
	@Autowired
	public AuthController(AuthService authService) {
		this.authService = authService;
	}
	
	@Operation(
			summary = "Login/Sign REST API",
			description = "Login/Sign REST API is used to authenticate the logged in credentials"
	)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 SUCCESS"
	)
	//Build Login REST API...
	@PostMapping(value = {"/login", "/signin"})
	public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDto loginDto){
		String token = authService.login(loginDto);
		
		JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
		jwtAuthResponse.setAccessToken(token);
		
		return ResponseEntity.ok(jwtAuthResponse);
	}
	
	@Operation(
			summary = "Register/Signup REST API",
			description = "Register/Signup REST API is used to register a new user"
	)
	@ApiResponse(
			responseCode = "201",
			description = "Http Status 201 CREATED"
	)
	//Build Register REST API...
	@PostMapping(value= {"/register", "/signup"})
	public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
		String response = authService.register(registerDto);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
}
