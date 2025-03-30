package com.jpacourse.persistance.dao;

import com.jpacourse.persistance.entity.AddressEntity;
import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.persistance.entity.VisitEntity;
import com.jpacourse.persistance.enums.TreatmentType;
import com.jpacourse.stubs.PatientTestStubber;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class PatientDaoTest {

    @Autowired
    protected EntityManager entityManager;

    @Autowired
    private AddressDao addressDao;

    @Autowired
    private PatientDao patientDao;

    @Test
    public void shouldSavePatient() {
        // GIVEN
        PatientEntity patient = PatientTestStubber.buildValidPatient();
        long patientsCountBefore = patientDao.count();
        long addressesCountBefore = addressDao.count();

        // WHEN
        PatientEntity saved = patientDao.save(patient);

        // THEN
        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
        assertThat(saved).usingRecursiveComparison().ignoringFields("id").isEqualTo(patient);
        assertThat(patientDao.count()).isEqualTo(patientsCountBefore + 1);
        assertThat(addressDao.count()).isEqualTo(addressesCountBefore + 1);
    }

    @Test
    public void shouldThrowAnError_whenSavePatient_emptyFirstName() {
        // GIVEN
        PatientEntity patient = PatientTestStubber.buildInvalidPatientWithNullFirstName();

        // WHEN && THEN
        assertThatThrownBy(() -> patientDao.save(patient)).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @Sql(scripts = {
            "/data/addresses.sql",
            "/data/patients.sql",
            "/data/doctors.sql",
            "/data/visits.sql",
            "/data/medical-treatments.sql"
    })
    public void shouldFindPatientById() {
        // GIVEN
        long patientId = 24L;

        // WHEN
        PatientEntity found = patientDao.findOne(patientId);

        // THEN
        assertThat(found).isNotNull();
        assertThat(found.getFirstName()).isEqualTo("Robert");
        assertThat(found.getLastName()).isEqualTo("Chen");
        assertThat(found.getPatientNumber()).isEqualTo("54327");
        assertThat(found.getDateOfPassing()).isEqualTo("2025-03-01");

        AddressEntity address = found.getAddress();
        assertThat(address).isNotNull();
        assertThat(address.getId()).isNotNull();
        assertThat(address.getAddressLine1()).isEqualTo("456 Pine Avenue");
        assertThat(address.getAddressLine2()).isEqualTo("Suite 300");
        assertThat(address.getCity()).isEqualTo("Seattle");

        assertThat(found.getVisits()).hasSize(1);
        VisitEntity gotVisit1 = found.getVisits().get(0);
        assertThat(gotVisit1.getDoctor().getFirstName()).isEqualTo("Michael");
        assertThat(gotVisit1.getDoctor().getLastName()).isEqualTo("Rodriguez");
        assertThat(gotVisit1.getTime()).isEqualTo("2025-03-18T14:00:00");

        assertThat(gotVisit1.getMedicalTreatments()).hasSize(1);
        TreatmentType treatmentType1 = gotVisit1.getMedicalTreatments().get(0).getType();
        assertThat(treatmentType1).isEqualTo(TreatmentType.RTG);
    }

    @Test
    @Sql(scripts = {
            "/data/addresses.sql",
            "/data/patients.sql",
            "/data/doctors.sql",
            "/data/visits.sql",
            "/data/medical-treatments.sql"
    })
    public void shouldFindPatientByIdWithMultipleVisits() {
        // GIVEN
        long patientId = 22L;

        // WHEN
        PatientEntity found = patientDao.findOne(patientId);

        // THEN
        assertThat(found).isNotNull();
        assertThat(found.getFirstName()).isEqualTo("Connor");
        assertThat(found.getLastName()).isEqualTo("Sullivan");
        assertThat(found.getPatientNumber()).isEqualTo("98730");
        assertThat(found.getDateOfPassing()).isNull();

        AddressEntity address = found.getAddress();
        assertThat(address).isNotNull();
        assertThat(address.getId()).isNotNull();
        assertThat(address.getAddressLine1()).isEqualTo("87 Parkway Ave");
        assertThat(address.getAddressLine2()).isEqualTo("Floor 3");
        assertThat(address.getCity()).isEqualTo("Miami");
        assertThat(address.getPostalCode()).isEqualTo("33101");

        assertThat(found.getVisits()).isNotNull();
        assertThat(found.getVisits()).hasSize(5);
    }

    @Test
    @Sql(scripts = {
            "/data/addresses.sql",
            "/data/patients.sql"
    })
    public void shouldFindAllPatients() {
        // GIVEN
        int patientsCount = 5;

        // WHEN
        List<PatientEntity> found = patientDao.findAll();

        // THEN
        assertThat(found).isNotNull();
        assertThat(found).hasSize(patientsCount);
    }

    @Test
    @Sql(scripts = {
            "/data/addresses.sql",
            "/data/patients.sql"
    })
    public void shouldFindAllPatientsByLastName() {
        // GIVEN
        int patientsWithSameLastNameCount = 3;

        // WHEN
        List<PatientEntity> found = patientDao.findAllByLastName("Chen");

        // THEN
        assertThat(found).isNotNull();
        assertThat(found).hasSize(patientsWithSameLastNameCount);
        assertThat(found).filteredOn("lastName", "Chen").isNotNull();
    }

    @Test
    @Sql(scripts = {
            "/data/addresses.sql",
            "/data/patients.sql",
            "/data/doctors.sql",
            "/data/visits.sql"
    })
    public void shouldFindAllPatientsWithMoreThan1Visit() {
        // GIVEN
        int patientsWithMoreThan1VisitCount = 1;

        // WHEN
        List<PatientEntity> found = patientDao.findAllByVisitsCountGreaterThan(1);

        // THEN
        assertThat(found).isNotNull();
        assertThat(found).hasSize(patientsWithMoreThan1VisitCount);
    }

    @Test
    @Sql(scripts = {
            "/data/addresses.sql",
            "/data/patients.sql"
    })
    public void shouldFindAllDeceasedPatientsAfterDate_1Patient() {
        // GIVEN
        int deceasedPatientsCount = 1;
        LocalDate dateOfPassingParam = LocalDate.of(1990, 4, 11);

        // WHEN
        List<PatientEntity> found = patientDao.findAllByDateOfPassingNotNullAndAfter(dateOfPassingParam);

        // THEN
        assertThat(found).isNotNull();
        assertThat(found).hasSize(deceasedPatientsCount);
    }

    @Test
    @Sql(scripts = {
            "/data/addresses.sql",
            "/data/patients.sql"
    })
    public void shouldFindAllDeceasedPatientsAfterDate_emptyList() {
        // GIVEN
        int deceasedPatientsCount = 0;
        LocalDate dateOfPassingParam = LocalDate.of(2025, 3, 1);

        // WHEN
        List<PatientEntity> found = patientDao.findAllByDateOfPassingNotNullAndAfter(dateOfPassingParam);

        // THEN
        assertThat(found).isNotNull();
        assertThat(found).hasSize(deceasedPatientsCount);
    }

    @Test
    @Sql(scripts = {
            "/data/addresses.sql",
            "/data/patients.sql",
            "/data/doctors.sql",
            "/data/visits.sql",
            "/data/medical-treatments.sql"
    })
    public void shouldRemovePatientById() {
        // GIVEN
        long patientsCountBefore = patientDao.count();
        long addressesCountBefore = addressDao.count();
        long doctorsCountBefore = countAllDoctorTableEntities();
        long visitsCountBefore = countAllVisitTableEntities();
        long medicalTreatmentsCountBefore = countAllMedicalTreatmentTableEntities();

        long patientId = 23L;
        long patientAddressId = patientDao.findOne(patientId).getAddress().getId();
        long patientVisitsCount = 2;
        long patientVisitsMedicalTreatments = 3;

        // WHEN
        patientDao.delete(patientId);

        // THEN
        assertThat(patientDao.findOne(patientId)).isNull();
        assertThat(addressDao.findOne(patientAddressId)).isNull();
        assertThat(patientDao.count()).isEqualTo(patientsCountBefore - 1);
        assertThat(addressDao.count()).isEqualTo(addressesCountBefore - 1);
        assertThat(countAllDoctorTableEntities()).isEqualTo(doctorsCountBefore);
        assertThat(countAllVisitTableEntities()).isEqualTo(visitsCountBefore - patientVisitsCount);
        assertThat(countAllMedicalTreatmentTableEntities())
                .isEqualTo(medicalTreatmentsCountBefore - patientVisitsMedicalTreatments);
    }

    @Test
    @Sql(scripts = {
            "/data/addresses.sql",
            "/data/patients.sql",
            "/data/doctors.sql"
    })
    public void shouldAddPatientVisit() {
        // GIVEN
        long patientId = 24L;
        long doctorId = 123L;
        LocalDateTime visitDate = LocalDateTime.of(2025, 4, 20, 14, 30);
        String visitDescription = "follow-up visit";

        // WHEN && THEN
        assertThatCode(() -> patientDao.addVisit(patientId, doctorId, visitDate, visitDescription)).doesNotThrowAnyException();

        PatientEntity patient = patientDao.getOne(patientId);
        assertThat(patient).isNotNull();
        assertThat(patient.getVisits()).hasSize(1);

        VisitEntity visit = patient.getVisits().get(0);
        assertThat(visit).isNotNull();
        assertThat(visit.getId()).isNotNull();
        assertThat(visit.getPatient().getId()).isEqualTo(patientId);
        assertThat(visit.getDoctor().getId()).isEqualTo(doctorId);
        assertThat(visit.getTime()).isEqualTo(visitDate);
        assertThat(visit.getDescription()).isEqualTo(visitDescription);
        assertThat(visit.getMedicalTreatments()).isNotNull();
        assertThat(visit.getMedicalTreatments()).hasSize(0);
    }

    @Test
    @Sql(scripts = {
            "/data/addresses.sql",
            "/data/patients.sql",
    })
    public void shouldThrowIllegalArgumentException_whenAddPatientVisit_doctorNotFound() {
        // GIVEN
        long patientId = 24L;
        long doctorId = 123L;
        LocalDateTime visitDate = LocalDateTime.of(2025, 4, 20, 14, 30);
        String visitDescription = "follow-up visit";

        // WHEN && THEN
        assertThatThrownBy(() -> patientDao.addVisit(patientId, doctorId, visitDate, visitDescription))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Patient or Doctor not found");

        PatientEntity patient = patientDao.getOne(patientId);
        assertThat(patient).isNotNull();
        assertThat(patient.getVisits()).hasSize(0);
    }

    private long countAllDoctorTableEntities() {
        return countAllTableEntities("DoctorEntity");
    }

    private long countAllVisitTableEntities() {
        return countAllTableEntities("VisitEntity");
    }

    private long countAllMedicalTreatmentTableEntities() {
        return countAllTableEntities("MedicalTreatmentEntity");
    }

    private long countAllTableEntities(String tableName) {
        String jpql = "SELECT COUNT(e) FROM " + tableName + " e";
        return entityManager.createQuery(jpql, Long.class).getSingleResult();
    }
}
