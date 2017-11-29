package com.nandy.contacts.mvp.model;

import android.content.Context;
import android.content.Intent;

import com.nandy.contacts.model.Contact;
import com.nandy.contacts.ui.ContactActivity;

/**
 * Created by yana on 29.11.17.
 */

public class NavigationModel {

    private Context context;

    public NavigationModel(Context context) {
        this.context = context;
    }

    public void startContactActivity(Contact contact){

        Intent intent = new Intent(context, ContactActivity.class);
        intent.putExtra("id", contact.getId());

        context.startActivity(intent);
    }

}
