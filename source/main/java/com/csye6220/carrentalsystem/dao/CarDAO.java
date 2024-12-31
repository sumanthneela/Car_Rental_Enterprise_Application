package com.csye6220.carrentalsystem.dao;

import com.csye6220.carrentalsystem.model.Car;
import com.csye6220.carrentalsystem.model.CarType;
import com.csye6220.carrentalsystem.model.Location;
import com.csye6220.carrentalsystem.model.Reservation;

import java.util.List;

public interface CarDAO {
	void createCar(Car car);
	Car getCarByID(int carID);
	void update(Car car);
	void delete(Car car);
	
	List<Car> getAllCars();
	List<Car> getCarsByCarType(CarType carType);
	List<Car> getCarsByLocation(Location location);
	List<Car> getCarsByAvailablity(boolean availability);
    
}