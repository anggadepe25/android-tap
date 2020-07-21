package com.angga.telkomselapprenticeprogam.fragments.profile_fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.angga.telkomselapprenticeprogam.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment(R.layout.fragment_akun){
    //private val profileViewModel : ProfileViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //observer()
        //observe()
    }

//    private fun observer() {
//        profileViewModel.listenToState().observer(viewLifecycleOwner, Observer { handleUiState(it) })
//    }
//
//    private fun handleUiState(it: ProfileState) {
//        when(it){
//            is ProfileState.Loading -> handleLoading(it.state)
//        }
//    }
//
//    private fun handleLoading(state: Boolean) {
//
//    }
//
//    private fun observe() {
//
//    }
}