package br.com.jdscaram.covid19radar.ui.main

import androidx.lifecycle.ViewModel
import br.com.jdscaram.covid.repository.CovidRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MainViewModel(private val repository: CovidRepository) : ViewModel() {

}