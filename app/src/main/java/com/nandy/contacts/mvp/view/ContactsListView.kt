package com.nandy.contacts.mvp.view

import android.support.annotation.StringRes
import android.support.v7.widget.RecyclerView
import com.nandy.contacts.mvp.BaseView
import com.nandy.contacts.mvp.presenter.ContactsListPresenter

/**
 * Created by yana on 03.12.17.
 */


interface ContactsListView : BaseView<ContactsListPresenter> {

    fun <T : RecyclerView.Adapter<*>> setContactsAdapter(adapter: T?)

    fun setErrorMessage(@StringRes resId: Int)

    fun setErrorMessageVisible(visible: Boolean)

    fun setLoadingProgressEnabled(enabled: Boolean)
}
