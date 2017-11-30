package com.nandy.contacts.mvp.view;

import android.graphics.Bitmap;

import com.nandy.contacts.model.Address;
import com.nandy.contacts.model.Email;
import com.nandy.contacts.model.Phone;
import com.nandy.contacts.mvp.BaseView;
import com.nandy.contacts.mvp.presenter.ContactDetailsPresenter;

import java.util.List;

/**
 * Created by yana on 30.11.17.
 */

public interface ContactDetailsView extends BaseView<ContactDetailsPresenter>{

    void setPhones(List<Phone> phones);

    void setEmails(List<Email> emails);

    void setAddresses(List<Address> addresses);

    void setName(String name);

    void setPhoto(Bitmap bitmap);

    void unbindViews();
}
