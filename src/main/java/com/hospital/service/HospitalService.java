package com.hospital.service;

import java.util.List;
import com.hospital.entities.Hospital;


public interface HospitalService {

	String changePassword(String userName, String oldPassword, String newPassword);
	public Hospital updateHospital(int hospitalId,Hospital hospital);
	Hospital viewHospitalById(int id);
	public void removeHospitalById(int userId);
	List<Hospital> viewAllHospitals();

}
