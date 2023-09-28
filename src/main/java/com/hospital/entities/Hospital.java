package com.hospital.entities;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor


public class Hospital {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	private int hospitalId;
	private String userName;
	@Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[#?!@$%^&*-]).{8,}$", message = "Password must be at least 8 characters with a special character, upper case, and lower case combination")
	@NotNull(message = "Please enter a password")
	@NotBlank(message = "Password cannot be null")
	private String password;
	@Email
	private String email;
	@Pattern(regexp="[0-9]{10}$",message="phone Number should be 10 digits")
	private String phNo;
	private String hospitalName;
	private String hospitalLocation;
	public Hospital(int hospitalId, String userName,@Pattern(regexp ="", message = "Password^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[#?!@$%^&*-]).{8,}$ must be at least 8 characters with a special character, upper case, and lower case combination")
	@NotNull(message = "Please enter a password") @NotBlank(message = "Password cannot be null")String password, String email, String phNo, String hospitalName,
	String hospitalLocation) {

		this.hospitalId = hospitalId;
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.phNo = phNo;
		this.hospitalName = hospitalName;
		this.hospitalLocation = hospitalLocation;
	} 

}




