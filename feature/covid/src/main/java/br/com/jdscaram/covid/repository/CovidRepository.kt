package br.com.jdscaram.covid.repository

import br.com.jdscaram.core.model.CountryUiModel

interface CovidRepository {
    suspend fun getCovidByCountry(): Result<List<CountryUiModel>>
}