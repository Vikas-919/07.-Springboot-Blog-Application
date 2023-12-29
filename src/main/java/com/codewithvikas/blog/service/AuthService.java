package com.codewithvikas.blog.service;

import com.codewithvikas.blog.payload.LoginDto;
import com.codewithvikas.blog.payload.RegisterDto;

public interface AuthService {
	String login(LoginDto loginDto);
	String register(RegisterDto registerDto);
}
