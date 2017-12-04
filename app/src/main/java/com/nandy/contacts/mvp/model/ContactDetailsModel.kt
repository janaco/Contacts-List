package com.nandy.contacts.mvp.model

import android.app.LoaderManager
import android.content.Context
import android.content.CursorLoader
import android.content.Loader
import android.database.Cursor
import android.provider.ContactsContract
import com.nandy.contacts.ContactImageLoader
import com.nandy.contacts.R
import com.nandy.contacts.model.Address
import com.nandy.contacts.model.Contact
import com.nandy.contacts.model.Email
import com.nandy.contacts.model.Phone

/**
 * Created by yana on 04.12.17.
 */

class ContactDetailsModel(
        val context: Context,
        val loaderManager: LoaderManager,
        val id: String) {

    companion object {
        @JvmField
        val PHONES_LOADER_ID = 11
        @JvmField
        val EMAILS_LOADER_ID = 11
        @JvmField
        val ADDRESSES_LOADER_ID = 11
        @JvmField
        val CONTACT_LOADER_ID = 11
    }

    fun initLoader(id: Int, loaderCallbacks: LoaderManager.LoaderCallbacks<Cursor>) {
        loaderManager.initLoader(id, null, loaderCallbacks)
    }

    fun destroyLoader(id: Int) {
        loaderManager.destroyLoader(id)
    }

    fun createLoader(id: Int): Loader<Cursor>? {

        return when (id) {
            PHONES_LOADER_ID -> createPhonesCursorLoader()
            EMAILS_LOADER_ID -> createEmailsCursorLoader()
            ADDRESSES_LOADER_ID -> createAddressesCursorLoader()
            CONTACT_LOADER_ID -> createContactsLoader()
            else -> null

        }
    }

    private fun createContactsLoader(): Loader<Cursor> {

        val uri = ContactsContract.Contacts.CONTENT_URI
        val selection = ContactsContract.Contacts._ID + "=?"
        val selectionArgs: Array<String> = arrayOf(id)
        val projection: Array<String> = arrayOf(
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.PHOTO_URI
        )

        return CursorLoader(context, uri, projection, selection, selectionArgs, null)
    }

    private fun createPhonesCursorLoader(): Loader<Cursor> {
        val uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        val selection = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?"
        val selectionArgs: Array<String> = arrayOf(id)

        return CursorLoader(context, uri, null, selection, selectionArgs, null)

    }

    private fun createEmailsCursorLoader(): Loader<Cursor> {
        val uri = ContactsContract.CommonDataKinds.Email.CONTENT_URI
        val selection = ContactsContract.CommonDataKinds.Email.CONTACT_ID + "=?"
        val selectionArgs: Array<String> = arrayOf(id)

        return CursorLoader(context, uri, null, selection, selectionArgs, null)


    }

    private fun createAddressesCursorLoader(): Loader<Cursor> {
        val uri = ContactsContract.Data.CONTENT_URI
        val selection = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?"
        val selectionArgs: Array<String> = arrayOf(id, ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE)

        return CursorLoader(context, uri, null, selection, selectionArgs, null)


    }

    fun parsePhones(cursor: Cursor?): MutableList<Phone> {
        val phones = mutableListOf<Phone>()

        if (cursor != null && cursor.moveToFirst()) {
            do {

                val number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                val type = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE))
                val typeResId = ContactsContract.CommonDataKinds.Phone.getTypeLabelResource(type)

                phones.add(Phone(number, typeResId))

            } while (cursor.moveToNext())

            cursor.close()
        }

        return phones
    }

    fun parseEmails(cursor: Cursor?): MutableList<Email> {
        val emails = mutableListOf<Email>()

        if (cursor != null && cursor.moveToFirst()) {
            do {

                val email = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA))
                val type = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE))
                val typeResId = ContactsContract.CommonDataKinds.Email.getTypeLabelResource(type)

                emails.add(Email(email, typeResId))

            } while (cursor.moveToNext())

            cursor.close()
        }

        return emails
    }

    fun parseAddresses(cursor: Cursor?): MutableList<Address> {
        val addresses = mutableListOf<Address>()

        if (cursor != null && cursor.moveToFirst()) {
            do {

                val street = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET))
                val city = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY))
                val postalCode = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE))
                val type = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.TYPE))
                val typeResId = ContactsContract.CommonDataKinds.StructuredPostal.getTypeLabelResource(type)


                addresses.add(Address(street, city, postalCode, typeResId))

            } while (cursor.moveToNext())

            cursor.close()
        }

        return addresses
    }


    fun parseContactsCursor(cursor: Cursor?): Contact? {
        var contact: Contact? = null
        val contactId = id.toLong()

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {

                    var name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                    val bitmap = ContactImageLoader.getContactImage(context.contentResolver, contactId, true)

                    if (name == null) {
                        name = context.getString(R.string.unknown)
                    }
                    contact = Contact(contactId, name, bitmap)

                } while (cursor.moveToNext())

                cursor.close()
            }
        }

        return contact
    }
}