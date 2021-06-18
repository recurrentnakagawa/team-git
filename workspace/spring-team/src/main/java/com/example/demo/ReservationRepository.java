package com.example.demo;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

	List<Reservation> findByClientCode(Integer clientCode);

	List<Reservation> findByClientCodeAndReservationInvalidAndReservationCheckinDateLessThan(Integer clientCode,
			String string, Date date);

	List<Reservation> findByClientCodeAndReservationInvalidAndReservationCheckinDateGreaterThan(Integer clientCode,
			String string, Date date);
	
}
