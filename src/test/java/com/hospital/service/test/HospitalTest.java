package com.hospital.service.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.hospital.entities.Hospital;
import com.hospital.exceptions.HospitalDetailsNotFoundException;
import com.hospital.exceptions.HospitalIdMismatchException;
import com.hospital.exceptions.UserNameNotFoundException;
import com.hospital.repository.HospitalRepository;
import com.hospital.service.HospitalServiceImpl;

class HospitalTest {

	HospitalServiceImpl hospitalserviceimpl;
	HospitalRepository hospitalRepository;

	List<Hospital> l;
	Hospital h;
	BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

	@BeforeEach

	public void dummy() {

		hospitalserviceimpl=new HospitalServiceImpl();

		h=new Hospital();
		h.setHospitalId(1);
		h.setUserName("deepthi");
		h.setPassword("Deepthi@27");
		h.setEmail("deepthi@gmail.com");
		h.setPhNo("9381300410");
		h.setHospitalName("Apollo");
		h.setHospitalLocation("hyd");

		Hospital h1=new Hospital();
		h1.setHospitalId(2);
		h1.setUserName("preethi");
		h1.setPassword("preethi@27");
		h1.setEmail("preethi@gmail.com");
		h1.setPhNo("9381399410");
		h1.setHospitalName("Apollo");
		h1.setHospitalLocation("blr");

		l=new ArrayList<Hospital>();
		l.add(h);
		l.add(h1);
	}

	@Test
	void testviewAllHospital() 
	{
		List<Hospital> h = new ArrayList<>(l);
		HospitalRepository hrMock = Mockito.mock(HospitalRepository.class);
		hospitalserviceimpl.setHospitalRepository(hrMock); 
		Mockito.when(hrMock.findAll()).thenReturn(l);
		List<Hospital> actual = hospitalserviceimpl.viewAllHospitals();
		assertEquals(h, actual);
		Mockito.verify(hrMock, Mockito.times(1)).findAll();
	}

	@Test
	void testUpdateHospital() {
		// Create a sample Hospital object to update
		Hospital h = new Hospital();
		h.setHospitalId(1);
		h.setUserName("deepthi");
		h.setPassword("Deepthi@27");
		h.setEmail("deepthi@gmail.com");
		h.setPhNo("9381399410");
		h.setHospitalName("Apollo");
		h.setHospitalLocation("hyd");

		// Mock the HospitalRepository
		HospitalRepository hrMock = Mockito.mock(HospitalRepository.class);
		hospitalserviceimpl.setHospitalRepository(hrMock);

		// Set up the mock to return true for existsById (hospital with ID exists)
		Mockito.when(hrMock.existsById(1)).thenReturn(true);

		// Set up the mock to return the saved Hospital object
		Mockito.when(hrMock.save(h)).thenReturn(h);

		// Call the updateHospital method
		Hospital result = hospitalserviceimpl.updateHospital(1, h);

		// Verify that the method returns "Updated successfully"
		//assertEquals("Updated successfully", result);

		// Verify that existsById and save methods were called with the correct parameters
		//Mockito.verify(hrMock, Mockito.times(1)).existsById(1);
		Mockito.verify(hrMock, Mockito.times(1)).save(h);
	}

	@Test
	void testUpdateHospitalNotFound() {
		// Mock the HospitalRepository to return false for existsById (hospital with ID does not exist)
		HospitalRepository hrMock = Mockito.mock(HospitalRepository.class);
		hospitalserviceimpl.setHospitalRepository(hrMock);
		Mockito.when(hrMock.existsById(1)).thenReturn(false);

		// Create a sample Hospital object for the update
		Hospital h = new Hospital();
		h.setHospitalId(1);

		// Call the updateHospital method with an ID that does not exist
		try {
			hospitalserviceimpl.updateHospital(1, h);
			fail("Expected HospitalIdMismatchException, but no exception was thrown");
		} catch (HospitalIdMismatchException e) {
			// Verify that the exception message matches the expected message
			assertEquals("update hospital with Id " + h.getHospitalId() + " not found", e.getMessage());
		}

		// Verify that existsById method was called with the correct ID
		Mockito.verify(hrMock, Mockito.times(1)).existsById(1);
	}
	@Test
	void testViewHospitalById() {
		// Mock the HospitalRepository
		HospitalRepository hrMock = Mockito.mock(HospitalRepository.class);
		hospitalserviceimpl.setHospitalRepository(hrMock);

		// Create a sample Hospital object
		Hospital h = new Hospital();
		h.setHospitalId(1);
		h.setHospitalName("Sample Hospital");

		// Set up the mock to return the Hospital object when findById is called
		when(hrMock.findById(1)).thenReturn(Optional.of(h));

		// Call the viewHospitalById method
		Hospital result = hospitalserviceimpl.viewHospitalById(1);

		// Verify that the method returns the expected Hospital object
		assertEquals(h, result);

		// Verify that findById method was called with the correct ID
		Mockito.verify(hrMock, Mockito.times(1)).findById(1);
	}

