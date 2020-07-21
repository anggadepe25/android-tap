package com.angga.telkomselapprenticeprogam.activities.detail_reward

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.api.load
import com.angga.telkomselapprenticeprogam.R
import com.angga.telkomselapprenticeprogam.models.Program
import com.angga.telkomselapprenticeprogam.models.Reward
import kotlinx.android.synthetic.main.content_detail_reward.*
import kotlinx.android.synthetic.main.fragment_home.*

class DetailRewardActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_reward)

        getPassedReward()?.let {
            supportActionBar?.title = it.judul
            txt_keterangan.text = it.keterangan
        }

    }

    private fun getPassedReward() : Reward? = intent.getParcelableExtra("REWARD")
}