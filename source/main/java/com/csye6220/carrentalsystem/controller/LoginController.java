 package com.csye6220.carrentalsystem.controller;

import com.csye6220.carrentalsystem.model.User;
import com.csye6220.carrentalsystem.model.UserRole;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.csye6220.carrentalsystem.service.UserService;

@Controller
public class LoginController {


    @Autowired
    UserService userService;
    
    @GetMapping("/login")
    public ModelAndView redirectToLoginPage(){
		return new ModelAndView("login_page");
    }

    @PostMapping("/login")
    public String checkValidation(@RequestParam("username") String username) {
    	 return "redirect:/user";
    }
 
}

