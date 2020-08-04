package com.angga.telkomselapprenticeprogam.activities.edit_password

import androidx.lifecycle.ViewModel
import com.angga.telkomselapprenticeprogam.models.User
import com.angga.telkomselapprenticeprogam.repositories.UserRepository
import com.angga.telkomselapprenticeprogam.utilities.SingleLiveEvent
import com.angga.telkomselapprenticeprogam.utilities.SingleResponse

class EditPasswordViewModel (private val userRepository: UserRepository) : ViewModel(){
    private val state : SingleLiveEvent<EditPasswordState> = SingleLiveEvent()

    private fun setLoading(){ state.value = EditPasswordState.Loading(true) }
    private fun hideLoading(){ state.value = EditPasswordState.Loading(false) }
    private fun toast(message: String){ state.value = EditPasswordState.ShowToast(message) }
    private fun success() { state.value = EditPasswordState.Success }

    fun validate(password: String, confirmPassword: String) : Boolean{
        if (password.isEmpty()){
            state.value = EditPasswordState.Validate(password = "password tidak boleh kosong")
            return false
        }

        if (confirmPassword.isEmpty()){
            state.value = EditPasswordState.Validate(confirmPassword = "confirm password tidak boleh kosong")
            return false
        }

        if (!confirmPassword.equals(password)){
            state.value = EditPasswordState.Validate(confirmPassword = "confirm password tidak sesuai")
            return false
        }
        return true
    }

    fun updatePassword(token : String, password : String){
        setLoading()
        userRepository.updatePassword(token, password, object : SingleResponse<User>{
            override fun onSuccess(data: User?) {
                hideLoading()
                data?.let { success() }
            }

            override fun onFailure(err: Error) {
                hideLoading()
                toast(err.message.toString())
            }
        })
    }

    fun listenToState() = state
}

sealed class EditPasswordState{
    data class Loading(var state : Boolean = false) : EditPasswordState()
    data class ShowToast(var message : String): EditPasswordState()
    object Success : EditPasswordState()
    object Reset : EditPasswordState()
    data class Validate(var password: String? = null, var confirmPassword : String? = null) : EditPasswordState()
}