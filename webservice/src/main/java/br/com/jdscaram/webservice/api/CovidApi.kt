package br.com.jdscaram.webservice.api

import br.com.jdscaram.webservice.model.CountryResponse
import retrofit2.Call
import retrofit2.http.GET

interface CovidApi {

    @GET("countries")
    fun getCountries(): Call<List<CountryResponse>>
}
