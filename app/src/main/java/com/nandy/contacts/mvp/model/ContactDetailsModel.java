package com.nandy.contacts.mvp.model;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.util.Log;

import com.nandy.contacts.BitmapUtils;
import com.nandy.contacts.ContactImageLoader;
import com.nandy.contacts.R;
import com.nandy.contacts.model.Address;
import com.nandy.contacts.model.Contact;
import com.nandy.contacts.model.Email;
import com.nandy.contacts.model.Phone;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yana on 29.11.17.
 */

public class ContactDetailsModel {

    public static final int PHONES_LOADER_ID = 11;
    public static final int EMAILS_LOADER_ID = 22;
    public static final int ADDRESSES_LOADER_ID = 33;
    public static final int CONTACT_LOADER_ID = 44;

    private String id;
    private LoaderManager loaderManager;
    private Context context;

    public ContactDetailsModel(Context context, LoaderManager loaderManager, String id) {
        this.context = context;
        this.loaderManager = loaderManager;
        this.id = id;
    }

    public void initLoader(int id, LoaderManager.LoaderCallbacks<Cursor> loaderCallbacks) {
        loaderManager.initLoader(id, null, loaderCallbacks);
    }



    public void destroyLoader(int id) {
        loaderManager.destroyLoader(id);
    }

    public Loader<Cursor> createLoader(int id) {

        switch (id) {

            case PHONES_LOADER_ID:
                return createPhonesCursorLoader();

            case EMAILS_LOADER_ID:
                return createEmailsCursorLoader();

            case ADDRESSES_LOADER_ID:
                return createAddressesCursorLoader();

            case CONTACT_LOADER_ID:
                return createContactsLoader();

            default:
                return null;
        }
    }

    private Loader<Cursor> createContactsLoader() {
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        String selection = ContactsContract.Contacts._ID + "=?";
        String[] selectionArgs = new String[]{id};
        String[] projection = {
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.PHOTO_URI
        };

        return new CursorLoader(
                context, uri, projection, selection, selectionArgs, null);

    }

    private CursorLoader createPhonesCursorLoader() {

        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String selection = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?";
        String[] selectionArgs = new String[]{id};

        return new CursorLoader(context, uri, null, selection, selectionArgs, null);
    }


    private CursorLoader createEmailsCursorLoader() {

        Uri uri = ContactsContract.CommonDataKinds.Email.CONTENT_URI;
        String selection = ContactsContract.CommonDataKinds.Email.CONTACT_ID + "=?";
        String[] selectionArgs = new String[]{id};

        return new CursorLoader(context, uri, null, selection, selectionArgs, null);
    }


    private CursorLoader createAddressesCursorLoader() {

        Uri uri = ContactsContract.Data.CONTENT_URI;
        String selection = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
        String[] selectionArgs = new String[]{id, ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE};

        return new CursorLoader(context, uri, null, selection, selectionArgs, null);
    }

    public List<Phone> parsePhones(Cursor cursor) {

        List<Phone> phones = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst()) {
            do {

                String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                int type = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                type = ContactsContract.CommonDataKinds.Phone.getTypeLabelResource(type);

                phones.add(new Phone(number, type));

            } while (cursor.moveToNext());

            cursor.close();
        }

        return phones;
    }


    public List<Email> parseEmails(Cursor cursor) {
        List<Email> emails = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst()) {

            do {
                String email = cursor.getString(
                        cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                int type = cursor.getInt(
                        cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));
                type = ContactsContract.CommonDataKinds.Email.getTypeLabelResource(type);

                emails.add(new Email(email, type));

            } while (cursor.moveToNext());
            cursor.close();
        }

        return emails;
    }

    public List<Address> parseAddresses(Cursor cursor) {

        List<Address> addresses = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst()) {

            do {
                String street = cursor.getString(
                        cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET));
                String city = cursor.getString(
                        cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY));
                String postalCode = cursor.getString(
                        cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE));
                int type = cursor.getInt(
                        cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.TYPE));
                type = ContactsContract.CommonDataKinds.StructuredPostal.getTypeLabelResource(type);

                addresses.add(new Address(street, city, postalCode, type));

            } while (cursor.moveToNext());
            cursor.close();
        }

        return addresses;
    }


    @Nullable
    public Contact parseContactsCursor(Cursor cursor) {

        long contactId = Long.valueOf(id);
        Contact contact = null;

        if (cursor.getCount() > 0 && cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            Bitmap bitmap = ContactImageLoader.getContactImage(context.getContentResolver(), contactId, true);

            if (name == null) {
                name = context.getString(R.string.unknown);
            }
            contact = new Contact(contactId, name, bitmap);
        }
        cursor.close();

        return contact;
    }


}
