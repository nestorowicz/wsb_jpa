package com.jpacourse.service.impl;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.dto.VisitTO;
import com.jpacourse.persistance.entity.AddressEntity;
import com.jpacourse.persistance.entity.VisitEntity;
import com.jpacourse.persistance.enums.TreatmentType;
import com.jpacourse.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PatientServiceImplTest {

    @Autowired
    private PatientService patientService;

    @Transactional
    @Test
    void findById() {
        // given
        // when
        PatientTO patientTO = patientService.findById(24L);
        // then
        assertThat(patientTO).isNotNull();
        assertThat(patientTO.getFirstName()).isEqualTo("Robert");
        assertThat(patientTO.getLastName()).isEqualTo("Chen");
        assertThat(patientTO.getPatientNumber()).isEqualTo("54327");
        assertThat(patientTO.getDateOfPassing()).isEqualTo("2025-03-01");

        assertThat(patientTO.getVisits()).hasSize(1);
        VisitTO gotVisit1 = patientTO.getVisits().get(0);
        assertThat(gotVisit1.getDoctorFirstName()).isEqualTo("Michael");
        assertThat(gotVisit1.getDoctorLastName()).isEqualTo("Rodriguez");
        assertThat(gotVisit1.getTime()).isEqualTo("2025-03-18T14:00:00");
        assertThat(gotVisit1.getTreatmentTypes()).hasSize(1);
        assertThat(gotVisit1.getTreatmentTypes().get(0)).isEqualTo(TreatmentType.RTG);
    }
}