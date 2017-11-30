package com.nandy.contacts.model;

import android.support.annotation.StringRes;

/**
 * Created by yana on 30.11.17.
 */

public class Phone {

    private String number;
    @StringRes
    private int type;

    public Phone(String number, @StringRes int type) {
        this.number = number;
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public int getType() {
        return type;
    }

    @Override
    public String toString() {
        return number + ", type: " + type;
    }
}
