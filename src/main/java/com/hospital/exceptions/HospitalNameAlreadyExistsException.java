package com.hospital.exceptions;

public class HospitalNameAlreadyExistsException extends RuntimeException {
	public HospitalNameAlreadyExistsException(String msg)
	{
		super(msg);
	}

}
