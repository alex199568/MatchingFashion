package com.test.data

data class Product(
    val code: String,
    val name: String,
    val designer: Designer,
    val primaryImageMap: ImageMap,
    val price: Price
) {
}