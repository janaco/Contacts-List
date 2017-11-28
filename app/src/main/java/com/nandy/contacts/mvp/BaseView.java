package com.nandy.contacts.mvp;

/**
 * Created by yana on 28.11.17.
 */

public interface BaseView<P extends BasePresenter> {

    void setPresenter(P presenter);
}
