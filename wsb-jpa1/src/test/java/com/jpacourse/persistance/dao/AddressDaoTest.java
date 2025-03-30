package com.jpacourse.persistance.dao;

import com.jpacourse.persistance.entity.AddressEntity;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
public class AddressDaoTest {

    @Autowired
    private AddressDao addressDao;

    @Transactional
    @Test
    public void shouldSaveAddress() {
        // GIVEN
        AddressEntity address = buildValidAddress();
        long entitiesCountBefore = addressDao.count();

        // WHEN
        AddressEntity saved = addressDao.save(address);

        // THEN
        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
        assertThat(addressDao.count()).isEqualTo(entitiesCountBefore + 1);
    }

    @Transactional
    @Test
    @Sql("/data/addresses.sql")
    public void shouldFindAddressById() {
        // GIVEN
        long addressId = 901L;

        // WHEN
        AddressEntity found = addressDao.findOne(addressId);

        // THEN
        assertThat(found).isNotNull();
        assertThat(found.getPostalCode()).isEqualTo("10001");
    }

    @Transactional
    @Test
    @Sql("/data/addresses.sql")
    public void shouldFindAllAddresses() {
        // GIVEN
        int entitiesCount = 7;

        // WHEN
        List<AddressEntity> addresses = addressDao.findAll();

        // THEN
        assertThat(addresses).isNotNull();
        assertThat(addresses).hasSize(entitiesCount);
    }

    @Transactional
    @Test
    @Sql("/data/addresses.sql")
    public void shouldRemoveAddressById() {
        // GIVEN
        long addressId = 901L;
        long entitiesCountBefore = addressDao.count();

        // WHEN
        addressDao.delete(addressId);

        // THEN
        assertThat(addressDao.count()).isEqualTo(entitiesCountBefore - 1);
        assertThat(addressDao.findOne(addressId)).isNull();
    }

    @Test
    @Sql(scripts = {
            "/data/addresses.sql"
    })
    public void shouldDetectVersionConflict() {
        // GIVEN
        AddressEntity entity1 = addressDao.findOne(901L);
        AddressEntity entity2 = addressDao.findOne(901L);
        entity1.setAddressLine1("XXX");
        addressDao.update(entity1);
        entity2.setAddressLine1("YYY");

        // TEST
        assertThrows(OptimisticLockingFailureException.class, () -> {
            addressDao.update(entity2);
        });
    }

    private AddressEntity buildValidAddress() {
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setAddressLine1("250 Hospital Drive");
        addressEntity.setAddressLine2("Building A");
        addressEntity.setCity("New York");
        addressEntity.setPostalCode("10001");

        return addressEntity;
    }
}
