package com.jpacourse.dto;

import com.jpacourse.persistance.enums.TreatmentType;

import java.time.LocalDateTime;
import java.util.List;

public class PatientVisitTO {

    private LocalDateTime time;

    private String doctorFirstName;

    private String doctorLastName;

    private List<TreatmentType> treatmentTypes;

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getDoctorFirstName() {
        return doctorFirstName;
    }

    public void setDoctorFirstName(String doctorFirstName) {
        this.doctorFirstName = doctorFirstName;
    }

    public String getDoctorLastName() {
        return doctorLastName;
    }

    public void setDoctorLastName(String doctorLastName) {
        this.doctorLastName = doctorLastName;
    }

    public List<TreatmentType> getTreatmentTypes() {
        return treatmentTypes;
    }

    public void setTreatmentTypes(List<TreatmentType> treatmentTypes) {
        this.treatmentTypes = treatmentTypes;
    }
}
