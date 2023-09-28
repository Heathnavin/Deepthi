package com.hospital.security.services;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hospital.entities.Hospital;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
public class UserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 1L;
	private int hospitalId;
	private String userName;
	@JsonIgnore
	private String password;
	private String email;
	private String phNo;
	private String hospitalName;
	private String hospitalLocation;


	private List <GrantedAuthority> authorities;

	public UserDetailsImpl(int hospitalId, String userName, String password, String email, String phNo,
			String hospitalName, String hospitalLocation) {

		this.hospitalId = hospitalId;
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.phNo = phNo;
		this.hospitalName = hospitalName;
		this.hospitalLocation = hospitalLocation;
	}
	public static UserDetailsImpl build(Hospital hospital) {


		return new UserDetailsImpl(
				hospital.getHospitalId(),
				hospital.getUserName(),
				hospital.getPassword(),
				hospital.getEmail(),
				hospital.getPhNo(),
				hospital.getHospitalName(),
				hospital.getHospitalLocation());			

	}
	public String getPhNo() {
		return phNo;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public int getHospitalId() {
		return hospitalId;
	}
	public void setHospitalId(int hospitalId) {
		this.hospitalId = hospitalId;
	}
	public String getHospitalName() {
		return hospitalName;
	}
	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}
	public String getHospitalLocation() {
		return hospitalLocation;
	}
	public void setHospitalLocation(String hospitalLocation) {
		this.hospitalLocation = hospitalLocation;
	}
	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}
	public String getUserName() {
		return userName;
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override

	public boolean isEnabled() {
		return true;
	}
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserDetailsImpl hospital = (UserDetailsImpl) o;
		return Objects.equals(hospitalId, hospital.hospitalId);
	}
	@Override
	public int hashCode() {
		return Objects.hash(hospitalId);
	}
	@Override
	public String getUsername() {
		return null;
	}
	


}