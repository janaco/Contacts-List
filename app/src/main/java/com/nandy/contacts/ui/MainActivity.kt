package com.nandy.contacts.ui

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.nandy.contacts.R
import com.nandy.contacts.mvp.model.ContactsLoadingModel
import com.nandy.contacts.mvp.model.NavigationModel
import com.nandy.contacts.mvp.model.PermissionsModel
import com.nandy.contacts.mvp.presenter.ContactsListPresenter
import com.nandy.contacts.mvp.view.ContactsListView

/**
 * Created by yana on 03.12.17.
 */
class MainActivity : AppCompatActivity(), ContactsListView {

    @BindView(R.id.contacts_list)
    lateinit var contactsList: RecyclerView

    @BindView(R.id.progressBar)
    lateinit var progressBar: ProgressBar

    @BindView(R.id.alert_message)
    lateinit var alertMessageView: TextView

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    var contactListPresenter: ContactsListPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)

        setSupportActionBar(toolbar)
        contactsList.layoutManager = LinearLayoutManager(applicationContext)

        val presenter = ContactsListPresenter(
                this,
                ContactsLoadingModel(this),
                PermissionsModel(this),
                NavigationModel(applicationContext))
        setPresenter(presenter)
    }

    override fun onStart() {
        super.onStart()
        contactListPresenter?.start()
    }

    override fun onStop() {
        super.onStop()
        contactListPresenter?.stop()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        contactListPresenter?.onRequestPermissionsResult(requestCode)
    }

    override fun setPresenter(presenter: ContactsListPresenter) {
        this.contactListPresenter = presenter
    }

    override fun <T : RecyclerView.Adapter<*>> setContactsAdapter(adapter: T?) {
        contactsList.adapter = adapter
    }

    override fun setErrorMessage(resId: Int) {
        alertMessageView.setText(resId)
    }

    override fun setErrorMessageVisible(visible: Boolean) {
        alertMessageView.visibility = if (visible) View.VISIBLE else View.GONE
    }

    override fun setLoadingProgressEnabled(enabled: Boolean) {
        progressBar.visibility = if (enabled) View.VISIBLE else View.GONE
    }


}
