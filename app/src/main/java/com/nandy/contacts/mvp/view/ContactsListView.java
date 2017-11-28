package com.nandy.contacts.mvp.view;

import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;

import com.nandy.contacts.mvp.BaseView;
import com.nandy.contacts.mvp.presenter.ContactsListPresenter;

/**
 * Created by yana on 28.11.17.
 */

public interface ContactsListView extends BaseView<ContactsListPresenter>{

    <T extends RecyclerView.Adapter>void setContactsAdapter(T adapter);

    void setErrorMessage(@StringRes int resId);

    void setErrorMessageVisible(boolean visible);

    void setLoadingProgressEnabled(boolean enabled);
}
