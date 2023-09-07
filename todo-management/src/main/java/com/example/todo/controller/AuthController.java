package com.example.todo.controller;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.todo.dto.JwtAuthResponse;
import com.example.todo.dto.LoginDto;
import com.example.todo.dto.RegisterDto;
import com.example.todo.service.AuthService;

import lombok.AllArgsConstructor;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	private AuthService authService;
	
	//Build register REST API
	
	@PostMapping("/register")
	ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
		String repsonse = authService.register(registerDto);
		return new ResponseEntity<>(repsonse,HttpStatus.CREATED);
	}
	
	//Build Login REST API
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto){
		//String token = authService.login(loginDto);
		//JwtAuthResponse jwtAuthResponse = new  JwtAuthResponse();
		//jwtAuthResponse.setAccessToken(token);
		//jwtAuthResponse.setTokenType("")
		JwtAuthResponse jwtAuthResponse = authService.login(loginDto);
		return new ResponseEntity<JwtAuthResponse>(jwtAuthResponse,HttpStatus.OK);
	}

}
