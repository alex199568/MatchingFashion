package com.test

import android.app.Application
import com.test.data.MFApi
import com.test.ui.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

private val myModule = module {

    single<MFApi?> {
        println("starting")

//        try {
//
//        } catch (t: Throwable) {
//            t.printStackTrace()
//            null
//        }

        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.matchesfashion.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        println("retrofit is built")

        val result = retrofit.create(MFApi::class.java)

        println("api is created")

        result
    }

    viewModel { MainViewModel(get()) }
}

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        // start Koin!
        startKoin {
            // declare used Android context
            androidContext(this@App)
            // declare modules
            modules(myModule)
        }
    }
}