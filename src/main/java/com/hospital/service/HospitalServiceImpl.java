package com.hospital.service;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.hospital.entities.Hospital;
import com.hospital.exceptions.HospitalDetailsNotFoundException;
import com.hospital.exceptions.HospitalIdMismatchException;
import com.hospital.exceptions.UserNameNotFoundException;
import com.hospital.repository.HospitalRepository;

import lombok.extern.slf4j.Slf4j;

@Service("hospitalService")
@Slf4j
public class HospitalServiceImpl implements HospitalService{

	@Autowired
	private HospitalRepository hospitalRepository;
	BCryptPasswordEncoder bcrypt=new BCryptPasswordEncoder();

	public HospitalRepository getHospitalRepository() {
		return hospitalRepository;
	}
	public void setHospitalRepository(HospitalRepository hospitalRepository) {
		this.hospitalRepository = hospitalRepository;
	}

	//change password
	@Override
	public String changePassword(String userName, String oldPassword, String newPassword) {
		Optional<Hospital> opcust= hospitalRepository.findByUserName(userName);
		if(opcust.isPresent()){
			Hospital dbcust=opcust.get();
			if(bcrypt.matches(oldPassword,dbcust.getPassword())){
				String encrypted= bcrypt.encode(newPassword);
				dbcust.setPassword(encrypted);
				hospitalRepository.save(dbcust);
				log.info("password changed successfully");
				return "password changed successfully";
			}
			else {
				log.info("password incorrect");
				return "password incorrect";
			}
		}
		log.error("userName not found"+userName);
		throw new UserNameNotFoundException("User Name not found");
	}

	//view all hsptls
	public List<Hospital> viewAllHospitals() {
		log.info("Inside hospital service viewAllHospitals()");
		return hospitalRepository.findAll();
	}

	// view hsptl by id
	public Hospital viewHospitalById(int id) {
		Optional<Hospital> h = hospitalRepository.findById(id);
		if(h.isPresent()) {
			log.info("Inside hospital service viewHospitalById()");
			return h.get();
		}
		else {
			log.error("Hospital details not found with hospitalId"+id);
			throw new HospitalDetailsNotFoundException("Hospital details not found with hospitalId:"+id);
		}
	}

	//deletehospital
	@Override
	public void removeHospitalById(int hospitalId) {

		if(hospitalRepository.existsById(hospitalId)) {
			log.info("hospital deleted successfully");
			hospitalRepository.deleteById(hospitalId);
			
		}

		else {
			log.error("hospital id doesnot exists");

		}
		throw new HospitalIdMismatchException("To remove user : no user is Found with id:"+hospitalId);
	}

	//updatehospital
	@Override
	public Hospital updateHospital(int hospitalId, Hospital hospital) {
		if (hospitalRepository.existsById(hospitalId)) {
			log.info("hospitalId updated successfully");
			return hospitalRepository.save(hospital);
			
		} else {
			log.info("Hospital details not found with hospitalId " + hospitalId);
			throw new HospitalIdMismatchException("update hospital with Id " + hospitalId + " not found");
		}
	}
}