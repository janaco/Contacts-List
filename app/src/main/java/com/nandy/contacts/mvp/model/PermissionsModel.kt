package com.nandy.contacts.mvp.model

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat

/**
 * Created by yana on 04.12.17.
 */

class PermissionsModel(val activity: Activity){

    companion object {
        @JvmField val REQUEST_READ_CONTACTS = 44
    }

    fun hasPermissionsToReadContacts():Boolean{
            return ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED;
    }

    fun requestReadContactsPermission(){
        ActivityCompat.requestPermissions(
            activity, arrayOf(Manifest.permission.READ_CONTACTS), REQUEST_READ_CONTACTS)
    }
}
