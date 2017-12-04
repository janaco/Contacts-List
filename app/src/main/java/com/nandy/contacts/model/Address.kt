package com.nandy.contacts.model

import android.support.annotation.StringRes

/**
 * Created by yana on 03.12.17.
 */

class Address(val street: String,
              val city: String,
              val postalCode: String,
              @param:StringRes @field:StringRes val type: Int) {

    override fun toString(): String {
        return String.format("%s\n%s, %s", street, city, postalCode)
    }
}
