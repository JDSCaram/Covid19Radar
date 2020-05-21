package br.com.jdscaram.webservice.core

import retrofit2.Call

interface Webservice {
    fun <T> createRequest(api: Class<T>): T

    fun <T> executeRequest(call: Call<T>): Result<T?>
}