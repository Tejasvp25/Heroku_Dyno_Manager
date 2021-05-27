package com.tejas.herokudynomanager.ui.main.view


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.withCreated
import com.tejas.herokudynomanager.R
import com.tejas.herokudynomanager.ui.main.viewmodel.AppListActivityViewModel
import com.tejas.herokudynomanager.utils.DatastorePreference
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    val TAG: String = this::class.java.simpleName


    @Inject lateinit var mDatastorePreference:DatastorePreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        mDatastorePreference.apiKey
            .asLiveData()
            .observe(this,{
                if(it != ""){
                    val intent = Intent(applicationContext,AppListActivity::class.java)
                    intent.putExtra("api_key",it)
                    startActivity(intent)
                }
            })
    }
    fun onNextClick(view: View){
        if (!input_api_key.text.toString().isBlank()){
            CoroutineScope(Dispatchers.Main).launch {
                mDatastorePreference.saveApikey(input_api_key.text.toString())
                val intent = Intent(applicationContext,AppListActivity::class.java)
                intent.putExtra("api_key",input_api_key.text.toString())
                startActivity(intent)
            }
        }else{
            input_api_key.error = "Please Enter Api Key"
        }
    }
}




