package com.mycompany.p2;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mycompany.p2.reservation.ReservationEntity;
import com.mycompany.p2.reservation.ReservationRepository;


@SpringBootTest
public class TestController {
	
	@Autowired
	private ReservationRepository reservationRepository;
	
	
	@Test
	@DisplayName("병원 예약하기")
	public void reserve() {
		ReservationEntity r = new ReservationEntity();
		r.setOwnername("오너");
		r.setPetname("펫이름");
		r.setResdate(LocalDateTime.now());
		r.setRestime("11:00");
		r.setSymptom("아파요");
		reservationRepository.save(r);
	}
	
	@Test
	@DisplayName("모든 예약 가져오기")
	void getReserv() {
		List<ReservationEntity> list = reservationRepository.findAll();
		list.get(0);
		
	}
	
}
