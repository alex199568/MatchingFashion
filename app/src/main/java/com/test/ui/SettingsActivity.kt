package com.test.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.test.ui.theme.MatchingFasionTheme

class SettingsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MatchingFasionTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column {
                        TopAppBar(
                            title = {
                                Text(text = "Settings")
                            },
                            navigationIcon = {
                                IconButton(onClick = {
                                    finish()
                                }) {
                                    Icon(Icons.Filled.ArrowBack,"back")
                                }
                            },
                            backgroundColor = Color.Blue,
                            contentColor = Color.White,
                            elevation = 12.dp
                        )

                        SettingsScreen()
                    }
                }
            }
        }
    }
}