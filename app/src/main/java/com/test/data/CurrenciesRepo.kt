package com.test.data

import android.content.SharedPreferences
import com.test.data.currency.Currency

class CurrenciesRepo(
    private val prefs: SharedPreferences
) {

    suspend fun saveSelectedCurrency(currency: Currency) {
        prefs.edit().putInt(CurrencyKey, currency.id).apply()
    }

    suspend fun getSelectedCurrency(): Currency {
        val id = prefs.getInt(CurrencyKey, -1)
        return Currency.values().firstOrNull { it.id == id } ?: Currency.Gbp
    }

    companion object {

        private const val CurrencyKey = "currency"
    }
}