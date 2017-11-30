package com.nandy.contacts.model;

import android.support.annotation.StringRes;

/**
 * Created by yana on 30.11.17.
 */

public class Address {

    private String street;
    private String city;
    private String postalCode;
    @StringRes
    private int type;

    public Address(String street, String city, String postalCode, @StringRes int type) {
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
        this.type = type;
    }


    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public int getType() {
        return type;
    }


    @Override
    public String toString() {
        return String.format("%s\n%s, %s", street, city, postalCode);
    }
}
