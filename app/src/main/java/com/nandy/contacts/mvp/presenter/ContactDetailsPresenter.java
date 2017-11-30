package com.nandy.contacts.mvp.presenter;

import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;

import com.nandy.contacts.model.Address;
import com.nandy.contacts.model.Contact;
import com.nandy.contacts.model.Email;
import com.nandy.contacts.model.Phone;
import com.nandy.contacts.mvp.BasePresenter;
import com.nandy.contacts.mvp.model.ContactDetailsModel;
import com.nandy.contacts.mvp.view.ContactDetailsView;

import java.util.List;

/**
 * Created by yana on 30.11.17.
 */

public class ContactDetailsPresenter implements BasePresenter, LoaderManager.LoaderCallbacks<Cursor> {

    private ContactDetailsView view;
    private ContactDetailsModel detailsModel;

    public ContactDetailsPresenter(ContactDetailsView view) {
        this.view = view;
    }

    public void setDetailsModel(ContactDetailsModel detailsModel) {
        this.detailsModel = detailsModel;
    }

    @Override
    public void start() {
        initLoaders();
    }

    @Override
    public void stop() {

    }

    private void initLoaders() {
        detailsModel.initLoader(ContactDetailsModel.CONTACT_LOADER_ID, this);
        detailsModel.initLoader(ContactDetailsModel.PHONES_LOADER_ID, this);
        detailsModel.initLoader(ContactDetailsModel.EMAILS_LOADER_ID, this);
        detailsModel.initLoader(ContactDetailsModel.ADDRESSES_LOADER_ID, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
        return detailsModel.createLoader(id);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        switch (loader.getId()) {

            case ContactDetailsModel.PHONES_LOADER_ID:
                List<Phone> phones = detailsModel.parsePhones(cursor);
                if (phones.size() > 0) {
                    view.setPhones(phones);
                }
                break;

            case ContactDetailsModel.EMAILS_LOADER_ID:
                List<Email> emails = detailsModel.parseEmails(cursor);
                if (emails.size() > 0) {
                    view.setEmails(emails);
                }
                break;

            case ContactDetailsModel.ADDRESSES_LOADER_ID:
                List<Address> addresses = detailsModel.parseAddresses(cursor);
                if (addresses.size() > 0) {
                    view.setAddresses(addresses);
                }
                break;

            case ContactDetailsModel.CONTACT_LOADER_ID:
                Contact contact = detailsModel.parseContactsCursor(cursor);
                if (contact != null) {
                    view.setName(contact.getName());
                    view.setPhoto(contact.getPhotoBitmap());
                }
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
