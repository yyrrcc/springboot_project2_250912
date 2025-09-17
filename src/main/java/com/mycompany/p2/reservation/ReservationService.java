package com.mycompany.p2.reservation;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
	
	@Autowired
	private ReservationRepository reservationRepository;
	
	// 병원 예약하기
	public void hospitalReserve(String ownername, String petname, LocalDate resdate, String restime, String symptom) {
		ReservationEntity reservation = new ReservationEntity();
		reservation.setOwnername(ownername);
		reservation.setPetname(petname);
		reservation.setResdate(resdate);
		reservation.setRestime(restime);
		reservation.setSymptom(symptom);
		reservationRepository.save(reservation);
	}
	
	// 모든 예약 확인하기
	public List<ReservationEntity> getReserves() {
		return reservationRepository.findAll();
	}


}
