package com.csye6220.carrentalsystem.service;

import java.util.Date;
import java.util.List;  

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.csye6220.carrentalsystem.dao.CarDAO;
import com.csye6220.carrentalsystem.dao.ReservationDAO;
import com.csye6220.carrentalsystem.model.Car;
import com.csye6220.carrentalsystem.model.CarType;
import com.csye6220.carrentalsystem.model.Location;
import com.csye6220.carrentalsystem.model.Reservation;

@Service
public class CarService {
	
	@Autowired
	private CarDAO carDAO;
	
	@Autowired
	private ReservationDAO reservationDAO;

	public void createCar(Car car) {
		carDAO.createCar(car);
	}

	public Car getCarByID(int carID) {
		return carDAO.getCarByID(carID);
	}

	public void update(Car car) {
		carDAO.update(car);
	}

	public void delete(Car car) {
		carDAO.delete(car);
	}

	public List<Car> getAllCars() {
		return carDAO.getAllCars();
	}

	public List<Car> getCarsByLocation(Location location) {
		return carDAO.getCarsByLocation(location);
	}

	public List<Car> getCarsByAvailablity(boolean availability) {
		return carDAO.getCarsByAvailablity(availability);
	}
	
	public List<Car> getCarsByCarType(CarType carType){
		return carDAO.getCarsByCarType(carType);
	}
	
	public boolean isCarAvailableForReservation(int carID, Date startDate, Date endDate) {
        // Retrieve the car by its ID
        Car car = carDAO.getCarByID(carID);

        // Check if the car exists
        if (car == null) {
            return false;
        }

        // Get reservations for the specified car
        List<Reservation> reservations = reservationDAO.getReservationsByCar(carID);

        // Check if there are any overlapping reservations
        for (Reservation reservation : reservations) {
            Date existingStartDate = reservation.getStartDate();
            Date existingEndDate = reservation.getEndDate();

            // Check for overlap
            if (startDate.before(existingEndDate) && endDate.after(existingStartDate)) {
                // Overlap found, the car is not available
                return false;
            }
        }
        
        // The car is available for reservation
        return true;
    }

	
}
