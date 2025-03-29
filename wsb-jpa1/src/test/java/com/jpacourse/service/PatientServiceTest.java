package com.jpacourse.service;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.persistance.dao.AddressDao;
import com.jpacourse.persistance.dao.PatientDao;
import com.jpacourse.persistance.entity.*;
import com.jpacourse.rest.exception.impl.PatientNotFoundException;
import com.jpacourse.service.impl.PatientServiceImpl;
import com.jpacourse.stubs.AddressTestStubber;
import com.jpacourse.stubs.PatientTestStubber;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@Transactional
@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {

    @Mock
    AddressDao addressDao;

    @Mock
    PatientDao patientDao;

    @InjectMocks
    PatientServiceImpl patientService;

    @Test
    public void shouldFindById() {
        // GIVEN
        long patientId = 123L;
        AddressEntity mockAddress = AddressTestStubber.buildValidAddress();
        PatientEntity mockPatient = PatientTestStubber.buildValidPatient();
        when(patientDao.findOne(anyLong())).thenReturn(mockPatient);

        // WHEN
        PatientTO found = patientService.findById(patientId);

        // THEN
        assertThat(found).isNotNull();
        assertThat(found).usingRecursiveComparison().ignoringFields("addressTO").isEqualTo(mockPatient);
        assertThat(found.getAddressTO()).usingRecursiveComparison().isEqualTo(mockAddress);
        verify(patientDao).findOne(patientId);
        verifyNoInteractions(addressDao);
    }

    @Test
    public void shouldRemovePatient() {
        // GIVEN
        long patientId = 23L;

        patientService.deleteById(patientId);

        verify(patientDao).delete(patientId);
    }

    @Test
    public void shouldThrowAnError_whenRemovePatient_patientNotFound() {
        // GIVEN
        long patientId = 991L;
        doThrow(new EntityNotFoundException("Patient not found"))
                .when(patientDao).delete(patientId);

        // WHEN
        assertThatThrownBy(() -> patientService.deleteById(patientId))
                .isInstanceOf(PatientNotFoundException.class);
    }
}
