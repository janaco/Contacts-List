package com.nandy.contacts;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;

import com.nandy.contacts.model.Contact;

import java.io.InputStream;

/**
 * Created by yana on 28.11.17.
 */

public class ContactImageLoader {

    @Nullable
    public static Bitmap getContactImage(ContentResolver contentResolver, Contact contact) {

        InputStream inputStream = ContactsContract.Contacts.openContactPhotoInputStream(contentResolver,
                ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contact.getId()));

        if (inputStream != null) {
            return BitmapFactory.decodeStream(inputStream);
        }

        return null;
    }
}
