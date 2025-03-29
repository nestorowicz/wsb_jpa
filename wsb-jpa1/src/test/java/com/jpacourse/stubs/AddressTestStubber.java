package com.jpacourse.stubs;

import com.jpacourse.persistance.entity.AddressEntity;

public class AddressTestStubber {

    public static AddressEntity buildValidAddress() {
        AddressEntity address = new AddressEntity();
        address.setAddressLine1("87 Parkway Ave");
        address.setAddressLine2("Floor 3");
        address.setCity("Miami");
        address.setPostalCode("33101");

        return address;
    }
}
