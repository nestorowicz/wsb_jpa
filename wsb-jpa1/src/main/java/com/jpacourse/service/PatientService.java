package com.jpacourse.service;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.rest.exception.impl.PatientNotFoundException;

public interface PatientService {
    PatientTO findById(final Long id);

    void deleteById(final Long id) throws PatientNotFoundException;
}
