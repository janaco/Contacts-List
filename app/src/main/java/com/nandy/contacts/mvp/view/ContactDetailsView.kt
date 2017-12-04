package com.nandy.contacts.mvp.view

import android.graphics.Bitmap
import com.nandy.contacts.model.Address
import com.nandy.contacts.model.Email
import com.nandy.contacts.model.Phone
import com.nandy.contacts.mvp.BaseView
import com.nandy.contacts.mvp.presenter.ContactDetailsPresenter

/**
 * Created by yana on 03.12.17.
 */
interface ContactDetailsView: BaseView<ContactDetailsPresenter>{

    fun setPhones(phones: MutableList<Phone>)

    fun setEmails(emails: MutableList<Email>)

    fun setAddresses(addresses: MutableList<Address>)

    fun setName(name: String)

    fun setPhoto(bitmap: Bitmap?)

    fun unbindViews()
}