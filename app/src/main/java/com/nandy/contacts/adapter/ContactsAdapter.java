package com.nandy.contacts.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nandy.contacts.OnListItemClickListener;
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
    private OnListItemClickListener<Contact> onListItemClickListener;

    public ContactsAdapter(List<Contact> contacts){
        this.contacts = contacts;
    }

    public void setOnListItemClickListener(OnListItemClickListener<Contact> onListItemClickListener) {
        this.onListItemClickListener = onListItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final Contact contact = contacts.get(position);

        holder.setName(contact.getName());
        holder.setPhoto(contact.getPhotoBitmap());
        holder.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onListItemClickListener != null){
                    onListItemClickListener.onListItemClick(contact, holder.getAdapterPosition());
                }
            }
        });

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

        private View itemView;

        ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;

            ButterKnife.bind(this, itemView);
        }

        void setName(String name){
            nameView.setText(name);
        }

        void setPhoto(Bitmap photo){
            photoView.setImageBitmap(photo);
        }

        void setOnItemClickListener(View.OnClickListener onClickListener){
            itemView.setOnClickListener(onClickListener);
        }


    }

}
