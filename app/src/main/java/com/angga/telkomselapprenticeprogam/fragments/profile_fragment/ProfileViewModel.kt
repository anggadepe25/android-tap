package com.angga.telkomselapprenticeprogam.fragments.profile_fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.angga.telkomselapprenticeprogam.models.User
import com.angga.telkomselapprenticeprogam.repositories.UserRepository
import com.angga.telkomselapprenticeprogam.utilities.SingleLiveEvent

class ProfileViewModel(private val userRepository: UserRepository) : ViewModel(){
    private val state : SingleLiveEvent<ProfileState> = SingleLiveEvent()
    private val user = MutableLiveData<User>()

    private fun setLoading(){ state.value = ProfileState.Loading(true) }
    private fun hideLoading(){ state.value = ProfileState.Loading(false) }
    private fun toast(message: String){ state.value = ProfileState.ShowToast(message) }

    fun profile(token : String){
        setLoading()
        userRepository.profile(token){resultUser, error ->
            hideLoading()
            error?.let { it.message?.let { message-> toast(message) } }
            resultUser?.let { user.postValue(it) }
        }
    }

    fun listenToState() = state
    fun listenToUser() = user
}

sealed class ProfileState{
    data class Loading(var state : Boolean = false) : ProfileState()
    data class ShowToast(var message : String) : ProfileState()
}