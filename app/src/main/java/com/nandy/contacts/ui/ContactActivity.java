package com.nandy.contacts.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nandy.contacts.R;
import com.nandy.contacts.model.Address;
import com.nandy.contacts.model.Email;
import com.nandy.contacts.model.Phone;
import com.nandy.contacts.mvp.model.ContactDetailsModel;
import com.nandy.contacts.mvp.presenter.ContactDetailsPresenter;
import com.nandy.contacts.mvp.view.ContactDetailsView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yana on 28.11.17.
 */

public class ContactActivity extends AppCompatActivity implements ContactDetailsView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.image)
    ImageView imageView;

    @BindView(R.id.content)
    LinearLayout layoutContent;

    private ContactDetailsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        String id = String.valueOf(getIntent().getLongExtra("id", -1));
        ContactDetailsPresenter presenter = new ContactDetailsPresenter(this);
        presenter.setDetailsModel(new ContactDetailsModel(getApplicationContext(), getLoaderManager(), id));
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
    public void setPresenter(ContactDetailsPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setPhones(List<Phone> phones) {

        View phonesViewGroup = LayoutInflater.from(getApplicationContext()).inflate(
                R.layout.content__block, null);
        ImageView contentIcon = phonesViewGroup.findViewById(R.id.content_icon);
        LinearLayout container = phonesViewGroup.findViewById(R.id.container);

        contentIcon.setImageResource(R.drawable.phone);

        for (Phone phone : phones) {

            View phoneView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_row, null);
            TextView mainTextView = phoneView.findViewById(R.id.main_text);
            TextView subTextView = phoneView.findViewById(R.id.sub_text);
            ImageView actionIcon = phoneView.findViewById(R.id.action_icon);

            actionIcon.setVisibility(View.VISIBLE);
            mainTextView.setText(phone.getNumber());
            subTextView.setText(phone.getType());

            container.addView(phoneView, container.getChildCount() - 1);

        }

        layoutContent.addView(phonesViewGroup, 0);

    }

    @Override
    public void setEmails(List<Email> emails) {
        View viewGroup = LayoutInflater.from(getApplicationContext()).inflate(
                R.layout.content__block, null);
        ImageView contentIcon = viewGroup.findViewById(R.id.content_icon);
        LinearLayout container = viewGroup.findViewById(R.id.container);

        contentIcon.setImageResource(R.drawable.email);

        for (Email email : emails) {

            View phoneView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_row, null);
            TextView mainTextView = phoneView.findViewById(R.id.main_text);
            TextView subTextView = phoneView.findViewById(R.id.sub_text);

            mainTextView.setText(email.getEmail());
            subTextView.setText(email.getType());

            container.addView(phoneView, container.getChildCount() - 1);

        }

        int childCount = layoutContent.getChildCount();
        int position = childCount > 1 ? 1 : childCount - 1;
        layoutContent.addView(viewGroup, position);
    }

    @Override
    public void setAddresses(List<Address> addresses) {
        View viewGroup = LayoutInflater.from(getApplicationContext()).inflate(
                R.layout.content__block, null);
        ImageView contentIcon = viewGroup.findViewById(R.id.content_icon);
        LinearLayout container = viewGroup.findViewById(R.id.container);

        contentIcon.setImageResource(R.drawable.map_marker);

        for (Address address : addresses) {

            View phoneView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_row, null);
            TextView mainTextView = phoneView.findViewById(R.id.main_text);
            TextView subTextView = phoneView.findViewById(R.id.sub_text);

            mainTextView.setText(address.toString());
            subTextView.setText(address.getType());

            container.addView(phoneView, container.getChildCount() - 1);

        }

        int childCount = layoutContent.getChildCount();
        int position = childCount == 2 ? 2 : childCount - 1;
        layoutContent.addView(viewGroup, position);
    }

    @Override
    public void setName(String name) {
        collapsingToolbar.setTitle(name);
    }

    @Override
    public void setPhoto(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }
}
