package com.jpacourse.mapper;

import com.jpacourse.dto.PatientVisitTO;
import com.jpacourse.persistance.entity.MedicalTreatmentEntity;
import com.jpacourse.persistance.entity.VisitEntity;

public final class VisitMapper {

    public static PatientVisitTO mapToTO(final VisitEntity visitEntity) {
        if (visitEntity == null) {
            return null;
        }

        final PatientVisitTO patientVisitTO = new PatientVisitTO();
        patientVisitTO.setTime(visitEntity.getTime());
        patientVisitTO.setDoctorFirstName(visitEntity.getDoctor().getFirstName());
        patientVisitTO.setDoctorLastName(visitEntity.getDoctor().getLastName());
        patientVisitTO.setTreatmentTypes(visitEntity.getMedicalTreatments().stream().map(MedicalTreatmentEntity::getType).toList());

        return patientVisitTO;
    }
}
