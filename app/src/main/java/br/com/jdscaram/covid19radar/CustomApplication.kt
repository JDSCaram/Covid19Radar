package br.com.jdscaram.covid19radar

import android.app.Application
import br.com.jdscaram.covid19radar.di.networkingModule
import br.com.jdscaram.covid19radar.di.repositoryModule
import br.com.jdscaram.covid19radar.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CustomApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CustomApplication)
            androidLogger()
            modules(networkingModule, repositoryModule, viewModelModule)
        }
    }
}