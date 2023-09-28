package com.hospital.response;

public class JwtResponse {
	private String token;
	private int hospitalId;
	private String userName;
	private String password;
	private String email;
	private String phNo;
	private String hospitalName;
	private String hospitalLocation;


	public JwtResponse(String token, int hospitalId, String userName, String password, String email,
			String phNo, String hospitalName, String hospitalLocation) {

		this.token = token;
		this.hospitalId = hospitalId;
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.phNo = phNo;
		this.hospitalName = hospitalName;
		this.hospitalLocation = hospitalLocation;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	public String getAccessToken() {

		return "Bearer " + token; // You can customize the format as needed.

	}
	public void setAccessToken(String accessToken) {

		this.token = accessToken;

	}
	
	public int getHospitalId() {
		return hospitalId;
	}
	public void setHospitalId(int hospitalId) {
		this.hospitalId = hospitalId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhNo() {
		return phNo;
	}
	public void setPhone(String phNo) {
		this.phNo = phNo;
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



}