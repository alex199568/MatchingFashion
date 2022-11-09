package com.test.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.data.CurrenciesRepo
import com.test.data.currency.Currency
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val currenciesRepo: CurrenciesRepo
) : ViewModel() {

    val selectedCurrencyFlow = MutableStateFlow(Currency.Gbp)

    init {
        viewModelScope.launch {
            selectedCurrencyFlow.value = currenciesRepo.getSelectedCurrency()
        }
    }

    fun selectCurrency(currency: Currency) {
        viewModelScope.launch {
            currenciesRepo.saveSelectedCurrency(currency)
            selectedCurrencyFlow.value = currency
        }
    }
}