package com.test

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.test.data.CurrenciesRepo
import com.test.data.ProductsRepo
import com.test.data.currency.CurrencyApi
import com.test.data.mf.MFApi
import com.test.ui.MainViewModel
import com.test.ui.SettingsViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent.inject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val apiModule = module {
    single<GsonConverterFactory> {
        GsonConverterFactory.create()
    }

    single<HttpLoggingInterceptor> {
        HttpLoggingInterceptor()
    }

    single<OkHttpClient> {
        val builder = OkHttpClient.Builder()
        val loggingInterceptor = get<HttpLoggingInterceptor>()
        builder.addInterceptor(loggingInterceptor)
        builder.build()
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
            .client(get())
            .addConverterFactory(get<GsonConverterFactory>())
            .build()

        retrofit.create(CurrencyApi::class.java)
    }
}

private val storageModule = module {

    single<SharedPreferences> {
        androidContext().getSharedPreferences("currencies", Context.MODE_PRIVATE)
    }
}

private val repoModule = module {
    single<CurrenciesRepo> {
        CurrenciesRepo(get(), get())
    }

    single<ProductsRepo> {
        ProductsRepo(get(), get())
    }
}

private val viewModelModule = module {

    viewModel { MainViewModel(get()) }
    viewModel { SettingsViewModel(get()) }
}

class App : Application() {

    private val currencyApi: CurrencyApi by inject()

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(apiModule, storageModule, repoModule, viewModelModule)
        }
    }
}