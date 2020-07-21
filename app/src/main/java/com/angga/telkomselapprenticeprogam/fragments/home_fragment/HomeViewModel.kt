package com.angga.telkomselapprenticeprogam.fragments.home_fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.angga.telkomselapprenticeprogam.models.Info
import com.angga.telkomselapprenticeprogam.models.Reward
import com.angga.telkomselapprenticeprogam.models.User
import com.angga.telkomselapprenticeprogam.repositories.InfoRepository
import com.angga.telkomselapprenticeprogam.repositories.RewardRepository
import com.angga.telkomselapprenticeprogam.repositories.UserRepository
import com.angga.telkomselapprenticeprogam.utilities.SingleLiveEvent

class HomeViewModel (private val infoRepository: InfoRepository,
                     private val rewardRepository: RewardRepository,
                     private val userRepository: UserRepository) : ViewModel(){
    private val state : SingleLiveEvent<HomeState> = SingleLiveEvent()
    private val infos = MutableLiveData<List<Info>>()
    private val rewards = MutableLiveData<List<Reward>>()
    private val user = MutableLiveData<User>()

    private fun setLoading() { state.value = HomeState.IsLoading(true) }
    private fun hideLoading() { state.value = HomeState.IsLoading(false) }
    private fun toast(message: String) { state.value = HomeState.ShowToast(message) }

    fun getInfos(token : String){
        setLoading()
        infoRepository.getInfos(token){resultList, error->
            hideLoading()
            error?.let { it.message?.let { message-> toast(message) }}
            resultList?.let { infos.postValue(it) }
        }
    }

    fun getRewards(token: String){
        setLoading()
        rewardRepository.getRewards(token){listReward, error ->
            hideLoading()
            error?.let { it.message?.let { message-> toast(message) }}
            listReward?.let { rewards.postValue(it) }
        }

    }

    fun profile(token : String){
        setLoading()
        userRepository.profile(token){resultUser, error ->
            hideLoading()
            error?.let { it.message?.let { message-> toast(message) } }
            resultUser?.let { user.postValue(it) }
        }
    }

    fun listenToState() = state
    fun listenToInfos() = infos
    fun listenToRewards() = rewards
    fun listenToUser() = user

}

sealed class HomeState{
    data class IsLoading(var state : Boolean = false) : HomeState()
    data class ShowToast(var message : String) : HomeState()
}