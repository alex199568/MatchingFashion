package com.test.data

import com.test.data.mf.MFApi
import com.test.data.mf.Result

class ProductsRepo(
    private val mfApi: MFApi,
    private val currenciesRepo: CurrenciesRepo
) {

    private var responseCache: List<Result> = listOf()
    private var responseReceivedAt: Long = 0L

    suspend fun getProducts(): List<Product> {
        val currentTime = System.currentTimeMillis()
        val timeDiff = currentTime - responseReceivedAt
        if (timeDiff > CacheLifetime || responseCache.isEmpty()) {
            responseCache = mfApi.getProducts().results
            responseReceivedAt = System.currentTimeMillis()
        }
        val selectedCurrency = currenciesRepo.getSelectedCurrency()
        return responseCache.map {
            val convertedPrice = currenciesRepo.convert(it.price.value)
            Product(
                name = it.name,
                designer = it.designer.name,
                imageUrl = "https:" + it.primaryImageMap.thumbnail.url,
                gbpPrice = it.price.formattedValue,
                altCurrencyPrice = "${selectedCurrency.displayName} $convertedPrice"
            )
        }
    }

    companion object {

        private const val CacheLifetime = 5L * 60L * 1000L
    }
}