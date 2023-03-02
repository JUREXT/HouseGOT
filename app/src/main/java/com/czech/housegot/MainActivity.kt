package com.czech.housegot

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.czech.housegot.ui.navigation.AppNavHost
import com.czech.housegot.ui.theme.HouseGOTTheme
import dagger.hilt.android.AndroidEntryPoint

@RequiresApi(Build.VERSION_CODES.O)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HouseGOTApp()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HouseGOTApp() {
    HouseGOTTheme {
        val navController = rememberNavController()
        AppNavHost(
            navController = navController
        )
    }
}