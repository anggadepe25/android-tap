package com.angga.telkomselapprenticeprogam.activities.edit_profil

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.angga.telkomselapprenticeprogam.models.User
import com.angga.telkomselapprenticeprogam.repositories.UserRepository
import com.angga.telkomselapprenticeprogam.utilities.SingleLiveEvent

class ActivityEditProfileViewModel(private val userRepository: UserRepository) : ViewModel(){
    private val state : SingleLiveEvent<EditProfileState> = SingleLiveEvent()
    private val user = MutableLiveData<User>()

    private fun setLoading() {
        state.value = EditProfileState.Loading(true)
    }
    private fun hideLoading() {
        state.value = EditProfileState.Loading(false)
    }
    private fun toast(mesage: String){
        state.value = EditProfileState.ShowToast(mesage)
    }

    fun profile(token: String){
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

sealed class EditProfileState{
    data class Loading(val state : Boolean) : EditProfileState()
    data class ShowToast(val state : String) : EditProfileState()
}