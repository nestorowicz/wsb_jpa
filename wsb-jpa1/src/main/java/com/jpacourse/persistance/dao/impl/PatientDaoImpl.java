package com.jpacourse.persistance.dao.impl;

import com.jpacourse.persistance.dao.PatientDao;
import com.jpacourse.persistance.entity.DoctorEntity;
import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.persistance.entity.VisitEntity;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class PatientDaoImpl extends AbstractDao<PatientEntity, Long> implements PatientDao {

    @Override
    public void addVisit(long patientId, long doctorId, LocalDateTime time,
                         String description) throws IllegalArgumentException {
        PatientEntity patient = findOne(patientId);
        DoctorEntity doctor = entityManager.find(DoctorEntity.class, doctorId);

        if (patient == null || doctor == null) {
            throw new IllegalArgumentException("Patient or Doctor not found");
        }

        VisitEntity visit = new VisitEntity();
        visit.setTime(time);
        visit.setDescription(description);

        patient.addVisit(visit);
        doctor.addVisit(visit);

        entityManager.merge(patient);
    }

    @Override
    public List<PatientEntity> findAllByLastName(String lastName) {
        String jpql = "SELECT p FROM PatientEntity p WHERE p.lastName = :lastName";

        return entityManager.createQuery(jpql, PatientEntity.class)
                .setParameter("lastName", lastName)
                .getResultList();
    }

    @Override
    public List<PatientEntity> findAllByVisitsCountGreaterThan(int visitsCount) {
        String jpql = "SELECT p FROM PatientEntity p WHERE SIZE(p.visits) > :visitsCount";

        return entityManager.createQuery(jpql, PatientEntity.class)
                .setParameter("visitsCount", visitsCount)
                .getResultList();
    }

    public List<PatientEntity> findAllByDateOfPassingNotNullAndAfter(LocalDate date) {
        String jpql = "SELECT p FROM PatientEntity p WHERE p.dateOfPassing IS NOT NULL AND p.dateOfPassing > :date";

        return entityManager.createQuery(jpql, PatientEntity.class)
                .setParameter("date", date)
                .getResultList();
    }
}
