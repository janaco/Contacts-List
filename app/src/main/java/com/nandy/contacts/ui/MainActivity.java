package com.nandy.contacts.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.nandy.contacts.R;
import com.nandy.contacts.mvp.model.ContactsLoadingModel;
import com.nandy.contacts.mvp.presenter.ContactsListPresenter;
import com.nandy.contacts.mvp.view.ContactsListView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ContactsListView {


    @BindView(R.id.contacts_list)
    RecyclerView contactsList;

    private ContactsListPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        contactsList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        ContactsListPresenter presenter = new ContactsListPresenter(this);
        presenter.setContactsLoadingModel(new ContactsLoadingModel(this));
        setPresenter(presenter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.destroy();
    }

    @Override
    public void setPresenter(ContactsListPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public <T extends RecyclerView.Adapter> void setContactsAdapter(T adapter) {
        contactsList.setAdapter(adapter);
    }


}