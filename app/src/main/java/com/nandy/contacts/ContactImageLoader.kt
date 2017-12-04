package com.nandy.contacts

import android.content.ContentResolver
import android.content.ContentUris
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.provider.ContactsContract
import org.jetbrains.annotations.Nullable
import java.io.InputStream


/**
 * Created by yana on 03.12.17.
 */
class ContactImageLoader {

    companion object {


        @JvmStatic
        @Nullable
        fun getContactImage(contentResolver: ContentResolver, id: Long, hightQuality: Boolean): Bitmap? {

            val uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, id)
            val inputStream: InputStream? = ContactsContract.Contacts.openContactPhotoInputStream(contentResolver, uri, hightQuality)


            if (inputStream != null){
                return BitmapFactory.decodeStream(inputStream)
            }

            return null

        }
    }

}