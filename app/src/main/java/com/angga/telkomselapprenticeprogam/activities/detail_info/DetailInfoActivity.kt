package com.angga.telkomselapprenticeprogam.activities.detail_info

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.api.load
import com.angga.telkomselapprenticeprogam.R
import com.angga.telkomselapprenticeprogam.models.Info
import kotlinx.android.synthetic.main.activity_detail_info.*
import kotlinx.android.synthetic.main.content_detail_info.*

class DetailInfoActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_info)
        setSupportActionBar(toolbar)

        getPassedInfo()?.let {
            println(it.judul)
            supportActionBar?.title = it.judul
            img_info.load(it.gambar)
            txt_deskripsi.text = it.deskripsi
        }


    }

    private fun getPassedInfo() : Info? = intent.getParcelableExtra("INFO")

}