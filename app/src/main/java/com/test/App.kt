package com.test

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.test.data.CurrenciesRepo
import com.test.data.currency.CurrencyApi
import com.test.data.mf.MFApi
import com.test.ui.MainViewModel
import com.test.ui.SettingsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val apiModule = module {
    single<GsonConverterFactory> {
        GsonConverterFactory.create()
    }

    single<MFApi> {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.matchesfashion.com/")
            .addConverterFactory(get<GsonConverterFactory>())
            .build()

        retrofit.create(MFApi::class.java)
    }

    single<CurrencyApi> {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.exchangerate.host/")
            .addConverterFactory(get<GsonConverterFactory>())
            .build()

        retrofit.create(CurrencyApi::class.java)
    }
}

private val storageModule = module {

    single<SharedPreferences> {
        androidContext().getSharedPreferences("currencies", Context.MODE_PRIVATE)
    }

    single<CurrenciesRepo> {
        CurrenciesRepo(get())
    }
}

private val viewModelModule = module {

    viewModel { MainViewModel(get()) }
    viewModel { SettingsViewModel(get()) }
}

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(apiModule, storageModule, viewModelModule)
        }
    }
}