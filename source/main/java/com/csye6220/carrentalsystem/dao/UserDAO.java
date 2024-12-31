package com.csye6220.carrentalsystem.dao;

import com.csye6220.carrentalsystem.model.Car;
import com.csye6220.carrentalsystem.model.CarType;
import com.csye6220.carrentalsystem.model.Location;
import com.csye6220.carrentalsystem.model.Reservation;
import com.csye6220.carrentalsystem.model.User;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserDAO {

    void createUser(User user);
    User getUserByID(int userID);
    User getUserByEmail(String email);
    User getUserByUsername(String username);
    void update(User user);
    void delete(int userID);

    List<User> getAllUsers();
	List<Car> getCarsByLocation(Location location);
	List<Car> getCarsByCarType(CarType carType);
	List<User> getAllCustomers();
	List<User> getAllAgencies();

}
