package br.com.jdscaram.webservice.core

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Converter
import retrofit2.Retrofit
import java.io.IOException

private const val UNKNOWN_ERROR_CODE = -1
private const val UNKNOWN_ERROR_MESSAGE = "Unknown error"
private const val PARSE_ERROR_BODY_MESSAGE = "Fail to parse error body"

class WebserviceImpl(
    private val retrofit: Retrofit
) : Webservice {
    override fun <T> createRequest(api: Class<T>): T = retrofit.create(api)

    override fun <T> executeRequest(call: Call<T>): Result<T?> {
        return try {
            val response = call.execute()
            val responseBody = response.body()
            if (response.isSuccessful) {
                Result.success(responseBody)
            } else {
                val errorBody = response.errorBody()
                if (errorBody != null) {
                    val errorConverter: Converter<ResponseBody, HttpError> =
                        retrofit.responseBodyConverter(
                            HttpError::class.java,
                            arrayOfNulls<Annotation>(0)
                        )
                    val error: HttpError? = errorConverter.convert(errorBody)
                    Result.failure(Exception("${error?.code} + ${error?.msg}"))
                } else {
                    Result.failure(Exception("$UNKNOWN_ERROR_CODE + $PARSE_ERROR_BODY_MESSAGE"))
                }
            }
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

    data class HttpError(val code: Int?, val msg: String?)
}