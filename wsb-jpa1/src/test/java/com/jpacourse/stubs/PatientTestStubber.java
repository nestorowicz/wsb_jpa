package com.jpacourse.stubs;

import com.jpacourse.persistance.entity.AddressEntity;
import com.jpacourse.persistance.entity.PatientEntity;

import java.time.LocalDate;

public class PatientTestStubber {

    public static PatientEntity buildValidPatient() {
        AddressEntity address = AddressTestStubber.buildValidAddress();

        PatientEntity patient = new PatientEntity();
        patient.setFirstName("John");
        patient.setLastName("Doe");
        patient.setTelephoneNumber("505123123");
        patient.setEmail("john.doe@example.com");
        patient.setPatientNumber("1212444");
        patient.setDateOfBirth(LocalDate.of(1950, 1, 1));
        patient.setDateOfPassing(LocalDate.of(2020, 4, 20));
        patient.setAddress(address);

        return patient;
    }

    public static PatientEntity buildInvalidPatientWithNullFirstName() {
        AddressEntity address = AddressTestStubber.buildValidAddress();

        PatientEntity patient = new PatientEntity();
        patient.setFirstName(null);
        patient.setLastName("Doe");
        patient.setTelephoneNumber("505123123");
        patient.setEmail("john.doe@example.com");
        patient.setPatientNumber("1212444");
        patient.setDateOfBirth(LocalDate.of(1990, 1, 1));
        patient.setDateOfPassing(null);
        patient.setAddress(address);

        return patient;
    }
}
