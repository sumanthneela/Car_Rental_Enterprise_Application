
package com.csye6220.carrentalsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.csye6220.carrentalsystem.model.User;
import com.csye6220.carrentalsystem.model.UserRole;
import com.csye6220.carrentalsystem.service.UserService;

@Controller
public class HomeController {
	
	@Autowired
	UserService userservice;
	
	@GetMapping("/")
	public String userSelectionForm() {
		return "login_page";
	}

	@GetMapping("/user")
	public String CustomerLogin(Model model) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String username = authentication.getName();
	    User user = userservice.getUserByUsername(username);
	    if(user.getRole() == UserRole.CUSTOMER) {
	    	model.addAttribute("reservations", user.getReservations());
	    	return "customer_portal";
	    }
	    else if(user.getRole() == UserRole.AGENCY_STAFF)
	    	return "rental_agency_portal";
	    else 
	    	return "admin_portal";
	}
	
	@PostMapping("/error")
	public String errorpage() {
		return "login_page";
	}
}
