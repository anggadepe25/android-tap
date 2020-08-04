package com.angga.telkomselapprenticeprogam.fragments.profile_fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import coil.api.load
import com.angga.telkomselapprenticeprogam.R
import com.angga.telkomselapprenticeprogam.activities.edit_password.activity_edit_password
import com.angga.telkomselapprenticeprogam.activities.edit_profil.activity_edit_profil
import com.angga.telkomselapprenticeprogam.activities.login.LoginActivity
import com.angga.telkomselapprenticeprogam.models.User
import com.angga.telkomselapprenticeprogam.utilities.Constants
import kotlinx.android.synthetic.main.fragment_akun.*
import kotlinx.android.synthetic.main.fragment_akun.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment(R.layout.fragment_akun){
    private val profileViewModel : ProfileViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()
        observeUser()
        goToAccountActivity()
        goToPasswordActivity()
        logout()
    }

    private fun observer() {
        profileViewModel.listenToState().observer(viewLifecycleOwner, Observer { handleUiState(it) })
    }

    override fun onResume() {
        super.onResume()
        fetchUser()

    }

    private fun goToAccountActivity(){
        requireView().ll_akun.setOnClickListener {
            startActivity(Intent(requireActivity(), activity_edit_profil::class.java))
        }
    }

    private fun goToPasswordActivity(){
        requireView().ll_password.setOnClickListener {
            startActivity(Intent(requireActivity(), activity_edit_password::class.java))
        }
    }


    private fun fetchUser() = profileViewModel.profile(Constants.getToken(requireActivity()))

    private fun handleUiState(it: ProfileState) {
        when(it){
            is ProfileState.Loading -> handleLoading(it.state)
        }
    }

    private fun handleLoading(state: Boolean) {}

    private fun observeUser() = profileViewModel.listenToUser().observe(viewLifecycleOwner, Observer { handleUser(it) })

    private fun handleUser(user: User){
        requireView().profile_name.text = user.nama
        user.foto?.let { urlPhoto ->
            requireView().profile_image.load(urlPhoto)
        }
    }

    private fun logout(){
        ll_logout.setOnClickListener {
            popup("apakah anda yakin?")
        }
    }

    private fun popup(message: String) {
        AlertDialog.Builder(requireActivity()).apply {
            setMessage(message)
            setPositiveButton("ya"){dialog, _ ->
                dialog.dismiss()
                Constants.clearToken(requireActivity())
                startActivity(Intent(requireActivity(), LoginActivity::class.java))
                requireActivity().finish()
            }
        }.show()
    }
}