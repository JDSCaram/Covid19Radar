package br.com.jdscaram.webservice.model


import com.google.gson.annotations.SerializedName

data class CountryResponse(
    @SerializedName("Country")
    val country: String,
    @SerializedName("ISO2")
    val iSO2: String,
    @SerializedName("Slug")
    val slug: String
)
