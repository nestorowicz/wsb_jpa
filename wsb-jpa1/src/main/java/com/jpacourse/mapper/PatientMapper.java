package com.jpacourse.mapper;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.persistance.entity.PatientEntity;

public final class PatientMapper {

    public static PatientTO mapToTO(final PatientEntity patientEntity) {
        if (patientEntity == null) {
            return null;
        }
        final PatientTO patientTO = new PatientTO();
        patientTO.setPatientNumber(patientEntity.getPatientNumber());
        patientTO.setFirstName(patientEntity.getFirstName());
        patientTO.setLastName(patientEntity.getLastName());
        patientTO.setDateOfPassing(patientEntity.getDateOfPassing());
        patientTO.setVisits(patientEntity.getVisits().stream().map(VisitMapper::mapToTO).toList());
        return patientTO;
    }
}
