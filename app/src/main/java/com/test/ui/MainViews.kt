package com.test.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import com.test.data.Designer
import com.test.data.Product
import com.test.ui.theme.MatchingFasionTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(viewModel: MainViewModel = koinViewModel()) {
    Column {

        Text(text = "Matching Fashion")

        val products = viewModel.productsFlow.collectAsState().value

        LazyColumn() {
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
    }
}

@Composable()
@Preview(showBackground = true)
fun ProductPreview() {
    val product = Product(code = "73843", name = "Some product", designer = Designer(name = "Designer"))
    ProductView(product = product)
}
