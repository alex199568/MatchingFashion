package com.test.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.test.data.Product
import com.test.ui.theme.Shapes
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel = koinViewModel()) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        val products = viewModel.productsFlow.collectAsState().value
        if (products.isEmpty()) {
            EmptyView()
        } else {
            ProductsView(products = products)
        }
    }
}

@Composable
fun ProductsView(products: List<Product>) {
    LazyColumn(
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
        items(products) { product ->
            ProductView(product = product)
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun ProductView(product: Product) {
    Card(
        shape = Shapes.large,
    ) {

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(product.imageUrl)
                .crossfade(true)
                .build(),
            modifier = Modifier.fillMaxWidth(),
            contentDescription = "Image",
            contentScale = ContentScale.FillWidth,
        )

        Box(
            modifier = Modifier
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.White,
                            Color.Transparent
                        )
                    )
                )
                .fillMaxWidth()
                .height(256.dp)
        ) {

        }

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .fillMaxHeight()
            ,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(text = product.name, fontSize = 24.sp, textAlign = TextAlign.Start, modifier = Modifier.fillMaxWidth())
            Text(text = "by ${product.designer}", modifier = Modifier.align(Alignment.End), fontSize = 12.sp)
            Spacer(modifier = Modifier.height(230.dp))

            Card(shape = Shapes.small, backgroundColor = Color.White, modifier = Modifier.align(Alignment.End)) {
                Column(modifier = Modifier.padding(6.dp)) {
                    Text(text = "${product.gbpPrice}")
                    if (product.altCurrencyPrice != null) {
                        Text(text = "${product.altCurrencyPrice}")
                    }
                }
            }
        }
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

@Composable
fun EmptyView() {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.weight(1f))
        CircularProgressIndicator()
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
@Preview(showBackground = true)
fun EmptyPreview() {
    EmptyView()
}
