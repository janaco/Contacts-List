package com.nandy.contacts.model

import android.graphics.Bitmap

/**
 * Created by yana on 03.12.17.
 */

class Contact(val id: Long,
              val name: String,
              val photoBitmap: Bitmap?): Comparable<Contact>{

    override fun compareTo(other: Contact): Int {
        return name.compareTo(other.name)
    }
}
