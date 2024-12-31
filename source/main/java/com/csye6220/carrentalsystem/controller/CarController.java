package com.csye6220.carrentalsystem.controller;

import java.util.List; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import com.csye6220.carrentalsystem.model.Car;
import com.csye6220.carrentalsystem.model.CarType;
import com.csye6220.carrentalsystem.model.Location;
import com.csye6220.carrentalsystem.model.Reservation;
import com.csye6220.carrentalsystem.model.User;
import com.csye6220.carrentalsystem.model.UserRole;
import com.csye6220.carrentalsystem.service.CarService;
import com.csye6220.carrentalsystem.service.UserService;



@Controller
@RequestMapping("/cars")
public class CarController {
	 
	@Autowired
	private CarService carService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/add") 
	public String getCarAddForm() {
		return "add_car";
	}
	
	@PostMapping("/add")
    public String addCar(
            @RequestParam(name = "car-make", required = false) String carMake,
            @RequestParam(name = "car-model", required = false) String carModel,
            @RequestParam(name = "car-year", required = false) int carYear,
            @RequestParam(name = "car-type", required = false) String carType,
            @RequestParam(name = "registration-number", required = false) String registrationNumber,
            @RequestParam(name = "availability", required = false) boolean availability,
            @RequestParam(name = "current-location", required = false) String location
    ) {
        Car car = new Car(carMake, carModel, carYear, CarType.valueOf(carType), registrationNumber, availability, Location.valueOf(location));
        carService.createCar(car);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String username = authentication.getName();
	    User user = userService.getUserByUsername(username);
	    user.getFleet().add(car);
	    userService.update(user);
        return "redirect:/cars/all";
    }
 
    @GetMapping("/{carID}")
    public ModelAndView getCarByID(@PathVariable int carID) {
        ModelAndView modelAndView = new ModelAndView("view_car");
        Car car = null;
		try {
			car = carService.getCarByID(carID);
		} catch (Exception e) {
			e.printStackTrace();
		}
        modelAndView.addObject("car", car);
        return modelAndView;
    }
     
    @GetMapping("/edit/{carID}") 
    public String editCarForm(@PathVariable int carID, Model model) {
        Car car = carService.getCarByID(carID);
        model.addAttribute("carId", carID);
        model.addAttribute("car", car);
        return "edit_car_content";
    }

    @PostMapping("/edit")
    public String editCar(
    		@RequestParam(name = "car-id") int carId,
            @RequestParam(name = "car-make", required = false) String carMake,
            @RequestParam(name = "car-model", required = false) String carModel,
            @RequestParam(name = "car-year", required = false) int carYear,
            @RequestParam(name = "car-type", required = false) String carType,
            @RequestParam(name = "registration-number", required = false) String registrationNumber,
            @RequestParam(name = "availability", required = false) boolean availability,
            @RequestParam(name = "current-location", required = false) String location,
            RedirectAttributes redirectAttributes
    ) {
        Car car = new Car(carMake, carModel, carYear, CarType.valueOf(carType), registrationNumber, availability, Location.valueOf(location));
        car.setCarID(carId);
        carService.update(car);
        return "redirect:/cars/all";
    }

    @GetMapping("/delete/{carID}")
    public String deleteCar(@PathVariable int carID) {

	    for (User user : userService.getAllAgencies()) {
            List<Car> fleet = user.getFleet();
            fleet.removeIf(cars -> cars.getCarID() == carID);
            user.setFleet(fleet);
       	 	userService.update(user);
        }	    
        Car car = carService.getCarByID(carID);
        carService.delete(car);
        return "redirect:/cars/all";
    }

    @GetMapping("/all")
    public ModelAndView getAllCars(@ModelAttribute("locations") Location[] locations) {
        ModelAndView modelAndView = new ModelAndView("view_all_cars");
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String username = authentication.getName();
	    User user = userService.getUserByUsername(username);
	    
        List<Car> cars;
        
	    if(user.getRole() == UserRole.AGENCY_STAFF) {
	    	cars = user.getFleet();
	    } else{
	    	cars = carService.getAllCars();
	    }

        modelAndView.addObject("role", user.getRole().name());
        modelAndView.addObject("locations", locations);
        modelAndView.addObject("filteredCars", cars);
        return modelAndView;
    }
    
    @ModelAttribute("carTypes")
    public CarType[] populateCarTypes() {
        return CarType.values();
    }
    
    @GetMapping("/byCarType")
    public String getCarsByCarType(@RequestParam(required = false) CarType carType, Model model) {
        List<Car> filteredCars;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String username = authentication.getName();
	    User user = userService.getUserByUsername(username);
        if(user.getRole() == UserRole.AGENCY_STAFF) {
	    	 if (carType != null) {
	             filteredCars = userService.getCarsByCarType(carType);
	         } else {
	             filteredCars = user.getFleet();
	         }
	    } else{
	    	if (carType != null) {
	            filteredCars = carService.getCarsByCarType(carType);
	        } else {
	            filteredCars = carService.getAllCars();
	        }
	    }
        model.addAttribute("role", user.getRole().name());
        model.addAttribute("filteredCars", filteredCars);
        return "view_all_cars";
    }
    
    @ModelAttribute("locations")
    public Location[] populateLocations() {
        return Location.values();
    }
    
    @GetMapping("/byLocation")
    public String getCarsByLocation(@RequestParam(required = false) Location location, Model model) {
        List<Car> filteredCars;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String username = authentication.getName();
	    User user = userService.getUserByUsername(username);
	    
	    if(user.getRole() == UserRole.AGENCY_STAFF) {
	    	 if (location != null) {
		            filteredCars = userService.getCarsByLocation(location);
		        } else {
		            filteredCars = user.getFleet();
		        }
	    } else{
	    	if (location != null) {
	            filteredCars = carService.getCarsByLocation(location);
	        } else {
	            filteredCars = carService.getAllCars();
	        }
	    }
	    model.addAttribute("role", user.getRole().name());
        model.addAttribute("filteredCars", filteredCars);
        return "view_all_cars";
    }  

}
