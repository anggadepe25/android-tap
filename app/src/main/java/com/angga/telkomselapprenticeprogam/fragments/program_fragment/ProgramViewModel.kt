package com.angga.telkomselapprenticeprogam.fragments.program_fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.angga.telkomselapprenticeprogam.models.Program
import com.angga.telkomselapprenticeprogam.repositories.ProgramRepository
import com.angga.telkomselapprenticeprogam.utilities.SingleLiveEvent

class ProgramViewModel (private val programRepository: ProgramRepository) : ViewModel(){
    private val state : SingleLiveEvent<ProgramState> = SingleLiveEvent()
    private val programs = MutableLiveData<List<Program>>()

    private fun toast(message: String){ state.value = ProgramState.ShowYoast(message) }
    private fun setLoading() { state.value = ProgramState.IsLoading(true) }
    private fun hideLoading() { state.value = ProgramState.IsLoading(false) }

    fun getPrograms(token : String){
        setLoading()
        programRepository.getPrograms(token){listProgram, error ->
            hideLoading()
            error?.let { it.message?.let { message -> toast(message) } }
            listProgram?.let { programs.postValue(it) }
        }
    }

    fun listenToState() = state
    fun listenToPrograms() = programs
}

sealed class ProgramState{
    data class IsLoading(val state : Boolean = false) : ProgramState()
    data class ShowYoast(val message : String) : ProgramState()
}