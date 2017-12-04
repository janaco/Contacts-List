package com.nandy.contacts.ui

import android.graphics.Bitmap
import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.nandy.contacts.R
import com.nandy.contacts.model.Address
import com.nandy.contacts.model.Email
import com.nandy.contacts.model.Phone
import com.nandy.contacts.mvp.model.ContactDetailsModel
import com.nandy.contacts.mvp.presenter.ContactDetailsPresenter
import com.nandy.contacts.mvp.view.ContactDetailsView

/**
 * Created by yana on 03.12.17.
 */

class ContactActivity : AppCompatActivity(), ContactDetailsView {

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    @BindView(R.id.collapsing_toolbar)
    lateinit var collapsingToolbar: CollapsingToolbarLayout

    @BindView(R.id.image)
    lateinit var imageView: ImageView

    @BindView(R.id.content)
    lateinit var layoutContent: LinearLayout

    var contactPresenter: ContactDetailsPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)
        ButterKnife.bind(this)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val id = intent.getLongExtra("id", -1).toString()
        val presenter = ContactDetailsPresenter(this, ContactDetailsModel(applicationContext, loaderManager, id))
        setPresenter(presenter)
    }

    override fun onStart() {
        super.onStart()
        contactPresenter?.start()
    }

    override fun onStop() {
        super.onStop()
        contactPresenter?.stop()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.contact_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        return when (item?.itemId) {

            R.id.action_edit, R.id.action_remove -> {
                Toast.makeText(applicationContext, getString(R.string.not_implemented), Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }


    }

    override fun setPresenter(presenter: ContactDetailsPresenter) {
        this.contactPresenter = presenter
    }

    override fun setPhones(phones: MutableList<Phone>) {

        val phonesViewGroup = LayoutInflater.from(applicationContext).inflate(R.layout.content__block, null)
        val contentIcon: ImageView = phonesViewGroup.findViewById(R.id.content_icon)
        val container: LinearLayout = phonesViewGroup.findViewById(R.id.container)

        contentIcon.setImageResource(R.drawable.phone)

        phones?.forEach({
            val phoneView = LayoutInflater.from(applicationContext).inflate(R.layout.item_row, null)
            val mainTextView: TextView = phoneView.findViewById(R.id.main_text)
            val subTextView: TextView = phoneView.findViewById(R.id.sub_text)
            val actionIcon: ImageView = phoneView.findViewById(R.id.action_icon)

            actionIcon.visibility = View.VISIBLE
            mainTextView.text = it.number
            subTextView.setText(it.type)

            container.addView(phoneView, container.childCount - 1)
        })

        layoutContent.addView(phonesViewGroup, 0)
    }

    override fun setEmails(emails: MutableList<Email>) {
        val viewGroup = LayoutInflater.from(applicationContext).inflate(R.layout.content__block, null)
        val contentIcon: ImageView = viewGroup.findViewById(R.id.content_icon)
        val container: LinearLayout = viewGroup.findViewById(R.id.container)

        contentIcon.setImageResource(R.drawable.email)

        emails?.forEach {
            val childView = LayoutInflater.from(applicationContext).inflate(R.layout.item_row, null)
            val mainTextView: TextView = childView.findViewById(R.id.main_text)
            val subTextView: TextView = childView.findViewById(R.id.sub_text)

            mainTextView.text = it.email
            subTextView.setText(it.type)

            container.addView(childView, container.childCount - 1)
        }

        val childCount = layoutContent.childCount
        val position = if (childCount > 1) 1 else childCount - 1
        layoutContent.addView(viewGroup, position)
    }

    override fun setAddresses(addresses: MutableList<Address>) {

        val viewGroup = LayoutInflater.from(applicationContext).inflate(R.layout.content__block, null)
        val contentIcon: ImageView = viewGroup.findViewById(R.id.content_icon)
        val container: LinearLayout = viewGroup.findViewById(R.id.container)

        contentIcon.setImageResource(R.drawable.map_marker)

        addresses?.forEach {
            val childView = LayoutInflater.from(applicationContext).inflate(R.layout.item_row, null)
            val mainTextView: TextView = childView.findViewById(R.id.main_text)
            val subTextView: TextView = childView.findViewById(R.id.sub_text)

            mainTextView.text = it.toString()
            subTextView.setText(it.type)

            container.addView(childView, container.childCount - 1)
        }

        val childCount = layoutContent.childCount
        val position = if (childCount == 2) 2 else childCount - 1
        layoutContent.addView(viewGroup, position)
    }

    override fun setName(name: String) {
        collapsingToolbar.title = name
    }

    override fun setPhoto(bitmap: Bitmap?) {
        imageView.setImageBitmap(bitmap)
    }

    override fun unbindViews() {
        layoutContent.removeAllViews()
    }




}

