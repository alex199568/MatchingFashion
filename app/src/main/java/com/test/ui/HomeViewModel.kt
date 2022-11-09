package com.test.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.data.Product
import com.test.data.ProductsRepo
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
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