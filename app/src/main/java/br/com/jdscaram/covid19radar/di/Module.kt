package br.com.jdscaram.covid19radar.di

import android.app.Application
import br.com.jdscaram.covid.repository.CovidRepository
import br.com.jdscaram.covid.repository.CovidRepositoryImpl
import br.com.jdscaram.covid.ui.countries.model.CountriesViewModel
import br.com.jdscaram.covid19radar.ui.main.MainViewModel
import br.com.jdscaram.core.DispatcherProvider
import br.com.jdscaram.core.DispatcherProviderImpl
import br.com.jdscaram.webservice.core.Webservice
import br.com.jdscaram.webservice.core.WebserviceImpl
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val baseUrl = "https://api.covid19api.com"

@ExperimentalCoroutinesApi
val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { CountriesViewModel(get()) }
}

val networkingModule = module {

    fun provideCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    fun provideHttpClient(cache: Cache): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .cache(cache)

        return okHttpClientBuilder.build()
    }

    fun provideGson(): Gson {
        return GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()
    }

    fun provideRetrofit(factory: Gson, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(factory))
            .client(client)
            .build()
    }

    fun provideWebservice(retrofit: Retrofit): Webservice {
        return WebserviceImpl(retrofit)
    }
    single { provideCache(androidApplication()) }
    single { provideHttpClient(get()) }
    single { provideGson() }
    single { provideRetrofit(get(), get()) }

    single { provideWebservice(get()) }

}

val repositoryModule = module {

    fun provideCovidRepository(
        webservice: Webservice,
        dispatcherProvider: DispatcherProvider
    ): CovidRepository {
        return CovidRepositoryImpl(webservice, dispatcherProvider)
    }

    single { provideCovidRepository(get(), get()) }
    single<DispatcherProvider> { DispatcherProviderImpl() }
}
