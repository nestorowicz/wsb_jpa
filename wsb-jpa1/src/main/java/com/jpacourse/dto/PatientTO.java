package com.jpacourse.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class PatientTO implements Serializable {

    private String firstName;
    private String lastName;
    private String patientNumber;
    private List<VisitTO> visits;
    private LocalDateTime dateOfPassing;

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

    public String getPatientNumber() {
        return patientNumber;
    }

    public void setPatientNumber(String patientNumber) {
        this.patientNumber = patientNumber;
    }

    public List<VisitTO> getVisits() {
        return visits;
    }

    public void setVisits(List<VisitTO> visits) {
        this.visits = Collections.unmodifiableList(visits);
    }

    public LocalDateTime getDateOfPassing() {
        return dateOfPassing;
    }

    public void setDateOfPassing(LocalDateTime dateOfPassing) {
        this.dateOfPassing = dateOfPassing;
    }
}


