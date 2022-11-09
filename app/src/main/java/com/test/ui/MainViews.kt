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
import com.test.data.mf.*
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(viewModel: MainViewModel = koinViewModel()) {
    Column {

        val products = viewModel.productsFlow.collectAsState().value

        LazyColumn {
            items(products) { product ->
                ProductView(result = product)
                Divider()
            }
        }
    }
}

@Composable
fun ProductView(result: Result) {
    Column {
        Text(text = "Code: ${result.code}")
        Text(text = "Name: ${result.name}")
        Text(text = "Designer: ${result.designer.name}")
        Text(text = "Price: ${result.price.formattedValue}")

        println("https:" + result.primaryImageMap.thumbnail.url)

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https:" + result.primaryImageMap.thumbnail.url)
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
    val result = Result(
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
    ProductView(result = result)
}
