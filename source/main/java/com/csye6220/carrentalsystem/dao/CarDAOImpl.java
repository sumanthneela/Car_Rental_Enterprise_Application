package com.csye6220.carrentalsystem.dao;

import java.util.ArrayList; 
import java.util.Collections;
import java.util.List; 

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.csye6220.carrentalsystem.model.Car;
import com.csye6220.carrentalsystem.model.CarType;
import com.csye6220.carrentalsystem.model.Location;
import com.csye6220.carrentalsystem.model.Reservation;
import com.csye6220.carrentalsystem.model.User;
import com.csye6220.carrentalsystem.util.HibernateUtil;


@Repository
public class CarDAOImpl implements CarDAO {

	private final SessionFactory sessionFactory;

    public CarDAOImpl() {
        this.sessionFactory = HibernateUtil.buildSessionFactory();
    }

	@Override
	public void createCar(Car car) {
		Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(car);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
	}

	@Override
	public Car getCarByID(int carID) {
		try(Session session = sessionFactory.openSession()){
            Car car= session.get(Car.class, carID);
            return car;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
	}

	@Override
	public void update(Car car) {
		try(Session session = sessionFactory.openSession()){
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.merge(car);
            transaction.commit();
        }
        catch (Exception e){
            e.printStackTrace();
        }
	}

	@Override
	public void delete(Car car) {
		try(Session session = sessionFactory.openSession()){
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.remove(car);
            transaction.commit();
        }
        catch (Exception e){
            e.printStackTrace();
        }
	}

	@Override
	public List<Car> getAllCars() {
		try(Session session = sessionFactory.openSession()){
			return session.createQuery("from Car",Car.class)
						.list();
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
	}

	@Override
	public List<Car> getCarsByLocation(Location location) {
		try(Session session = sessionFactory.openSession()){
            return session.createQuery ("FROM Car c WHERE c.location = :location", Car.class)
            			.setParameter("location", location)
            			.list();
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
	}

	@Override
	public List<Car> getCarsByAvailablity(boolean availability) {
	    try (Session session = sessionFactory.openSession()) {
	        return session.createQuery("FROM Car c WHERE c.availability = :availability", Car.class)
	                      .setParameter("availability", availability)
	                      .list();
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}

	@Override
	public List<Car> getCarsByCarType(CarType carType) {
		try(Session session = sessionFactory.openSession()){
            return session.createQuery ("FROM Car c WHERE c.carType = :carType", Car.class)
            			.setParameter("carType", carType)
            			.list();
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
	}

}