package com.test.domain.model

data class Product(
    val name: String,
    val designer: String,
    val imageUrl: String,
    val gbpPrice: String,
    val altCurrencyPrice: String
) {
}