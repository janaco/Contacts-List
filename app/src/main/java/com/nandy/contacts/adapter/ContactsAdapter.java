package com.nandy.contacts.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nandy.contacts.ContactImageLoader;
import com.nandy.contacts.R;
import com.nandy.contacts.model.Contact;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yana on 28.11.17.
 */

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder>{

    private List<Contact> contacts;

    public ContactsAdapter(List<Contact> contacts){
        this.contacts = contacts;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Contact contact = contacts.get(position);

        holder.setName(contact.getName());
        holder.setPhoto(ContactImageLoader.getContactImage(holder.context.getContentResolver(), contact));

    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.name)
        TextView nameView;
        @BindView(R.id.photo)
        ImageView photoView;

        Context context;

        public ViewHolder(View itemView) {
            super(itemView);
            context  = itemView.getContext();

            ButterKnife.bind(this, itemView);
        }

        void setName(String name){
            nameView.setText(name);
        }

        void setPhoto(Bitmap photo){
            photoView.setImageBitmap(photo);
        }

    }

}
