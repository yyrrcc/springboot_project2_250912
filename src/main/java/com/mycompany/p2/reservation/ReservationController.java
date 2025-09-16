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
	public String reserve(@Valid ReservationEntity reservationEntity, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "reservation";
		}
		reservationService.hospitalReserve(reservationEntity.getOwnername(), reservationEntity.getPetname(), 
				reservationEntity.getResdate(), reservationEntity.getRestime(), reservationEntity.getSymptom());
		return "redirect:/";
	}
	
	// 병원예약 폼
	@GetMapping(value = "/list")
	public String list(Model model) {
		List<ReservationEntity> reservations = reservationService.getReserves();
		model.addAttribute("reservations", reservations);
		return "reservationList";
	}
	
	

}
