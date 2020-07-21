package com.angga.telkomselapprenticeprogam.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.angga.telkomselapprenticeprogam.R
import com.angga.telkomselapprenticeprogam.activities.detail_info.DetailInfoActivity
import com.angga.telkomselapprenticeprogam.activities.detail_reward.DetailRewardActivity
import com.angga.telkomselapprenticeprogam.models.Info
import kotlinx.android.synthetic.main.item_info.view.*

class InfoAdapter (private var infos : MutableList<Info>, private var context: Context)
    : RecyclerView.Adapter<InfoAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_info, parent, false))
    }

    override fun getItemCount(): Int = infos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(infos[position], context)

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        fun bind(info: Info, context: Context){
            with(itemView){
                iv_info.load(info.gambar)
                setOnClickListener {
                    context.startActivity(Intent(context, DetailInfoActivity::class.java).apply {
                        putExtra("INFO", info)
                    })
                }
            }
        }
    }

    fun changelist(c : List<Info>){
        infos.clear()
        infos.addAll(c)
        notifyDataSetChanged()
    }
}