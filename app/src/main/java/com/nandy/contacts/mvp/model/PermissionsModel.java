package com.nandy.contacts.mvp.model;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by yana on 28.11.17.
 */

public class PermissionsModel {

    public static final int REQUEST_READ_CONTACTS = 44;
    private Activity activity;

    public PermissionsModel(Activity activity) {
        this.activity = activity;
    }

    public boolean hasPermissionsToReadContacts() {
        return ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED;
    }

    public void requestReadContactsPermission() {
        ActivityCompat.requestPermissions(
                activity,
                new String[]{Manifest.permission.READ_CONTACTS},
                REQUEST_READ_CONTACTS);

    }
}
