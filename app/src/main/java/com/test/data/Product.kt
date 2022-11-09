package com.test.data

data class Product(
    val name: String,
    val designer: String,
    val imageUrl: String,
    val gbpPrice: String,
    val altCurrencyPrice: String?
) {
}