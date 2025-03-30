package com.jpacourse.persistance.dao;

import com.jpacourse.persistance.entity.PatientEntity;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface PatientDao extends Dao<PatientEntity, Long> {

    @Transactional
    void addVisit(long patientId, long doctorId, LocalDateTime time, String description) throws IllegalArgumentException;

    List<PatientEntity> findAllByLastName(String lastName);

    List<PatientEntity> findAllByVisitsCountGreaterThan(int visitsCount);

    List<PatientEntity> findAllByDateOfPassingNotNullAndAfter(LocalDate date);
}
