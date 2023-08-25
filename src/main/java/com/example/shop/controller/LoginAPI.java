package com.example.shop.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.shop.dto.ResponseDTO;
import com.example.shop.dto.UserDTO;
import com.example.shop.repository.UserRepo;
import com.example.shop.service.JwtTokenService;
import com.example.shop.service.UserService;

@RestController
public class LoginAPI {
	@Autowired
	AuthenticationManager authenticationManager;
	 
	@Autowired 
	JwtTokenService jwtTokenService;
	
	@Autowired
	UserService userService;
	
	// Lấy thông tin của thằng người dùng đang đăng nhập
	@PostMapping("/me")
	public UserDTO me(Principal p) {
		String username = p.getName();
		UserDTO userDTO = userService.findByUsername(username);
		return userDTO;
	}
//	@PostMapping("/me")
//	@PreAuthorize("isAuthenticated()")
//	public Principal me(Principal p) {
//		
//		return p;
//	}
	@PostMapping("/login")
	public ResponseDTO<String> login(@RequestParam("username") String username,@RequestParam("password") String password){
		
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		//loginsucces
		return ResponseDTO.<String>builder().status(200).data(jwtTokenService.createToken(username)).build();
	}
}
