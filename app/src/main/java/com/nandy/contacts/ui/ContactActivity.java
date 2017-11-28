package com.nandy.contacts.ui;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.nandy.contacts.R;
import com.nandy.contacts.adapter.ContactsAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yana on 28.11.17.
 */

public class ContactActivity extends AppCompatActivity {

    @BindView(R.id.contacts_list)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;


    private ContactsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        collapsingToolbar.setTitle("Expand");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

    }
}
