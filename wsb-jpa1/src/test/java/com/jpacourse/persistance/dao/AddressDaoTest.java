package com.jpacourse.persistance.dao;

import com.jpacourse.persistance.entity.AddressEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class AddressDaoTest {

    @Autowired
    private AddressDao addressDao;

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

    @Test
    @Sql("/data/addresses.sql")
    public void shouldFindAllAddresses() {
        // GIVEN
        int entitiesCount = 5;

        // WHEN
        List<AddressEntity> addresses = addressDao.findAll();

        // THEN
        assertThat(addresses).isNotNull();
        assertThat(addresses).hasSize(entitiesCount);
    }

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

    private AddressEntity buildValidAddress() {
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setAddressLine1("250 Hospital Drive");
        addressEntity.setAddressLine2("Building A");
        addressEntity.setCity("New York");
        addressEntity.setPostalCode("10001");

        return addressEntity;
    }
}
