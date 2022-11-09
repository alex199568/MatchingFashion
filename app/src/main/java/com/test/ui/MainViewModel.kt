package com.test.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.data.mf.MFApi
import com.test.data.mf.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    val api: MFApi
) : ViewModel() {

    val productsFlow = MutableStateFlow(listOf<Result>())

    init {
        viewModelScope.launch {
            val response = api.getProducts()
            productsFlow.value = response.results
        }
    }
}