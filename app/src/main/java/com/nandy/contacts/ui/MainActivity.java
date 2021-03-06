package com.nandy.contacts.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nandy.contacts.R;
import com.nandy.contacts.mvp.model.ContactsLoadingModel;
import com.nandy.contacts.mvp.model.NavigationModel;
import com.nandy.contacts.mvp.model.PermissionsModel;
import com.nandy.contacts.mvp.presenter.ContactsListPresenter;
import com.nandy.contacts.mvp.view.ContactsListView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ContactsListView {


    @BindView(R.id.contacts_list)
    RecyclerView contactsList;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.alert_message)
    TextView alertMessageView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private ContactsListPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        contactsList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        ContactsListPresenter presenter = new ContactsListPresenter(this);
        presenter.setContactsLoadingModel(new ContactsLoadingModel(this));
        presenter.setPermissionsModel(new PermissionsModel(this));
        presenter.setNavigationModel(new NavigationModel(this));
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
        presenter.stop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        presenter.onRequestPermissionsResult(requestCode);
    }

    @Override
    public void setPresenter(ContactsListPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public <T extends RecyclerView.Adapter> void setContactsAdapter(T adapter) {
        contactsList.setAdapter(adapter);
    }

    @Override
    public void setErrorMessage(int resId) {
        alertMessageView.setText(resId);
    }

    @Override
    public void setErrorMessageVisible(boolean visible) {
        alertMessageView.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setLoadingProgressEnabled(boolean enabled) {
        progressBar.setVisibility(enabled ? View.VISIBLE : View.GONE);
    }
}