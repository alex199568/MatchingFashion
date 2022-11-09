package com.test.data

import android.content.SharedPreferences
import com.test.data.currency.Currency
import com.test.data.currency.CurrencyApi
import com.test.data.currency.Rates

class CurrenciesRepo(
    private val prefs: SharedPreferences,
    private val currencyApi: CurrencyApi
) {

    private var ratesCache: Rates? = null
    private var ratesReceivedAt: Long = 0L

    suspend fun saveSelectedCurrency(currency: Currency) {
        prefs.edit().putInt(CurrencyKey, currency.id).apply()
    }

    suspend fun getSelectedCurrency(): Currency {
        val id = prefs.getInt(CurrencyKey, -1)
        return Currency.values().firstOrNull { it.id == id } ?: Currency.Gbp
    }

    suspend fun convert(price: Int): Float {
        val currentTime = System.currentTimeMillis()
        val timeDiff = currentTime - ratesReceivedAt
        if (ratesCache == null || timeDiff > CacheLifetime) {
            val rates = currencyApi.getRates("GBP")
            ratesCache = rates.rates
            ratesReceivedAt = System.currentTimeMillis()
        }
        val selectedCurrency = getSelectedCurrency()
        val rate = ratesCache?.rateFor(selectedCurrency) ?: 1f
        return price * rate
    }

    companion object {

        private const val CurrencyKey = "currency"
        private const val CacheLifetime = 1L * 60L * 1000L
    }
}