package com.csye6220.carrentalsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.csye6220.carrentalsystem.model.User;
import com.csye6220.carrentalsystem.model.UserRole;
import com.csye6220.carrentalsystem.service.UserService;

import java.util.List;



@Controller
@RequestMapping("/rental-agencies")
public class RentalAgencyController {
	
	@Autowired
	private UserService userService;

	
	@GetMapping("/agencyportal")
    public String showAgencyPortal() {
        return "rental_agency_portal";
    }
	
	@GetMapping("/addagencyportal")
    public String showaddAgencyPortal() {
        return "add_rental_agency";
    }
	
    @PostMapping("/add")
    public String addRentalAgency(
    		@RequestParam(name = "username") String username,
            @RequestParam(name = "email") String email,
            @RequestParam(name = "password") String password,
            @RequestParam(name = "phoneNumber") Long phoneNumber
    ) {
    	User user = new User(username, email, new BCryptPasswordEncoder().encode(password), phoneNumber, UserRole.AGENCY_STAFF);
    	userService.createUser(user);
        return "redirect:/rental-agencies/allAgencies";
    }
    
    @GetMapping("/{userID}")
    public ModelAndView getUserByID(@PathVariable int userID) {
        ModelAndView modelAndView = new ModelAndView("view_user");
        User user = userService.getUserByID(userID);
        modelAndView.addObject("user", user);
        return modelAndView;
    }
    
    @GetMapping("/update") 
    public String editagencyForm(Model model) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String username = authentication.getName();
	    User currentUser = userService.getUserByUsername(username);
	    
        model.addAttribute("user", currentUser);
        return "edit_rental_agency_content";
    }
    
    @GetMapping("/update/{userID}") 
    public String editagencyForm(@PathVariable int userID, Model model) {
        User user = userService.getUserByID(userID);
        model.addAttribute("user", user);
        return "edit_rental_agency_content";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute("user") User user, @RequestParam("userID") int userID, 
    		@RequestParam(name = "username") String username,
            @RequestParam(name = "email") String email,
            @RequestParam(name = "password") String password,
            @RequestParam(name = "phoneNumber") Long phoneNumber) {
    	user.setUserID(userID);
    	user.setUsername(username);
    	user.setEmail(email);
    	user.setPassword(new BCryptPasswordEncoder().encode(password));
    	user.setPhoneNumber(phoneNumber);
    	user.setRole(UserRole.AGENCY_STAFF);

    	userService.update(user);
    	
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    User currentUser = userService.getUserByUsername(authentication.getName());
	    
    	if(currentUser.getRole() == UserRole.AGENCY_STAFF) {
        	return "redirect:/user";
        } else {
        	return "redirect:/rental-agencies/allAgencies";
        }
    }
    
    @GetMapping("/delete/{userID}")
    public String deleteUser(@PathVariable int userID) {
    	userService.delete(userID);
        return "redirect:/rental-agencies/allAgencies";
    }

    @GetMapping("/allUsers")
    public ModelAndView getAllUsers() {
        ModelAndView modelAndView = new ModelAndView("view_all_users");
        List<User> users = userService.getAllCustomers();
        users.forEach(System.out::println);
        modelAndView.addObject("users", users);
        return modelAndView;
    }
    
    @GetMapping("/allAgencies")
    public ModelAndView getAllAgencies() {
        ModelAndView modelAndView = new ModelAndView("view_all_rental_agencies");
        List<User> users = userService.getAllAgencies();
        users.forEach(System.out::println);
        modelAndView.addObject("users", users);
        return modelAndView;
    }
    

//
//    @GetMapping("/delete/{agencyID}")
//    public String deleteRentalAgency(@PathVariable int agencyID) {
//        rentalAgencyService.deleteAgency(agencyID);
//        return "redirect:/rental-agencies/all";
//    }
   
//
}


