package com.test.data

import com.test.data.mf.MFApi

class ProductsRepo(
    private val mfApi: MFApi,
    private val currenciesRepo: CurrenciesRepo
) {

    suspend fun getProducts(): List<Product> {
        val results = mfApi.getProducts().results
        val selectedCurrency = currenciesRepo.getSelectedCurrency()
        return results.map {
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
}