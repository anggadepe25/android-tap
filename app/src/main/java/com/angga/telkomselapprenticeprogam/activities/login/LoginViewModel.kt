package com.angga.telkomselapprenticeprogam.activities.login

import androidx.lifecycle.ViewModel
import com.angga.telkomselapprenticeprogam.repositories.UserRepository
import com.angga.telkomselapprenticeprogam.utilities.Constants
import com.angga.telkomselapprenticeprogam.utilities.SingleLiveEvent

class LoginViewModel (private val userRepository: UserRepository) : ViewModel(){
    private val state : SingleLiveEvent<LoginState> = SingleLiveEvent()

    private fun toast(message: String) { state.value =
        LoginState.ShowToast(message)
    }
    private fun setLoading() { state.value =
        LoginState.IsLoading(true)
    }
    private fun hideLoading() { state.value =
        LoginState.IsLoading(false)
    }
    private fun success(token: String) { state.value  =
        LoginState.Success(token)
    }
    private fun reset() { state.value =
        LoginState.Reset
    }

    fun login(email : String, password : String){
        setLoading()
        userRepository.login(email, password){token, error ->
            hideLoading()
            error?.let { it.message.let { message-> toast(message!!) } }
            token?.let { success(it) }
        }
    }

    fun validate(email: String, password: String) : Boolean{
        reset()
        if (email.isEmpty() || password.isEmpty()){
            state.value =
                LoginState.ShowToast("mohon isi semua form")
            return false
        }
        if (!Constants.isValidEmail(email)){
            state.value =
                LoginState.Validate(email = "email tidak valid")
            return false
        }
        if (!Constants.isValidPassword(password)){
            state.value =
                LoginState.Validate(password = "password tidak valid")
            return false
        }

        return true
    }

    fun listenToState() = state

}
sealed class LoginState{
    data class IsLoading(var state: Boolean = false) : LoginState()
    data class ShowToast(var message : String) : LoginState()
    data class Success(var token : String) : LoginState()
    data class Validate(
        var email: String? = null,
        var password: String? = null
    ) : LoginState()
    object Reset : LoginState()

}