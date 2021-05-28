package com.tejas.herokudynomanager.ui.main.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import androidx.lifecycle.asLiveData
import com.tejas.herokudynomanager.R
import com.tejas.herokudynomanager.utils.DatastorePreference
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {
    @Inject
    lateinit var mDatastorePreference: DatastorePreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
    }

    override fun onStart() {
        super.onStart()
        mDatastorePreference.apiKey
            .asLiveData()
            .observe(this, {
                val timer = object: CountDownTimer(1500, 100) {
                    override fun onTick(millisUntilFinished: Long) {}
                    override fun onFinish() {
                        if(it!!.equals("")){
                            val intent = Intent(applicationContext,MainActivity::class.java)
                            startActivity(intent)
                        }else{
                            val intent = Intent(applicationContext,AppListActivity::class.java)
                            intent.putExtra("api_key",it)
                            startActivity(intent)
                        }
                    }
                }
                timer.start()
            })
    }
}