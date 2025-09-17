package com.mycompany.p2.reservation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/hospital")
public class ReservationController {
	
	@Autowired
	private ReservationService reservationService;
	
	// 병원예약 폼
	@GetMapping(value = "/reserve")
	public String reserve() {
		return "reservation";
	}
	
	// 병원예약하기
	@PostMapping(value = "/reserve")
	public String reserve(@Valid ReservationValid reservationValid, BindingResult result, Model model) {
		System.out.println("에러결과 : " + result);
		if (result.hasErrors()) {
			return "reservation";
		}
		reservationService.hospitalReserve(reservationValid.getOwnername(), reservationValid.getPetname(), 
				reservationValid.getResdate(), reservationValid.getRestime(), reservationValid.getSymptom());
		return "redirect:/hospital/list";
	}
	
	// 예약확인 목록
	@GetMapping(value = "/list")
	public String list(Model model) {
		List<ReservationEntity> reservations = reservationService.getReserves();
		model.addAttribute("reservations", reservations);
		return "reservationList";
	}
	
	

}
