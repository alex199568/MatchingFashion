package com.test.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.test.data.Product
import com.test.data.mf.*
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(viewModel: MainViewModel = koinViewModel()) {
    Column {

        val products = viewModel.productsFlow.collectAsState().value

        LazyColumn {
            items(products) { product ->
                ProductView(product = product)
                Divider()
            }
        }
    }
}

@Composable
fun ProductView(product: Product) {
    Column {
        Text(text = "Name: ${product.name}")
        Text(text = "Designer: ${product.designer}")
        Text(text = "Price: ${product.gbpPrice}")
        Text(text = "Alt price: ${product.altCurrencyPrice}")

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(product.imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = "Image",
            contentScale = ContentScale.Crop,
        )
    }
}

@Composable()
@Preview(showBackground = true)
fun ProductPreview() {
    val product = Product(
        name = "Product",
        designer = "Designer",
        imageUrl = "",
        gbpPrice = "1000",
        altCurrencyPrice = "1000"
    )
    ProductView(product)
}
