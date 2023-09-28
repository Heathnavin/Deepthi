package com.hospital.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.dtos.HospitalDto;
import com.hospital.entities.Hospital;
import com.hospital.exceptions.HospitalIdMismatchException;
import com.hospital.service.HospitalServiceImpl;
@CrossOrigin("*")
@RestController
@RequestMapping("/users")
public class HospitalController {
	@Autowired

	private HospitalServiceImpl hospitalServiceImpl;
	public HospitalServiceImpl getHospitalServiceImpl() {
		return hospitalServiceImpl;
	}

	public void setCustomerServiceImpll(HospitalServiceImpl hospitalServiceImpl) {
		this.hospitalServiceImpl = hospitalServiceImpl;
	}
	

	@PostMapping("/changePassword")

	public ResponseEntity<String> changePassword(@Valid @RequestBody HospitalDto hospitalDto) {

		String s = hospitalServiceImpl.changePassword(hospitalDto.getUserName(), hospitalDto.getOldPassword(),

				hospitalDto.getNewPassword());
		return new ResponseEntity<>(s, HttpStatus.ACCEPTED);

	}

	@PutMapping("/updateHospital/{hospitalId}")

	public Hospital updateHospital (@PathVariable("hospitalId")int hospitalId, @RequestBody Hospital hospital) {

		 return hospitalServiceImpl.updateHospital(hospitalId, hospital);

		
	}

	@GetMapping("/viewHospitalById/{hospitalId}")

	public Hospital viewHospitalById(@PathVariable("hospitalId") int id) {

		return hospitalServiceImpl.viewHospitalById(id);


	}

	@GetMapping("/viewAllHospitals")

	public List<Hospital> viewAllHospitals() {

		return hospitalServiceImpl.viewAllHospitals();

	}

	@DeleteMapping("/remove/{hospitalId}")

	@ExceptionHandler(HospitalIdMismatchException.class)

	public void removeHospitalById(@PathVariable int hospitalId) {

		hospitalServiceImpl.removeHospitalById(hospitalId);

	}

}








