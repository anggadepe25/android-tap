package com.angga.telkomselapprenticeprogam.fragments.home_fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import coil.api.load
import com.angga.telkomselapprenticeprogam.R
import com.angga.telkomselapprenticeprogam.adapters.InfoAdapter
import com.angga.telkomselapprenticeprogam.adapters.RewardAdapter
import com.angga.telkomselapprenticeprogam.extensions.gone
import com.angga.telkomselapprenticeprogam.extensions.visible
import com.angga.telkomselapprenticeprogam.models.Info
import com.angga.telkomselapprenticeprogam.models.Reward
import com.angga.telkomselapprenticeprogam.models.User
import com.angga.telkomselapprenticeprogam.utilities.Constants
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(R.layout.fragment_home){
    private val homeViewModel : HomeViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpInfo()
        setUpReward()
        observer()
        observe()
    }

    private fun observer() {
        homeViewModel.listenToState().observer(viewLifecycleOwner, Observer { handleUI(it) })
    }

    private fun observe() {
        homeViewModel.listenToInfos().observe(viewLifecycleOwner, Observer { handleInfo(it) })
        homeViewModel.listenToRewards().observe(viewLifecycleOwner, Observer { handleReward(it) })
        homeViewModel.listenToUser().observe(viewLifecycleOwner, Observer { handleUser(it) })
    }

    private fun handleUser(it: User) {
        foto.load(it.foto)
        username.text = it.nama
        branch.text = it.branch
        point.text = it.point.toString()
    }

    private fun setUpInfo(){
        rv_info.apply {
            adapter = InfoAdapter(mutableListOf(), activity!!)
            layoutManager = LinearLayoutManager(activity!!, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun setUpReward(){
        rv_reward.apply {
            adapter = RewardAdapter(mutableListOf(), requireActivity())
            layoutManager = LinearLayoutManager(activity!!, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun handleUI(it : HomeState){
        when(it){
            is HomeState.IsLoading -> handleLoading(it.state)
            is HomeState.ShowToast -> toast(it.message)
        }
    }

    private fun handleLoading(state: Boolean) {
        if(state){
            pb_home.visible()
        }else{
            pb_home.gone()
        }
    }

    private fun handleInfo(it : List<Info>){
        rv_info.adapter?.let { adapter ->
            if (adapter is InfoAdapter){
                adapter.changelist(it)
            }
        }
    }

    private fun handleReward(it : List<Reward>){
        rv_reward.adapter?.let { adapter ->
            if (adapter is RewardAdapter){
                adapter.changelist(it)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        homeViewModel.getInfos(Constants.getToken(requireActivity()))
        homeViewModel.getRewards(Constants.getToken(requireActivity()))
        homeViewModel.profile(Constants.getToken(requireActivity()))
    }

    private fun toast(message : String){ Toast.makeText(activity!!, message, Toast.LENGTH_LONG).show() }

}