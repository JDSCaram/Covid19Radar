package br.com.jdscaram.covid.ui.countries.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.jdscaram.core.model.CountryUiModel
import br.com.jdscaram.covid.repository.CovidRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class CountriesViewModel(private val repository: CovidRepository) : ViewModel() {

    val viewAction = MutableStateFlow<ViewAction>(ViewAction.ShowLoading)

    sealed class ViewAction {
        object ShowLoading : ViewAction()
        object HideLoading : ViewAction()
        object ShowErrorMsg : ViewAction()
        class SetData(val countriesUiModel: List<CountryUiModel>) : ViewAction()
    }

    fun loadCountries() {
        viewAction.value = ViewAction.ShowLoading
        viewModelScope.launch {
            repository.getCovidByCountry().onSuccess { result ->
                viewAction.value = ViewAction.HideLoading
                viewAction.value = if (result.isEmpty()) {
                    ViewAction.ShowErrorMsg
                } else {
                    ViewAction.SetData(result)
                }
            }.onFailure {
                viewAction.value = ViewAction.HideLoading
                viewAction.value = ViewAction.ShowErrorMsg
            }
        }
    }
}