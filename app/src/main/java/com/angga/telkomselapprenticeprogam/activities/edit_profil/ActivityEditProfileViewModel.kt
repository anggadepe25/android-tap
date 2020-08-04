package com.angga.telkomselapprenticeprogam.activities.edit_profil

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.angga.telkomselapprenticeprogam.models.User
import com.angga.telkomselapprenticeprogam.repositories.UserRepository
import com.angga.telkomselapprenticeprogam.utilities.SingleLiveEvent
import com.angga.telkomselapprenticeprogam.utilities.SingleResponse

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

    fun updateProfile(token: String, user: User, pathImage: String){
        setLoading()
        if (user.nama == null){
            updatePhotoProfile(token, pathImage)
        }else{
            userRepository.updateProfile(token, user, object : SingleResponse<User>{
                override fun onSuccess(data: User?) {
                    hideLoading()
                    data?.let {
                        if (pathImage.isNotEmpty()) {
                            updatePhotoProfile(token, pathImage)
                        } else {
                            state.value = EditProfileState.Success
                        }
                    }
                }

                override fun onFailure(err: Error) {
                    toast(err.message.toString())
                }

            })
        }
    }

    private fun updatePhotoProfile(token: String, pathImage: String) {
        userRepository.updatePhoto(token, pathImage, object : SingleResponse<User>{
            override fun onFailure(err: Error) {
                hideLoading()
                toast(err.message.toString())
            }

            override fun onSuccess(data: User?) {
                hideLoading()
                data?.let {
                    state.value = EditProfileState.Success
                }
            }

        })
    }

    fun listenToState() = state
    fun listenToUser() = user

}

sealed class EditProfileState{
    data class Loading(val state : Boolean) : EditProfileState()
    data class ShowToast(val state : String) : EditProfileState()
    object Success : EditProfileState()
    data class Validate(
        var name : String? = null,
        var pass: String? = null
    ) : EditProfileState()
    object Reset : EditProfileState()
}