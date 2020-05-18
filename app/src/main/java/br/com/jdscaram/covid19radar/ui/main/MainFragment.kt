package br.com.jdscaram.covid19radar.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.jdscaram.covid19radar.databinding.MainFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {

    private val mainViewModel by viewModel<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = MainFragmentBinding.inflate(inflater, container, false).apply {
        loadInfo()
    }.root

    private fun loadInfo() {
        mainViewModel.loadCountries()
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}