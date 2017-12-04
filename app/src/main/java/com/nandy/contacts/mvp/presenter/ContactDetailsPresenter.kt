package com.nandy.contacts.mvp.presenter

import android.app.LoaderManager
import android.content.Loader
import android.database.Cursor
import android.os.Bundle
import com.nandy.contacts.model.Address
import com.nandy.contacts.model.Contact
import com.nandy.contacts.model.Email
import com.nandy.contacts.model.Phone
import com.nandy.contacts.mvp.BasePresenter
import com.nandy.contacts.mvp.model.ContactDetailsModel
import com.nandy.contacts.mvp.model.ContactDetailsModel.Companion.ADDRESSES_LOADER_ID
import com.nandy.contacts.mvp.model.ContactDetailsModel.Companion.CONTACT_LOADER_ID
import com.nandy.contacts.mvp.model.ContactDetailsModel.Companion.EMAILS_LOADER_ID
import com.nandy.contacts.mvp.model.ContactDetailsModel.Companion.PHONES_LOADER_ID
import com.nandy.contacts.mvp.view.ContactDetailsView

/**
 * Created by yana on 04.12.17.
 */
class ContactDetailsPresenter(
        val view: ContactDetailsView,
        val detailsModel: ContactDetailsModel) : BasePresenter, LoaderManager.LoaderCallbacks<Cursor> {

    override fun start() {
        detailsModel.initLoader(ContactDetailsModel.CONTACT_LOADER_ID, this)
        detailsModel.initLoader(ContactDetailsModel.PHONES_LOADER_ID, this)
        detailsModel.initLoader(ContactDetailsModel.EMAILS_LOADER_ID, this)
        detailsModel.initLoader(ContactDetailsModel.ADDRESSES_LOADER_ID, this)
    }

    override fun stop() {
        detailsModel.destroyLoader(ContactDetailsModel.CONTACT_LOADER_ID)
        detailsModel.destroyLoader(ContactDetailsModel.PHONES_LOADER_ID)
        detailsModel.destroyLoader(ContactDetailsModel.EMAILS_LOADER_ID)
        detailsModel.destroyLoader(ContactDetailsModel.ADDRESSES_LOADER_ID)
        view.unbindViews()
    }

    override fun onLoaderReset(p0: Loader<Cursor>?) {
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor>? {
        return detailsModel.createLoader(id)
    }

    override fun onLoadFinished(loader: Loader<Cursor>?, cursor: Cursor?) {
        when (loader?.getId()) {

            PHONES_LOADER_ID -> {
                val <Phone> phones = detailsModel.parsePhones(cursor)
                if (phones.size > 0) {
                    view.setPhones(phones)
                }
            }
            EMAILS_LOADER_ID -> {

                val <Email> emails = detailsModel.parseEmails(cursor)
                if (emails.size > 0) {
                    view.setEmails(emails)
                }
            }

            ADDRESSES_LOADER_ID -> {
                val <Address> addresses = detailsModel.parseAddresses(cursor)
                if (addresses.size > 0) {
                    view.setAddresses(addresses)
                }
            }

            CONTACT_LOADER_ID -> {
                val contact = detailsModel.parseContactsCursor(cursor)
                if (contact != null) {
                    view.setName(contact.name)
                    view.setPhoto(contact.photoBitmap)
                }

            }
        }

    }
}
