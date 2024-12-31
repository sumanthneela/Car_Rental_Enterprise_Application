package com.csye6220.carrentalsystem.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "cars")
public class Car {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="car_id")
    private int carID;
	
	@Column(name="car_make")
    private String carMake;
	
	@Column(name="car_model")
    private String carModel;

	@Column(name="car_year")
    private int carYear;

	@Enumerated(EnumType.STRING)
    private CarType carType;
	
	@Column(name="registration_number")
    private String registrationNumber;
	
    private boolean availability;
	
    @Enumerated(EnumType.STRING)
    private Location location;
    
    @OneToMany(mappedBy = "car", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<Reservation> reservations;
    
    public Car () {}  
    
    public Car(String carMake, String carModel, int carYear, CarType carType, String registrationNumber, boolean availability, Location location) {
		this.carMake = carMake;
		this.carModel = carModel;
		this.carYear = carYear;
		this.carType = carType;
		this.registrationNumber = registrationNumber;
		this.availability = availability;
		this.location = location;
	}

    public int getCarID() {
        return carID;
    }

    public void setCarID(int carID) {
        this.carID = carID;
    }

    public String getCarMake() {
        return carMake;
    }

    public void setCarMake(String carMake) {
        this.carMake = carMake;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public int getCarYear() {
        return carYear;
    }

    public void setCarYear(int carYear) {
        this.carYear = carYear;
    }

    public CarType getCarType() {
        return carType;
    }

    public void setCarType(CarType carType) {
        this.carType = carType;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}  
	
	public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
	
	
    
}
