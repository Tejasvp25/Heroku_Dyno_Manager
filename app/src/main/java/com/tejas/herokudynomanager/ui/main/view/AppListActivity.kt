package com.tejas.herokudynomanager.ui.main.view

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.withResumed
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputEditText
import com.tejas.herokudynomanager.R
import com.tejas.herokudynomanager.ui.main.adapter.AppListAdapter
import com.tejas.herokudynomanager.ui.main.viewmodel.AppListActivityViewModel
import com.tejas.herokudynomanager.utils.DatastorePreference
import com.tejas.herokudynomanager.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_app_list.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


@AndroidEntryPoint
class AppListActivity : AppCompatActivity() {
    private val TAG:String = this.javaClass.simpleName
    private val viewModel: AppListActivityViewModel by viewModels<AppListActivityViewModel>()
    @Inject
    lateinit var mDatastorePreference: DatastorePreference
    private lateinit var apiKey:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_list)
        apiKey = intent.getStringExtra("api_key")!!
        viewModel.setAuthorizationToken(apiKey)
        supportActionBar.let {
            title = "Heroku Apps"
        }
        setupObserver()
    }

    private fun setupObserver(){
        viewModel.apps.observe(this, Observer {
            when(it.status){

                Status.SUCCESS ->
                    it.data?.let {
                        if(it.size > 0) {
                            progress_bar_applist.visibility = View.GONE
                            img_error.visibility = View.GONE
                            text_error_msg.visibility = View.GONE
                            applist_recyclerview.adapter = AppListAdapter(it)
                            applist_recyclerview.visibility = View.VISIBLE
                        }
                    }
                Status.ERROR -> {
                    applist_recyclerview.visibility = View.GONE
                    progress_bar_applist.visibility = View.GONE
                    img_error.visibility = View.VISIBLE
                    text_error_msg.visibility = View.VISIBLE
                    text_error_msg.text = "Check Internet Connection\nAnd\nAPI key"
                }
                Status.LOADING -> {
                    applist_recyclerview.visibility = View.GONE
                    progress_bar_applist.visibility = View.VISIBLE
                    img_error.visibility = View.GONE
                    text_error_msg.visibility = View.GONE
                }
            }
        })
        viewModel.getApps()
        mDatastorePreference.apiKey.asLiveData().observe(this, Observer {
            viewModel.setAuthorizationToken(it!!)
            viewModel.getApps()
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflator = menuInflater
        menuInflator.inflate(R.menu.menu_applist_activity,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Update Api Key")
        val apiKeyInput = TextInputEditText(this)
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        apiKeyInput.layoutParams = layoutParams
        alertDialogBuilder.setView(apiKeyInput)
        alertDialogBuilder.setPositiveButton("Update",DialogInterface.OnClickListener { dialog, which ->
            run {
                runBlocking(Dispatchers.IO) {
                    mDatastorePreference.saveApikey(apiKeyInput.text.toString())
                }
            }
        })
        alertDialogBuilder.setNegativeButton("Cancel",DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })
        alertDialogBuilder.show()
        return true
    }
}