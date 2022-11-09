package com.test.data.mf

import retrofit2.http.GET

interface MFApi {

    @GET("womens/shop?format=json")
    suspend fun getProducts(): ProductsResponse
}