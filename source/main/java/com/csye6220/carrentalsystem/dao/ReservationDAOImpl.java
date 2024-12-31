package com.csye6220.carrentalsystem.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.csye6220.carrentalsystem.model.Car;
import com.csye6220.carrentalsystem.model.Reservation;
import com.csye6220.carrentalsystem.util.HibernateUtil;

@Repository
public class ReservationDAOImpl implements ReservationDAO {
	
	private SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();

	@Override
	public void createReservation(Reservation reservation) {
		try(Session session = sessionFactory.openSession()){
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.merge(reservation);
            transaction.commit();
        }
        catch (Exception e){
            e.printStackTrace();
        }
	}

	@Override
	public Reservation getReservationById(int reservationID) {
		try(Session session = sessionFactory.openSession()){
			Reservation reservation = session.get(Reservation.class, reservationID);
            return reservation;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
	}

	@Override
	public void updateReservation(Reservation reservation) {
		try(Session session = sessionFactory.openSession()){
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.merge(reservation);
            transaction.commit();
        }
        catch (Exception e){
            e.printStackTrace();
        }
	}

	@Override
	public void deleteReservation(int reservationID) {
		try(Session session = sessionFactory.openSession()){
            Transaction transaction = session.getTransaction();
            transaction.begin();
            Reservation reservation = getReservationById(reservationID);
            session.remove(reservation);
            transaction.commit();
        }
        catch (Exception e){
            e.printStackTrace();
        }
	}

	@Override
	public List<Reservation> getAllReservations() {
		try(Session session = sessionFactory.openSession()){
            List<Reservation> boardList = session.createQuery("from Reservation", Reservation.class).list();
            return boardList;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
	}

	@Override
	public List<Reservation> getReservationsByUser(int userID) {
		try(Session session = sessionFactory.openSession()){
            return session.createQuery("FROM Reservation WHERE user.userID = :userID", Reservation.class)
            				.setParameter("userID", userID)
            				.list();
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
	}
	
	@Override
	public List<Reservation> getReservationsByCar(int carID) {
		try(Session session = sessionFactory.openSession()){
			return session.createQuery("FROM Reservation WHERE car.carID = :carID", Reservation.class)
    				.setParameter("carID", carID)
    				.list();
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
	}

}
