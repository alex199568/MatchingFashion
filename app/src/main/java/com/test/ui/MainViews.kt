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
import com.test.data.*
import com.test.ui.theme.MatchingFasionTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(viewModel: MainViewModel = koinViewModel()) {
    Column {

        Text(text = "Matching Fashion")

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
        Text(text = "Code: ${product.code}")
        Text(text = "Name: ${product.name}")
        Text(text = "Designer: ${product.designer.name}")
        Text(text = "Price: ${product.price.formattedValue}")

        println("https:" + product.primaryImageMap.thumbnail.url)

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https:" + product.primaryImageMap.thumbnail.url)
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
        code = "73843",
        name = "Some product",
        designer = Designer(name = "Designer"),
        primaryImageMap = ImageMap(
            thumbnail =
            ApiImage(
                url = "https://image.shutterstock.com/image-photo/mountains-under-mist-morning-amazing-260nw-1725825019.jpg"
            )
        ),
        price = Price(formattedValue = "1000")
    )
    ProductView(product = product)
}
