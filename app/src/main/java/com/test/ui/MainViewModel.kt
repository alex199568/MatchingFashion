package com.test.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.data.Product
import com.test.data.ProductsRepo
import com.test.data.mf.MFApi
import com.test.data.mf.Result
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val productsRepo: ProductsRepo
) : ViewModel() {

    val productsFlow = MutableStateFlow(listOf<Product>())

    init {
        viewModelScope.launch {
            while (true) {
                productsFlow.value = productsRepo.getProducts()
                delay(500L)
            }
        }
    }
}