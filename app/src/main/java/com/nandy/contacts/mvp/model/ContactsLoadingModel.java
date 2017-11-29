package com.nandy.contacts.mvp.model;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.provider.ContactsContract;

import com.nandy.contacts.BitmapUtils;
import com.nandy.contacts.ContactImageLoader;
import com.nandy.contacts.R;
import com.nandy.contacts.model.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yana on 28.11.17.
 */

public class ContactsLoadingModel {

    private static final int CONTACTS_LOADER_ID = 1;

    private final static String[] FROM_COLUMNS = {
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.Contacts.PHOTO_URI
    };

    private Activity activity;

    public ContactsLoadingModel(Activity activity) {
        this.activity = activity;
    }

    public void initLoader(LoaderManager.LoaderCallbacks<Cursor> loaderCallbacks) {
        activity.getLoaderManager().initLoader(CONTACTS_LOADER_ID, null, loaderCallbacks);
    }

    public Loader<Cursor> createLoader(int id) {
        if (id == CONTACTS_LOADER_ID) {
            return contactsLoader();
        }
        return null;
    }

    public List<Contact> parseContactsCursor(Cursor cursor) {
        List<Contact> contacts = new ArrayList<>();

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {
                long id = cursor.getLong(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                Bitmap bitmap = getContactPhoto(id, name);

                if (name == null) {
                    name = activity.getString(R.string.unknown);
                }
                contacts.add(new Contact(id, name, bitmap));
            } while (cursor.moveToNext());
        }

        return contacts;
    }

    private Bitmap getContactPhoto(long id, String name) {
        Bitmap bitmap = ContactImageLoader.getContactImage(activity.getContentResolver(), id);
        if (bitmap == null) {
            bitmap = BitmapUtils.drawTextToBitmap(activity, getNameInitials(name));
        } else {
            bitmap = BitmapUtils.convertToCircle(bitmap);
        }

        return bitmap;
    }

    private String getNameInitials(String name) {

        if (name != null) {
            String[] textArray = name.split(" ");
            StringBuilder builder = new StringBuilder();
            for (String word : textArray) {
                builder.append(word.charAt(0));
            }
            return builder.toString().toUpperCase();
        } else {
            return "?";
        }
    }

    private Loader<Cursor> contactsLoader() {

        return new CursorLoader(
                activity, ContactsContract.Contacts.CONTENT_URI, FROM_COLUMNS, null, new String[0], null);


    }


}