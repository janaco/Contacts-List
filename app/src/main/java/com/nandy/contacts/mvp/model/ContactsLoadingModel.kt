package com.nandy.contacts.mvp.model

import android.app.Activity
import android.app.LoaderManager
import android.content.CursorLoader
import android.content.Loader
import android.database.Cursor
import android.graphics.Bitmap
import android.provider.ContactsContract
import com.nandy.contacts.BitmapUtils
import com.nandy.contacts.ContactImageLoader
import com.nandy.contacts.R
import com.nandy.contacts.model.Contact

/**
 * Created by yana on 04.12.17.
 */
class ContactsLoadingModel(val activity: Activity) {

    private companion object {
        @JvmField
        val CONTACTS_LOADER_ID = 1
        @JvmField
        val PROJECTION: Array<String> = arrayOf(
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.PHOTO_URI)
    }

    fun initLoader(loaderCallbacks: LoaderManager.LoaderCallbacks<Cursor>) {
        activity.loaderManager.initLoader(CONTACTS_LOADER_ID, null, loaderCallbacks)
    }

    fun createLoader(id: Int): Loader<Cursor>? {
        if (id == CONTACTS_LOADER_ID) {
            return contactsLoader()
        }
        return null
    }

    private fun contactsLoader(): Loader<Cursor> {

        return CursorLoader(activity, ContactsContract.Contacts.CONTENT_URI, PROJECTION, null, arrayOfNulls(0), null)
    }

    fun parseContactsCursor(cursor: Cursor?): MutableList<Contact> {

        val contacts = mutableListOf<Contact>()

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getLong(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                    var name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                    val bitmap = getContactPhoto(id, name)

                    if (name == null) {
                        name = activity.getString(R.string.unknown)
                    }
                    contacts.add(Contact(id, name, bitmap))

                } while (cursor.moveToNext())
            }

            cursor.close()
        }
        return contacts
    }

    private fun getContactPhoto(id: Long, name: String?): Bitmap {
        var bitmap = ContactImageLoader.getContactImage(activity.contentResolver, id, false)
        if (bitmap == null) {
            bitmap = BitmapUtils.drawTextToBitmap(activity, getNameInitials(name))
        } else {
            bitmap = BitmapUtils.convertToCircle(bitmap)
        }

        return bitmap
    }

    private fun getNameInitials(name: String?): String {
        return if (name != null) {
            val textArray: List<String> = name.split(" ".toRegex())
            val builder = StringBuilder()
            textArray.forEach { builder.append(it[0]) }
            builder.toString().toUpperCase()
        } else {
            "?"
        }
    }
}
