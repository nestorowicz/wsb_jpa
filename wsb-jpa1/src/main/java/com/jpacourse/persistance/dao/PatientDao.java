package com.jpacourse.persistance.dao;

import com.jpacourse.persistance.entity.PatientEntity;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;

public interface PatientDao extends Dao<PatientEntity, Long> {

    @Transactional
    void addVisit(long patientId, long doctorId, LocalDateTime time, String description) throws IllegalArgumentException;
}
