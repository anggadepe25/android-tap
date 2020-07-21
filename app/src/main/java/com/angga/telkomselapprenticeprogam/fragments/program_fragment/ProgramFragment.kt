package com.angga.telkomselapprenticeprogam.fragments.program_fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.angga.telkomselapprenticeprogam.R
import com.angga.telkomselapprenticeprogam.adapters.ProgramAdapter
import com.angga.telkomselapprenticeprogam.extensions.gone
import com.angga.telkomselapprenticeprogam.extensions.visible
import com.angga.telkomselapprenticeprogam.utilities.Constants
import kotlinx.android.synthetic.main.fragment_program.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProgramFragment : Fragment(R.layout.fragment_program){
    private val programViewModel : ProgramViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_program.apply {
            adapter = ProgramAdapter(mutableListOf(), requireActivity())
            layoutManager = LinearLayoutManager(requireActivity())
        }

        programViewModel.listenToState().observer(viewLifecycleOwner, Observer { handleUI(it) })
        programViewModel.listenToPrograms().observe(viewLifecycleOwner, Observer {
            rv_program.adapter?.let { adapter ->
                if (adapter is ProgramAdapter){
                    adapter.changelist(it)
                }
            }
        })
    }

    private fun handleUI(it : ProgramState){
        when(it){
            is ProgramState.IsLoading -> {
                if (it.state){
                    pb_program.visible()
                }else{
                    pb_program.gone()
                }
            }
            is ProgramState.ShowYoast -> toast(it.message)
        }
    }

    private fun toast(message : String) = Toast.makeText(requireActivity(), message, Toast.LENGTH_LONG).show()

    override fun onResume() {
        super.onResume()
        programViewModel.getPrograms(Constants.getToken(requireActivity()))
    }
}