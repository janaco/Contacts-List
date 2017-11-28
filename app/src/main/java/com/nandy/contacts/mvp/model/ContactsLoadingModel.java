package com.nandy.contacts.mvp.model;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import com.nandy.contacts.model.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yana on 28.11.17.
 */

public class ContactsLoadingModel {

    private static final int CONTACTS_LOADER_ID = 1;

    private Activity activity;

    public ContactsLoadingModel(Activity activity){
        this.activity = activity;
    }

    public void initLoader(LoaderManager.LoaderCallbacks<Cursor> loaderCallbacks){
        activity.getLoaderManager().initLoader(CONTACTS_LOADER_ID, null, loaderCallbacks);
    }

    public Loader<Cursor> createLoader(int id) {
        if (id == CONTACTS_LOADER_ID) {
            return contactsLoader();
        }
        return null;
    }

    public List<Contact> parseContactsCursor(Cursor cursor){
        List<Contact> contacts = new ArrayList<>();

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                contacts.add(new Contact(name));
            } while (cursor.moveToNext());
        }

        return contacts;    }


    private Loader<Cursor> contactsLoader() {
        Uri contactsUri = ContactsContract.Contacts.CONTENT_URI;

        String[] projection = {
                ContactsContract.Contacts.DISPLAY_NAME
        };

        String selection = null;
        String[] selectionArgs = {};
        String sortOrder = null;

        return new CursorLoader(
                activity,
                contactsUri,
                projection,
                selection,
                selectionArgs,
                sortOrder);
    }


}