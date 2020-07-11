package br.com.jdscaram.covid.repository

import br.com.jdscaram.core.model.CountryUiModel
import br.com.jdscaram.webservice.api.CovidApi
import br.com.jdscaram.core.DispatcherProvider
import br.com.jdscaram.webservice.core.Webservice
import br.com.jdscaram.webservice.model.mapper.CountryResponseToUiModelMapper
import kotlinx.coroutines.withContext
import java.lang.Exception

class CovidRepositoryImpl(
    private val webservice: Webservice,
    private val dispatcher: DispatcherProvider
) : CovidRepository {

    private val request = webservice.createRequest(CovidApi::class.java)
    private val mapperCountry = CountryResponseToUiModelMapper()

    override suspend fun getCovidByCountry(): Result<List<CountryUiModel>> =
        withContext(dispatcher.io()) {
            val result = webservice.executeRequest(request.getCountries()).getOrThrow()
            if (result != null) {
                Result.success(mapperCountry.mapFrom(result))
            } else {
                Result.failure(Exception("Country not found"))
            }
        }
}
