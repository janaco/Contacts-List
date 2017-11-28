package com.nandy.contacts.mvp.presenter;

import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;

import com.nandy.contacts.adapter.ContactsAdapter;
import com.nandy.contacts.model.Contact;
import com.nandy.contacts.mvp.BasePresenter;
import com.nandy.contacts.mvp.model.ContactsLoadingModel;
import com.nandy.contacts.mvp.model.PermissionsModel;
import com.nandy.contacts.mvp.view.ContactsListView;

import java.util.List;

/**
 * Created by yana on 28.11.17.
 */

public class ContactsListPresenter implements BasePresenter, LoaderManager.LoaderCallbacks<Cursor> {

    private ContactsListView view;
    private ContactsLoadingModel contactsLoadingModel;
    private PermissionsModel permissionsModel;

    private ContactsAdapter adapter;

    public ContactsListPresenter(ContactsListView view) {
        this.view = view;
    }

    public void setPermissionsModel(PermissionsModel permissionsModel) {
        this.permissionsModel = permissionsModel;
    }

    public void setContactsLoadingModel(ContactsLoadingModel contactsLoadingModel) {
        this.contactsLoadingModel = contactsLoadingModel;
    }

    @Override
    public void start() {

        if (!permissionsModel.hasPermissionsToReadContacts()) {
            permissionsModel.requestReadContactsPermission();
            return;
        }

        initLoader();
    }

    @Override
    public void destroy() {

    }

    private void initLoader() {
        contactsLoadingModel.initLoader(this);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
        return contactsLoadingModel.createLoader(id);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        List<Contact> contacts = contactsLoadingModel.parseContactsCursor(cursor);
        adapter = new ContactsAdapter(contacts);
        view.setContactsAdapter(adapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    public void onRequestPermissionsResult(int requestCode) {

        if (requestCode == PermissionsModel.REQUEST_READ_CONTACTS
                && permissionsModel.hasPermissionsToReadContacts()) {
            initLoader();
        }
    }
}
