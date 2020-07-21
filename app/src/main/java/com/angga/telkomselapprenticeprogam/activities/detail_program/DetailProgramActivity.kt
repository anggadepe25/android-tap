package com.angga.telkomselapprenticeprogam.activities.detail_program

import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import coil.api.load
import com.angga.telkomselapprenticeprogam.R
import com.angga.telkomselapprenticeprogam.extensions.gone
import com.angga.telkomselapprenticeprogam.extensions.visible
import com.angga.telkomselapprenticeprogam.models.Program
import com.angga.telkomselapprenticeprogam.utilities.Constants
import kotlinx.android.synthetic.main.activity_detail_program.*
import kotlinx.android.synthetic.main.content_detail_program.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailProgramActivity : AppCompatActivity() {

    private val detailProgramViewModel : DetailProgramViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_program)
        setSupportActionBar(toolbar)

        detailProgramViewModel.listenToState().observer(this, Observer { handleUI(it) })
        send()
        getPassedProgram()?.let {
            img_program.load(it.gambar)
            supportActionBar?.title = it.judul
            txt_panduan.text = it.panduan
        }
    }

    private fun handleUI(it : DetailProgramState){
        when(it){
            is DetailProgramState.ShowToast -> toast(it.message)
            is DetailProgramState.IsLoading -> {
                if (it.state){
                    pb_detail_program.visible()
                }else{
                    pb_detail_program.gone()
                }
            }
            is DetailProgramState.Success -> {
                toast("berhasil mengirim")
                finish()
            }
        }
    }

    private fun send(){
        btn_send.setOnClickListener {
            val link = et_link.text.toString().trim()
            detailProgramViewModel.challenge(Constants.getToken(this@DetailProgramActivity),getPassedProgram()?.id.toString(), link)
        }
    }

    private fun getPassedProgram() : Program? =intent.getParcelableExtra("PROGRAM")
    private fun toast(message : String) = Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}