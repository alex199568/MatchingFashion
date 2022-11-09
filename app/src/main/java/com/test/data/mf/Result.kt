package com.test.data.mf

data class Result(
    val code: String,
    val name: String,
    val designer: Designer,
    val primaryImageMap: ImageMap,
    val price: Price
) {
}