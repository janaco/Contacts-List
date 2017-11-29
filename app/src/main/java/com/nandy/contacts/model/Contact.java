package com.nandy.contacts.model;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

/**
 * Created by yana on 28.11.17.
 */


public class Contact implements Comparable<Contact>{

    private long id;
    private String name;
    private Bitmap photoBitmap;

    public Contact(long id, String name, Bitmap photoBitmap) {
        this.id = id;
        this.name = name;
        this.photoBitmap = photoBitmap;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getPhotoBitmap() {
        return photoBitmap;
    }


    @Override
    public int compareTo(@NonNull Contact contact) {
        return name.compareTo(contact.getName());
    }
}
