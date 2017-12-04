package com.nandy.contacts.mvp.presenter

import android.app.LoaderManager
import android.content.Loader
import android.database.Cursor
import android.os.Bundle
import com.nandy.contacts.OnListItemClickListener
import com.nandy.contacts.R
import com.nandy.contacts.adapter.ContactsAdapter
import com.nandy.contacts.model.Contact
import com.nandy.contacts.mvp.BasePresenter
import com.nandy.contacts.mvp.model.ContactsLoadingModel
import com.nandy.contacts.mvp.model.NavigationModel
import com.nandy.contacts.mvp.model.PermissionsModel
import com.nandy.contacts.mvp.view.ContactsListView
import java.util.*

/**
 * Created by yana on 04.12.17.
 */

class ContactsListPresenter(
        val view: ContactsListView,
        val contactsLoadingModel: ContactsLoadingModel,
        val permissionsModel: PermissionsModel,
        val navigationModel: NavigationModel) : BasePresenter, LoaderManager.LoaderCallbacks<Cursor>, OnListItemClickListener<Contact> {

    var adapter: ContactsAdapter? = null

    override fun start() {
        if (!permissionsModel.hasPermissionsToReadContacts()) {
            view.setErrorMessage(R.string.no_permissions)
            view.setErrorMessageVisible(true)
            permissionsModel.requestReadContactsPermission()
            return
        }
        initLoader()
    }

    override fun stop() {
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor>? {
        return contactsLoadingModel.createLoader(id)
    }

    override fun onLoadFinished(loader: Loader<Cursor>?, cursor: Cursor?) {
        val contacts = contactsLoadingModel.parseContactsCursor(cursor)
        Collections.sort(contacts)
        adapter =  ContactsAdapter(contacts)
        adapter?.onListItemClickListener = this
        view.setContactsAdapter(adapter)
        view.setLoadingProgressEnabled(false)
    }

    override fun onLoaderReset(loader: Loader<Cursor>?) {
    }

    override fun onListItemClick(item: Contact, position: Int) {
        navigationModel.startContactActivity(item)
    }

    fun initLoader() {
        view.setErrorMessageVisible(false)
        view.setLoadingProgressEnabled(true)

        contactsLoadingModel.initLoader(this)
    }

    fun onRequestPermissionsResult(requestCode: Int){
        if (requestCode == PermissionsModel.REQUEST_READ_CONTACTS
                && permissionsModel.hasPermissionsToReadContacts()) {
            initLoader()
        }
    }

}
