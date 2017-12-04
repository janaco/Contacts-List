package com.nandy.contacts.adapter

import android.graphics.Bitmap
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.nandy.contacts.OnListItemClickListener
import com.nandy.contacts.R
import com.nandy.contacts.model.Contact

/**
 * Created by yana on 03.12.17.
 */

class ContactsAdapter(val contacts: MutableList<Contact>) : RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {
    var onListItemClickListener: OnListItemClickListener<Contact>? = null

    override fun getItemCount(): Int {
        return contacts.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(
                LayoutInflater.from(parent?.context).inflate(R.layout.item_contact, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val contact = contacts[position]

        holder?.setName(contact.name)
        holder?.setPhoto(contact.photoBitmap)
        holder?.setOnItemClickListener(
                View.OnClickListener {
                    onListItemClickListener?.onListItemClick(contact, holder.adapterPosition)
                })
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.name)
        lateinit var nameView: TextView
        @BindView(R.id.photo)
        lateinit var photoView: ImageView

        init {
            ButterKnife.bind(this, itemView)
        }

        fun setName(name: String?) {
            nameView.text = name
        }

        fun setPhoto(photo: Bitmap?) {
            photoView.setImageBitmap(photo)
        }

        fun setOnItemClickListener(onClickListener: View.OnClickListener?) {
            itemView.setOnClickListener(onClickListener)
        }
    }
}