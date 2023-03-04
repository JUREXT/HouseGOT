package com.czech.housegot.ui.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.czech.housegot.R
import androidx.compose.ui.res.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailsScreen(
    onBackPressed: () -> Unit,
    viewModel: HousesViewModel
) {
    Scaffold(
        topBar = {
            IconButton(
                onClick = { onBackPressed() }
            ) {
                Icon(
                    painter = painterResource(id = if (isSystemInDarkTheme()) R.drawable.arrow_back_white else R.drawable.arrow_back_black),
                    contentDescription = "back_button"
                )
            }
        }
    ) {

    }
}