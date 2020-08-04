package com.angga.telkomselapprenticeprogam.activities.edit_profil

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputFilter
import android.text.Spanned
import android.widget.Toast
import androidx.lifecycle.Observer
import coil.api.load
import com.angga.telkomselapprenticeprogam.R
import com.angga.telkomselapprenticeprogam.extensions.gone
import com.angga.telkomselapprenticeprogam.extensions.visible
import com.angga.telkomselapprenticeprogam.models.User
import com.angga.telkomselapprenticeprogam.utilities.Constants
import com.fxn.pix.Pix
import kotlinx.android.synthetic.main.activity_edit_profil.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class activity_edit_profil : AppCompatActivity() {

    private val editProfileViewModel: ActivityEditProfileViewModel by viewModel()
    private var imgUrl = ""
    private val REQ_CODE_PIX = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profil)
        setupNameFilter()
        gb_profil.setOnClickListener { Pix.start(this, REQ_CODE_PIX) }
        observe()
        updateProfil()
        setDateListener()
    }

    private fun validateEmail(email: String) : Boolean {
        return Constants.isValidEmail(email)
    }

    private fun observe(){
        observeState()
        observeUser()
    }

    private fun setupNameFilter(){
        et_nama.filters = arrayOf(object : InputFilter {
            override fun filter(source: CharSequence?, start: Int, end: Int, dest: Spanned?, dstart: Int, dend: Int): CharSequence {
                source?.let { s ->
                    if(s == ""){
                        return s
                    }
                    if(s.toString().matches("[a-zA-Z\\s]+".toRegex())){
                        return s
                    }
                    return ""
                }
                return ""
            }
        })
    }

    private fun isLoading(b: Boolean) = if(b) editProfile_progressBar.visible() else editProfile_progressBar.gone()
    private fun fetchProfile() = editProfileViewModel.profile(Constants.getToken(this@activity_edit_profil))
    private fun observeState() = editProfileViewModel.listenToState().observer(this, Observer { handleState(it) })
    private fun observeUser() = editProfileViewModel.listenToUser().observe(this, Observer { handleUser(it) })

    private fun handleState(state: EditProfileState){
        when(state){
            is EditProfileState.Loading -> isLoading(state.state)
            is EditProfileState.ShowToast -> Toast.makeText(this, state.state, Toast.LENGTH_LONG).show()
            is EditProfileState.Success -> handleSuccess()
        }
    }

    private fun handleSuccess() {
        Toast.makeText(this, "Berhasil Update Profile", Toast.LENGTH_LONG).show()
        finish()
    }

    private fun setDateListener() {
        val cal = Calendar.getInstance()
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, month)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val myFormat = "yyyy-MM-dd"
            val simpleDateFormat = SimpleDateFormat(myFormat, Locale.US)
            setDateText(simpleDateFormat.format(cal.time))
        }

        et_tgl_lahir.setOnClickListener {
            DatePickerDialog(this@activity_edit_profil, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).apply {
//                datePicker.minDate = cal.timeInMillis
                datePicker.maxDate= cal.timeInMillis
            }.show()
        }
    }

    private fun setDateText(readyDate: String){
        et_tgl_lahir.setText(readyDate)
    }

    private fun updateProfil() {
        btn_simpan_profil.setOnClickListener {
            resetError()

            val token = Constants.getToken(this@activity_edit_profil)
            val name = et_nama.text.toString().trim()
            val email = et_email.text.toString().trim()
            val nohp = et_nohp.text.toString().trim()
            val branch = et_branch.text.toString().trim()
            val jenis_kelamin = et_jenis_kelamin.text.toString().trim()
            val tgl_lahir = et_tgl_lahir.text.toString().trim()
            val tempat_tinggal = et_tempat_tinggal.text.toString().trim()
            val kampus = et_kampus.text.toString().trim()
            val jurusan = et_jurusan.text.toString().trim()
            val semester = et_semester.text.toString().trim()
            val hobi = et_hobi.text.toString().trim()
            val instagram = et_instagram.text.toString().trim()

            if(!validateEmail(email)){
                til_email.error = "Email tidak valid"
                return@setOnClickListener
            }


            val user = User(nama = name, email = email, nohp = nohp, branch = branch, jenis_kelamin = jenis_kelamin,
                tgl_lahir = tgl_lahir, tempat_tinggal = tempat_tinggal, kampus = kampus, jurusan = jurusan,
                semester = semester, hobi = hobi, instagram = instagram)
            editProfileViewModel.updateProfile(token, user, imgUrl)
        }
    }

    private fun resetError(){
        til_email.error = null
    }


    private fun handleUser(user: User){
        et_nama.setText(user.nama)
        et_email.setText(user.email)
        et_nohp.setText(user.nohp)
        et_branch.setText(user.branch)
        et_jenis_kelamin.setText(user.jenis_kelamin)
        et_tgl_lahir.setText(user.tgl_lahir)
        et_tempat_tinggal.setText(user.tempat_tinggal)
        et_kampus.setText(user.kampus)
        et_jurusan.setText(user.jurusan)
        et_semester.setText(user.semester)
        et_hobi.setText(user.hobi)
        et_instagram.setText(user.instagram)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQ_CODE_PIX && resultCode == Activity.RESULT_OK && data != null){
            val selectedImageUri = data.getStringArrayListExtra(Pix.IMAGE_RESULTS)
            selectedImageUri?.let {
                gb_profil.load(File(it[0]))
                imgUrl = it[0]
            }
        }
    }

    override fun onResume() {
        super.onResume()
        fetchProfile()
    }
}
