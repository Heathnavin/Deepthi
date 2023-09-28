package com.hospital.exceptions;

public class HospitalDetailsNotFoundException extends RuntimeException{
	public HospitalDetailsNotFoundException(String msg)
	{
		super(msg);
	}

}
