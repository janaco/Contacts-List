package com.nandy.contacts.mvp

/**
 * Created by yana on 03.12.17.
 */
interface BaseView<in BasePresenter>{

    fun setPresenter(presenter: BasePresenter)
}