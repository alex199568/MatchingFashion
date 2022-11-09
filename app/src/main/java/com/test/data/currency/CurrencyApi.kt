package com.test.data.currency

import retrofit2.http.GET

interface CurrencyApi {

    @GET("latest?base=GBP")
    fun getRates(): RatesResponse
}