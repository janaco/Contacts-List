package com.nandy.contacts.model;

import android.support.annotation.StringRes;

/**
 * Created by yana on 30.11.17.
 */

public class Email {

    private String email;
    @StringRes
    private int type;

    public Email(String email, @StringRes int type) {
        this.email = email;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Email{" +
                "email='" + email + '\'' +
                ", type=" + type +
                '}';
    }
}
