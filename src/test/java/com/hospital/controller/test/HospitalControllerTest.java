package com.hospital.controller.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.hospital.controller.HospitalController;
import com.hospital.dtos.HospitalDto;
import com.hospital.entities.Hospital;
import com.hospital.exceptions.HospitalIdMismatchException;
import com.hospital.service.HospitalServiceImpl;

 class HospitalControllerTest {

    @InjectMocks
    private HospitalController hospitalController;

    @Mock
    private HospitalServiceImpl hospitalService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
     void testChangePassword() {
        HospitalDto hospitalDto = new HospitalDto();
        hospitalDto.setUserName("username");
        hospitalDto.setOldPassword("oldPassword");
        hospitalDto.setNewPassword("newPassword");

        when(hospitalService.changePassword(anyString(), anyString(), anyString())).thenReturn("Password changed successfully");

        ResponseEntity<String> response = hospitalController.changePassword(hospitalDto);

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals("Password changed successfully", response.getBody());

        verify(hospitalService, times(1)).changePassword(anyString(), anyString(), anyString());
    }

    @Test
    void testUpdateHospital() {
        int hospitalId = 1;
        Hospital hospital = new Hospital();
        hospital.setHospitalId(hospitalId);
        hospital.setHospitalName("Hospital Name");

        when(hospitalService.updateHospital(eq(hospitalId), any())).thenReturn(hospital);

        Hospital updatedHospital = hospitalController.updateHospital(hospitalId, hospital);

        assertEquals(hospitalId, updatedHospital.getHospitalId());
        assertEquals("Hospital Name", updatedHospital.getHospitalName());

        verify(hospitalService, times(1)).updateHospital(eq(hospitalId), any());
    }

    @Test
    void testViewHospitalById() {
        int hospitalId = 1;
        Hospital hospital = new Hospital();
        hospital.setHospitalId(hospitalId);
        hospital.setHospitalName("Hospital Name");

        when(hospitalService.viewHospitalById(hospitalId)).thenReturn(hospital);

        Hospital retrievedHospital = hospitalController.viewHospitalById(hospitalId);

        assertEquals(hospitalId, retrievedHospital.getHospitalId());
        assertEquals("Hospital Name", retrievedHospital.getHospitalName());

        verify(hospitalService, times(1)).viewHospitalById(hospitalId);
    }

    @Test
    void testViewAllHospitals() {
        List<Hospital> hospitals = new ArrayList<>();
        hospitals.add(new Hospital());
        hospitals.add(new Hospital());

        when(hospitalService.viewAllHospitals()).thenReturn(hospitals);

        List<Hospital> retrievedHospitals = hospitalController.viewAllHospitals();

        assertEquals(2, retrievedHospitals.size());

        verify(hospitalService, times(1)).viewAllHospitals();
    }

    @Test
    void testRemoveHospitalById() {
        int hospitalId = 1;

        doNothing().when(hospitalService).removeHospitalById(hospitalId);

        hospitalController.removeHospitalById(hospitalId);

        verify(hospitalService, times(1)).removeHospitalById(hospitalId);
    }
}
