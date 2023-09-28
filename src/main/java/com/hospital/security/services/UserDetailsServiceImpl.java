package com.hospital.security.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hospital.exceptions.UserNameNotFoundException;
import com.hospital.repository.HospitalRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	HospitalRepository HospitalRepository;
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UserNameNotFoundException {
		var hospital = HospitalRepository.findByUserName(username)
				.orElseThrow(() -> new UserNameNotFoundException("User Not Found with username: " + username));
		return UserDetailsImpl.build(hospital);
	}
}
