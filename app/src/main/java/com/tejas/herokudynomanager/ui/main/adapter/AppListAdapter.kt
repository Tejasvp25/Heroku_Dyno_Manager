package com.tejas.herokudynomanager.ui.main.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.tejas.herokudynomanager.R
import com.tejas.herokudynomanager.network.models.HerokuApp
import com.tejas.herokudynomanager.ui.main.view.AppInfoActivity

class AppListAdapter constructor(private val appList:List<HerokuApp>) : RecyclerView.Adapter<AppListAdapter.ViewHolder>() {


    lateinit var context:Context
    inner class ViewHolder(listItemView: View):RecyclerView.ViewHolder(listItemView) {
        val cardView = listItemView.findViewById<MaterialCardView>(R.id.cardview_apps)
        val textName = listItemView.findViewById<TextView>(R.id.text_app_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{
        context = parent.context
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.listitem_app,parent,false))
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val herokuApp:HerokuApp = appList.get(position)
        holder.textName.text = herokuApp.name
        holder.cardView.setOnClickListener {
            val intent = Intent(context,AppInfoActivity::class.java)
            intent.putExtra("app",herokuApp)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int = appList.size
}