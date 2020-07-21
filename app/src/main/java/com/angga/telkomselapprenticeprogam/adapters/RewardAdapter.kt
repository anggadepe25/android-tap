package com.angga.telkomselapprenticeprogam.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.angga.telkomselapprenticeprogam.R
import com.angga.telkomselapprenticeprogam.activities.detail_reward.DetailRewardActivity
import com.angga.telkomselapprenticeprogam.models.Reward
import kotlinx.android.synthetic.main.item_reward.view.*

class RewardAdapter (private var rewards : MutableList<Reward>, private var context: Context)
    : RecyclerView.Adapter<RewardAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_reward, parent, false))
    }

    override fun getItemCount(): Int = rewards.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(rewards[position], context)

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        fun bind(reward: Reward, context: Context){
            with(itemView){
                txt_name_reward.text = reward.judul
                setOnClickListener {
                    context.startActivity(Intent(context, DetailRewardActivity::class.java).apply {
                        putExtra("REWARD", reward)
                    })
                }
            }
        }
    }

    fun changelist(c : List<Reward>){
        rewards.clear()
        rewards.addAll(c)
        notifyDataSetChanged()
    }

}