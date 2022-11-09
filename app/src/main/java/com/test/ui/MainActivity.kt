package com.test.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.test.ui.theme.MatchingFasionTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MatchingFasionTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        TopAppBar(
                            title = {
                                Text(text = "Matching Fashion")
                            },
                            backgroundColor = Color.Blue,
                            contentColor = Color.White,
                            elevation = 12.dp,
                            actions = {
                                IconButton(onClick = {
                                    val intent = Intent(this@MainActivity, SettingsActivity::class.java)
                                    startActivity(intent)
                                }) {
                                    Icon(Icons.Filled.Settings,"settings")
                                }
                            }
                        )

                        HomeScreen()
                    }
                }
            }
        }
    }
}
