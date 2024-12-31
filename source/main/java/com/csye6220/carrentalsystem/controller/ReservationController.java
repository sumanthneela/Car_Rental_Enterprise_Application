package com.csye6220.carrentalsystem.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.csye6220.carrentalsystem.model.Car;
import com.csye6220.carrentalsystem.model.Reservation;
import com.csye6220.carrentalsystem.model.User;
import com.csye6220.carrentalsystem.model.UserRole;
import com.csye6220.carrentalsystem.service.CarService;
import com.csye6220.carrentalsystem.service.ReservationService;
import com.csye6220.carrentalsystem.service.UserService;

@Controller
@RequestMapping("/reservations")
public class ReservationController {
	
	@Autowired
	private ReservationService reservationService;
	
	@Autowired
	private CarService carService;
	
	@Autowired
	private UserService userService;

	@PostMapping("/reserve")
	public String reserveCar(
	       @RequestParam("carID") int carID,
	       @RequestParam("startDate") String startDate,
	       @RequestParam("endDate") String endDate,
	       RedirectAttributes redirectAttributes) {
		   Car car = carService.getCarByID(carID);

		   Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		   String username = authentication.getName();
		   User user = userService.getUserByUsername(username);
	       if (car == null) {
	           redirectAttributes.addFlashAttribute("error", "Car not found.");
	           return "redirect:/cars/all"; // Redirect to the car listing page or an error page
	       }
	       	try {
	            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	            Date parsedStartDate = dateFormat.parse(startDate);
	            Date parsedEndDate = dateFormat.parse(endDate);
	            
	            if (carService.isCarAvailableForReservation(carID, parsedStartDate, parsedEndDate)) {
	                Reservation reservation = new Reservation();
	                reservation.setUser(user);
	                reservation.setCar(car);
	                reservation.setStartDate(parsedStartDate);
	                reservation.setEndDate(parsedEndDate);
	                reservation.setStatus("Pending");

	                reservationService.createReservation(reservation);
	                
	                car.getReservations().add(reservation);
	                carService.update(car);

	                redirectAttributes.addFlashAttribute("success", "Reservation successful!");
	            } else {
	            	System.out.println("Failed");
	                redirectAttributes.addFlashAttribute("error", "Car is not available for the selected dates.");
	            }
	        } catch (ParseException e) {
	            e.printStackTrace();
	            redirectAttributes.addFlashAttribute("error", "Invalid date format.");
	        }

	        return "redirect:/cars/all"; 
	    }

    @GetMapping("/{reservationID}")
    public ModelAndView getReservationById(@PathVariable int reservationID) {
        ModelAndView modelAndView = new ModelAndView("view_reservation");
        Reservation reservation = reservationService.getReservationById(reservationID);
        modelAndView.addObject("reservation", reservation);
        return modelAndView;
    }
    
    @GetMapping("/update/{reservationID}")
    public String updateReservationpage(@PathVariable int reservationID, Model model) {
    	Reservation reservation = reservationService.getReservationById(reservationID);
    	model.addAttribute("reservation", reservation);
        return "edit_reservation";
    }

    @PostMapping("/update")
    public String updateReservation(
    		@RequestParam int reservationID,
    		@RequestParam int userID,
    		@RequestParam int carID,
            @RequestParam(name = "startDate") String startDate,
            @RequestParam(name = "endDate") String endDate,
            RedirectAttributes redirectAttributes
    ) {
    	Reservation reservation = new Reservation();

	    User user = userService.getUserByID(userID);
	    Car car = carService.getCarByID(carID);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date parsedStartDate = dateFormat.parse(startDate);
            Date parsedEndDate = dateFormat.parse(endDate);
            reservation.setCar(car);
            reservation.setUser(user);
            reservation.setReservationID(reservationID);
            reservation.setStartDate(parsedStartDate);
            reservation.setEndDate(parsedEndDate);
            reservation.setStatus("Pending");
        } catch (ParseException e) {
            e.printStackTrace();
            return "redirect:/error";
        }
        reservationService.updateReservation(reservation);
        return "redirect:/reservations/all";
    }

    @GetMapping("/delete/{reservationID}")
    public String deleteReservation(@PathVariable int reservationID) {
    	reservationService.deleteReservation(reservationID);
        return "redirect:/reservations/all";
    }

    @GetMapping("/all")
    public ModelAndView getAllReservations() {
        ModelAndView modelAndView = new ModelAndView("view_all_reservations");
        List<Reservation> reservations;
	    
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String username = authentication.getName();
	    User user = userService.getUserByUsername(username);
	    
	    if(user.getRole() == UserRole.CUSTOMER) {
	    	reservations = user.getReservations();
	    } else if(user.getRole() == UserRole.AGENCY_STAFF) {
	    	List<Car> fleet = user.getFleet();

	        List<Reservation> allReservations = new ArrayList<>();
	        
	        for (Car car : fleet) {
	            allReservations.addAll(car.getReservations());
	        }

	    	reservations = allReservations;
	    } else {
	    	reservations = reservationService.getAllReservations();
	    }
        
        modelAndView.addObject("reservations", reservations);
        return modelAndView;
    }

    @GetMapping("/byUser/{userID}")
    public ModelAndView getReservationsByUser(@PathVariable int userID) {
        ModelAndView modelAndView = new ModelAndView("view_reservations_by_user");
        List<Reservation> reservations = reservationService.getReservationsByUser(userID);
        modelAndView.addObject("reservations", reservations);
        return modelAndView;
    }

    @GetMapping("/byCar/{carID}")
    public ModelAndView getReservationsByCar(@PathVariable int carID) {
        ModelAndView modelAndView = new ModelAndView("view_reservations_by_car");
        List<Reservation> reservations = reservationService.getReservationsByCar(carID);
        modelAndView.addObject("reservations", reservations);
        return modelAndView;
    }
}