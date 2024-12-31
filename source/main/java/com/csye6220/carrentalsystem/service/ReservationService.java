package com.csye6220.carrentalsystem.service;

import java.util.List; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csye6220.carrentalsystem.dao.CarDAO;
import com.csye6220.carrentalsystem.dao.ReservationDAO;
import com.csye6220.carrentalsystem.dao.UserDAO;
import com.csye6220.carrentalsystem.dao.UserDAOImpl;
import com.csye6220.carrentalsystem.model.Car;
import com.csye6220.carrentalsystem.model.Reservation;
import com.csye6220.carrentalsystem.model.User;

@Service
public class ReservationService {
	
	@Autowired
	private ReservationDAO reservationDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private CarDAO carDAO;

	public void createReservation(Reservation reservation) {
		reservationDAO.createReservation(reservation);
	}

	public Reservation getReservationById(int reservationID) {
		return reservationDAO.getReservationById(reservationID);
	}
	
	public void updateReservation(Reservation reservation) {
		reservationDAO.updateReservation(reservation);
		
	}

	public void deleteReservation(int reservationID) {
		reservationDAO.deleteReservation(reservationID);		
	}

	public List<Reservation> getAllReservations() {
		return reservationDAO.getAllReservations();
	}

	public List<Reservation> getReservationsByUser(int userID) {
		return reservationDAO.getAllReservations();
	}

	public List<Reservation> getReservationsByCar(int carID) {
		return reservationDAO.getAllReservations();
	}
	
	
	
//	public void linkUserToReservation(User user, Reservation reservation) {
//        reservation.setUser(user);
//        user.getReservations().add(reservation);
//        userDAO.update(user);
//    }
//
//    public void linkCarToReservation(Car car, Reservation reservation) {
//        reservation.setCar(car);
//        car.getReservations().add(reservation);
//        carDAO.update(car);
//    }
//
//    public void unlinkUserFromReservation(User user, Reservation reservation) {
//        reservation.setUser(null);
//        user.getReservations().remove(reservation);
//        userDAO.update(user);
//    }
//
//    public void unlinkCarFromReservation(Car car, Reservation reservation) {
//        reservation.setCar(null);
//        car.getReservations().remove(reservation);
//        carDAO.update(car);
//    }

}
