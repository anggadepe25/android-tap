package com.angga.telkomselapprenticeprogam.activities.detail_program

import androidx.lifecycle.ViewModel
import com.angga.telkomselapprenticeprogam.repositories.ChallengeRepository
import com.angga.telkomselapprenticeprogam.utilities.SingleLiveEvent

class DetailProgramViewModel (private val challengeRepository: ChallengeRepository) : ViewModel(){
    private val state : SingleLiveEvent<DetailProgramState> = SingleLiveEvent()

    private fun setLoading(){ state.value = DetailProgramState.IsLoading(true) }
    private fun hideLoading(){ state.value = DetailProgramState.IsLoading(false) }
    private fun toast(message: String){ state.value = DetailProgramState.ShowToast(message) }

    fun challenge(token : String, id_program : String, link : String){
        setLoading()
        challengeRepository.challenge(token, id_program.toInt(), link){resultBool, error ->
            hideLoading()
            error?.let { it.message?.let { message-> toast(message) } }
            if (resultBool){
                state.value = DetailProgramState.Success
            }
        }
    }

    fun listenToState() = state
}

sealed class DetailProgramState{
    data class IsLoading(var state : Boolean = false) : DetailProgramState()
    data class ShowToast(var message : String) : DetailProgramState()
    object Success : DetailProgramState()
}