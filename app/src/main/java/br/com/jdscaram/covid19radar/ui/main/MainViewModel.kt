package br.com.jdscaram.covid19radar.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.jdscaram.core.model.CountryUiModel
import br.com.jdscaram.covid.repository.CovidRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: CovidRepository) : ViewModel() {

    val viewAction = MutableLiveData<ViewAction>()

    sealed class ViewAction {
        class Loading(val isLoading: Boolean) : ViewAction()
        class SetData(val countriesUiModel: List<CountryUiModel>) : ViewAction()
        object ShowErrorMsg : ViewAction()
    }

    fun loadCountries() {
        viewAction.value = ViewAction.Loading(true)
        viewModelScope.launch {
            repository.getCovidByCountry().onSuccess {
                viewAction.postValue(ViewAction.SetData(it))
            }.onFailure {
                viewAction.postValue(ViewAction.ShowErrorMsg)
            }
        }
    }
}