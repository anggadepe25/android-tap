package com.angga.telkomselapprenticeprogam.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.angga.telkomselapprenticeprogam.R
import com.angga.telkomselapprenticeprogam.activities.detail_program.DetailProgramActivity
import com.angga.telkomselapprenticeprogam.models.Program
import kotlinx.android.synthetic.main.item_program.view.*

class ProgramAdapter (private var programs : MutableList<Program>, private var context: Context)
    : RecyclerView.Adapter<ProgramAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_program, parent, false))
    }

    override fun getItemCount(): Int = programs.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(programs[position], context)


    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        fun bind(program: Program, context: Context){
            with(itemView){
                txt_name_program.text = program.judul
                img_program.load(program.gambar)
                setOnClickListener {
                    context.startActivity(Intent(context, DetailProgramActivity::class.java).apply {
                        putExtra("PROGRAM", program)
                    })
                }
            }
        }
    }

    fun changelist(c : List<Program>){
        programs.clear()
        programs.addAll(c)
        notifyDataSetChanged()
    }
}