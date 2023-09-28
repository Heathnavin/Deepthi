package com.hospital.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.dtos.LoginDto;
import com.hospital.entities.Hospital;
import com.hospital.repository.HospitalRepository;
import com.hospital.response.JwtResponse;
import com.hospital.response.MessageResponse;
import com.hospital.security.jwt.JwtUtils;
import com.hospital.security.services.UserDetailsImpl;


@CrossOrigin("*")
@RestController
@RequestMapping("/Users/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	HospitalRepository hospitalRepository;
	@Autowired
	PasswordEncoder encoder;
	@Autowired
	JwtUtils jwtUtils;
	@PostMapping("/signin")
	public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginDto loginDto) {
		var authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginDto.getUserName(), loginDto.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		return ResponseEntity.ok(new JwtResponse(jwt,
				userDetails.getHospitalId(), 
				userDetails.getUserName(), 
				userDetails.getPassword(),
				userDetails.getEmail(), 
				userDetails.getPhNo(),                                                
				userDetails.getHospitalName(),
				userDetails.getHospitalLocation()
				));
	}
	@PostMapping("/signup")
	public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody Hospital signUpRequest) {
		if (hospitalRepository.existsByUserName(signUpRequest.getUserName())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}
		if (hospitalRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}

		var hospital = new Hospital( signUpRequest.getHospitalId(),signUpRequest.getUserName(), encoder.encode(signUpRequest.getPassword()), signUpRequest.getEmail(), signUpRequest.getPhNo(), signUpRequest.getHospitalName(),signUpRequest.getHospitalLocation());


		hospitalRepository.save(hospital);
		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
}

