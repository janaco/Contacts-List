package com.nandy.contacts.model;

import android.graphics.Bitmap;
import android.net.Uri;

/**
 * Created by yana on 28.11.17.
 */


public class Contact {

    public long id;
    public String name;
    public Uri photoURI;

    public Contact(long id, String name, Uri photoURI) {
        this.id = id;
        this.name = name;
        this.photoURI = photoURI;
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


    public Uri getPhotoURI() {
        return photoURI;
    }

    public void setPhotoURI(Uri photoURI) {
        this.photoURI = photoURI;
    }
}
