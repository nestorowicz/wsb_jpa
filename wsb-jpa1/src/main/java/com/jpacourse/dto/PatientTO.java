package com.jpacourse.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class PatientTO implements Serializable {

    private String patientNumber;
    private String firstName;
    private String lastName;
    private AddressTO addressTO;
    private String telephoneNumber;
    private String email;
    private LocalDate dateOfBirth;
    private LocalDate dateOfPassing;
    private List<VisitTO> visits;

    public String getPatientNumber() {
        return patientNumber;
    }

    public void setPatientNumber(String patientNumber) {
        this.patientNumber = patientNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public AddressTO getAddressTO() {
        return addressTO;
    }

    public void setAddressTO(AddressTO addressTO) {
        this.addressTO = addressTO;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public LocalDate getDateOfPassing() {
        return dateOfPassing;
    }

    public void setDateOfPassing(LocalDate dateOfPassing) {
        this.dateOfPassing = dateOfPassing;
    }

    public List<VisitTO> getVisits() {
        return visits;
    }

    public void setVisits(List<VisitTO> visits) {
        this.visits = Collections.unmodifiableList(visits);
    }
}


