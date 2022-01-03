package com.tejas.herokudynomanager.ui.main.view

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.tejas.herokudynomanager.R
import com.tejas.herokudynomanager.network.models.DynoFormationUpdatePayload
import com.tejas.herokudynomanager.network.models.HerokuApp
import com.tejas.herokudynomanager.network.models.UpdatesItem
import com.tejas.herokudynomanager.ui.main.view.fragment.AppInfoBottomSheetDialogFragment
import com.tejas.herokudynomanager.ui.main.viewmodel.AppInfoViewModel
import com.tejas.herokudynomanager.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_app_info.*
import kotlinx.android.synthetic.main.listitem_dyno_state.view.*
import kotlinx.android.synthetic.main.listitem_dyno_switch.view.*
import kotlinx.android.synthetic.main.listitem_dyno_switch.view.switch_dyno
import kotlinx.android.synthetic.main.listitem_dyno_switch.view.text_dyno_type

@AndroidEntryPoint
class AppInfoActivity : AppCompatActivity() {
    private val TAG: String = this.javaClass.simpleName
    private lateinit var app: HerokuApp
    private var clipboardManager: ClipboardManager? = null

    private val viewModel: AppInfoViewModel by viewModels<AppInfoViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_info)
        app = intent.getSerializableExtra("app") as HerokuApp
        text_appid.text = app.id
        text_appname.text = app.name
        text_owner.text = app.owner?.email ?: ""
        text_buildstack.text = app.buildStack?.name ?: ""
        text_createdat.text = app.createdAt
        text_maintainancemode.text = app.maintenance.toString()
        text_app_url.text = app.webUrl
        setupObserver()
        supportActionBar?.let {
            it.title = app.name
        }
    }

    override fun onStart() {
        super.onStart()
        clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    }

    private fun setupObserver() {
        viewModel.dynoFormations.observe(this, Observer {
            if (it.status == Status.SUCCESS) {
                it.data?.let {
                    if (it.isNotEmpty()) {
                        cardview_dynos_top.visibility = View.VISIBLE
                        layout_dynolist_top.removeAllViews()
                        val layoutInflater = this@AppInfoActivity.layoutInflater
                        it.forEach { dynoFormation ->
                            val dynoLayout =
                                layoutInflater.inflate(R.layout.listitem_dyno_switch, null)
                            dynoLayout.text_dyno_type.text = dynoFormation.type
                            dynoLayout.switch_dyno.isChecked = dynoFormation.quantity!! > 0
                            dynoLayout.switch_dyno.setOnClickListener { switch ->
                                cardview_dynos_state.visibility = View.GONE
                                switch.isActivated = false
                                val isChecked = (switch as CompoundButton).isChecked
                                val updatePayload: DynoFormationUpdatePayload =
                                    DynoFormationUpdatePayload(
                                        listOf(
                                            UpdatesItem(
                                                (if (isChecked) 1 else 0),
                                                dynoFormation.type!!
                                            )
                                        )
                                    )
                                viewModel.updateDynoFormation(
                                    app.name!!, updatePayload
                                ).observe(this@AppInfoActivity, Observer {
                                    if (it.status == Status.ERROR) {
                                        (switch as CompoundButton).isChecked = !isChecked
                                    }

                                    if (it.status == Status.SUCCESS) {
                                        viewModel.getDynoFormation(app.name!!, true)
                                        viewModel.getDynos(app.name!!, true)
                                    }
                                    if (it.status == Status.ERROR || it.status == Status.SUCCESS) {
                                        switch.isActivated = true
                                    }
                                })
                            }
                            dynoLayout.btn_dyno_info.setOnClickListener {
                                val bundle: Bundle = Bundle()
                                bundle.putSerializable("dyno", dynoFormation)
                                val fragment = AppInfoBottomSheetDialogFragment()
                                fragment.arguments = bundle
                                fragment.show(
                                    this@AppInfoActivity.supportFragmentManager,
                                    AppInfoBottomSheetDialogFragment.TAG
                                )
                            }
                            layout_dynolist_top.addView(dynoLayout)
                        }
                    }

                }
            }
        })
        viewModel.dynos.observe(this, Observer {
            if (it.status == Status.SUCCESS) {
                it.data?.let {
                    if (it.isNotEmpty()) {
                        cardview_dynos_state.visibility = View.VISIBLE
                        layout_dynolist_state.removeAllViews()
                        it.forEach { dyno ->
                            val dynoLayout =
                                layoutInflater.inflate(R.layout.listitem_dyno_state, null)
                            dynoLayout.text_dyno_type.text = dyno.type
                            dynoLayout.text_dyno_state.text = dyno.state ?: "-"
                            layout_dynolist_state.addView(dynoLayout)
                        }
                    }
                }
            }
        })
        viewModel.log.observe(this, Observer {
            Log.i(TAG, "setupObserver: ")
            if (it.status == Status.SUCCESS) {
                Log.i(TAG, it.toString())
            }
            Log.i(TAG, it.toString())
        })
        viewModel.getDynoFormation(app.name ?: "")
        viewModel.getDynos(app.name ?: "")
    }

    fun copyAppUrl(view: View) {
        val clipData = ClipData.newPlainText("App Web URL", app.webUrl)
        clipboardManager?.let {
            it.setPrimaryClip(clipData)
            Snackbar.make(
                appinfo_nest_scroll_view,
                "Web URL copied to clipboard",
                Snackbar.LENGTH_SHORT
            ).apply {
                animationMode = Snackbar.ANIMATION_MODE_FADE
            }.show()
        }
    }
}