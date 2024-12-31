package com.csye6220.carrentalsystem.dao;

import java.util.List;

import com.csye6220.carrentalsystem.model.Reservation;

public interface ReservationDAO {
	void createReservation(Reservation reservation);
	Reservation getReservationById(int reservationID);
	void updateReservation(Reservation reservation);
	void deleteReservation(int reservationID);

	List<Reservation> getAllReservations();
	List<Reservation> getReservationsByUser(int userID);
	List<Reservation> getReservationsByCar(int carID);
}
