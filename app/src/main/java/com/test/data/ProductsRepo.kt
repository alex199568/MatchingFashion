package com.test.data

import com.test.data.mf.MFApi
import com.test.data.mf.Result
import kotlinx.coroutines.delay
import java.text.NumberFormat
import java.util.*

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
            val results = try {
                mfApi.getProducts().results
            } catch (t: Throwable) {
                listOf<Result>()
            }
            if (results.isNotEmpty()) {
                responseCache = results
                responseReceivedAt = System.currentTimeMillis()
            }
        }
        val selectedCurrency = currenciesRepo.getSelectedCurrency()
        val format: NumberFormat = NumberFormat.getCurrencyInstance()
        format.maximumFractionDigits = 2
        format.minimumFractionDigits = 2
        format.currency = Currency.getInstance(selectedCurrency.displayName)
        return responseCache.map {
            val altPriceString = if (selectedCurrency != com.test.data.currency.Currency.Gbp) {
                val convertedPrice = currenciesRepo.convert(it.price.value)
                format.format(convertedPrice)
            } else null
            Product(
                name = it.name,
                designer = it.designer.name,
                imageUrl = "https:" + it.primaryImageMap.large.url,
                gbpPrice = it.price.formattedValue,
                altCurrencyPrice = altPriceString
            )
        }
    }

    companion object {

        private const val CacheLifetime = 5L * 60L * 1000L
    }
}