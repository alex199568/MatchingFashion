package com.test.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.data.MFApi
import com.test.data.Product
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    val api: MFApi
) : ViewModel() {

    val productsFlow = MutableStateFlow(listOf<Product>())

    init {
        viewModelScope.launch {
            val response = api.getProducts()
            productsFlow.value = response.results
        }
    }
}