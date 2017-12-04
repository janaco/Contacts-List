package com.nandy.contacts.mvp.model

import android.content.Context
import android.content.Intent
import com.nandy.contacts.model.Contact
import com.nandy.contacts.ui.ContactActivity

/**
 * Created by yana on 04.12.17.
 */
class NavigationModel(val context: Context) {

    fun startContactActivity(contact: Contact) {
        val intent = Intent(context, ContactActivity::class.java)
        intent.putExtra("id", contact.id)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        context.startActivity(intent)

    }
}