	@Test
	void testViewHospitalById_HospitalNotFound() {
		// Mock the HospitalRepository to return an empty optional (hospital not found)
		HospitalRepository hrMock = Mockito.mock(HospitalRepository.class);
		hospitalserviceimpl.setHospitalRepository(hrMock);

		// Set up the mock to return an empty optional when findById is called
		when(hrMock.findById(1)).thenReturn(Optional.empty());

		// Call the viewHospitalById method with an ID that does not exist
		HospitalDetailsNotFoundException exception = assertThrows(HospitalDetailsNotFoundException.class, () -> {
			hospitalserviceimpl.viewHospitalById(1);
		});

		// Verify that the exception message matches the expected message
		assertEquals("Hospital details not found with hospitalId:1", exception.getMessage());

		// Verify that findById method was called with the correct ID
		Mockito.verify(hrMock, Mockito.times(1)).findById(1);
	}

	/*@Test
	void testRemoveHospitalById() {
		// Mock the HospitalRepository
		HospitalRepository hrMock = Mockito.mock(HospitalRepository.class);
		hospitalserviceimpl.setHospitalRepository(hrMock);

		// Set up the mock to return true for existsById (hospital with ID exists)
		when(hrMock.existsById(1)).thenReturn(true);

		// Call the removeHospitalById method
		 Hospital result = hospitalserviceimpl.removeHospitalById(1);

		// Verify that the method returns "Removed successfully"
		assertEquals("Removed successfully", result);

		// Verify that existsById and deleteById methods were called with the correct ID
		verify(hrMock, times(1)).existsById(1);
		verify(hrMock, times(1)).deleteById(1);
	}
*/
	@Test
	void testRemoveHospitalById_HospitalNotFound() {
		// Mock the HospitalRepository to return false for existsById (hospital with ID does not exist)
		HospitalRepository hrMock = Mockito.mock(HospitalRepository.class);
		hospitalserviceimpl.setHospitalRepository(hrMock);
		when(hrMock.existsById(1)).thenReturn(false);

		// Call the removeHospitalById method with an ID that does not exist
		HospitalIdMismatchException exception = assertThrows(HospitalIdMismatchException.class, () -> {
			hospitalserviceimpl.removeHospitalById(1);
		});

		// Verify that the exception message matches the expected message
		assertEquals("To remove user : no user is Found with id:1", exception.getMessage());

		// Verify that existsById method was called with the correct ID
		verify(hrMock, times(1)).existsById(1);

		// Verify that deleteById was never called in this case
		verify(hrMock, never()).deleteById(1);
	}
	@Test
	void testChangePassword() {
		// Mock the HospitalRepository
		HospitalRepository hrMock = Mockito.mock(HospitalRepository.class);
		hospitalserviceimpl.setHospitalRepository(hrMock);

		// Mock the bcrypt encoder
		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

		// Create a sample Hospital object
		Hospital hospital = new Hospital();
		hospital.setUserName("testuser");
		hospital.setPassword(bcrypt.encode("oldPassword")); // Encrypt the password

		// Set up the mock to return the Hospital by username
		when(hrMock.findByUserName("testuser")).thenReturn(Optional.of(hospital));

		// Call the changePassword method with the correct old password and new password
		String result = hospitalserviceimpl.changePassword("testuser", "oldPassword", "newPassword");

		// Verify that the method returns "password changed successfully"
		assertEquals("password changed successfully", result);

		// Verify that bcrypt encoder is used to encrypt the new password
		assertTrue(bcrypt.matches("newPassword", hospital.getPassword()));
		verify(hrMock, times(1)).save(hospital);
	}

	@Test
	void testChangePassword_IncorrectOldPassword() {
		// Mock the HospitalRepository
		HospitalRepository hrMock = Mockito.mock(HospitalRepository.class);
		hospitalserviceimpl.setHospitalRepository(hrMock);

		// Mock the bcrypt encoder
		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

		// Create a sample Hospital object
		Hospital hospital = new Hospital();
		hospital.setUserName("testuser");
		hospital.setPassword(bcrypt.encode("oldPassword")); // Encrypt the password

		// Set up the mock to return the Hospital by username
		when(hrMock.findByUserName("testuser")).thenReturn(Optional.of(hospital));

		// Call the changePassword method with an incorrect old password
		String result = hospitalserviceimpl.changePassword("testuser", "incorrectPassword", "newPassword");

		// Verify that the method returns "password incorrect"
		assertEquals("password incorrect", result);

		// Verify that the Hospital object was not updated
		verify(hrMock, never()).save(hospital);
	}

	@Test
	void testChangePassword_UserNameNotFound() {
		// Mock the HospitalRepository to return an empty Optional (user not found)
		HospitalRepository hrMock = Mockito.mock(HospitalRepository.class);
		hospitalserviceimpl.setHospitalRepository(hrMock);
		when(hrMock.findByUserName("nonexistentuser")).thenReturn(Optional.empty());

		// Call the changePassword method with a nonexistent username
		UserNameNotFoundException exception = assertThrows(UserNameNotFoundException.class, () -> {
			hospitalserviceimpl.changePassword("nonexistentuser", "oldPassword", "newPassword");
		});

		// Verify that the exception message matches the expected message
		assertEquals("User Name not found", exception.getMessage());

		// Verify that the HospitalRepository method was called with the correct username
		verify(hrMock, times(1)).findByUserName("nonexistentuser");
	}
}










