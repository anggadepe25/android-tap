package com.angga.telkomselapprenticeprogam.activities.edit_profil

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.angga.telkomselapprenticeprogam.R
import com.angga.telkomselapprenticeprogam.extensions.gone
import com.angga.telkomselapprenticeprogam.extensions.visible
import com.angga.telkomselapprenticeprogam.models.User
import com.angga.telkomselapprenticeprogam.utilities.Constants
import kotlinx.android.synthetic.main.activity_edit_profil.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class activity_edit_profil : AppCompatActivity() {

    private val editProfileViewModel: ActivityEditProfileViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profil)
        observe()
    }

    override fun onResume() {
        super.onResume()
        fetchProfile()
    }



    private fun observe(){
        observeState()
        observeUser()
    }

    private fun isLoading(b: Boolean) = if(b) editProfile_progressBar.visible() else editProfile_progressBar.gone()
    private fun fetchProfile() = editProfileViewModel.profile(Constants.getToken(this@activity_edit_profil))
    private fun observeState() = editProfileViewModel.listenToState().observer(this, Observer { handleState(it) })
    private fun observeUser() = editProfileViewModel.listenToUser().observe(this, Observer { handleUser(it) })

    private fun handleState(state: EditProfileState){
        when(state){
            is EditProfileState.Loading -> isLoading(state.state)
            is EditProfileState.ShowToast -> Toast.makeText(this, state.state, Toast.LENGTH_LONG).show()
        }
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
}
