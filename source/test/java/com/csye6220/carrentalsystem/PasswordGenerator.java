package com.csye6220.carrentalsystem;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {
	
	public static void main(String[] args) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		String rawPassword = "yusuf";
		String encodedPassword = bCryptPasswordEncoder.encode(rawPassword);
		
		System.out.println(encodedPassword);
	}

}

 
