package com.czech.housegot.ui.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.czech.housegot.R
import androidx.compose.ui.res.painterResource
import com.czech.housegot.ui.components.HouseDetails
import com.czech.housegot.utils.DetailsState
import kotlinx.coroutines.flow.MutableStateFlow

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailsScreen(
    onBackPressed: () -> Unit,
    viewModel: DetailsViewModel
) {

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
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
        Observe(
            detailsState = viewModel.detailsState,
            snackbarHostState = snackbarHostState,
            onBackPressed = {onBackPressed()}
        )
    }
}

@Composable
fun Observe(
    detailsState: MutableStateFlow<DetailsState?>,
    snackbarHostState: SnackbarHostState,
    onBackPressed: () -> Unit
) {
    when (val state = detailsState.collectAsState().value) {
        is DetailsState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
        }
        is DetailsState.Success -> {
            val house = state.data
            HouseDetails(
                house = house?.name.toString(),
                founder = house?.founder.toString(),
                founded = house?.founded.toString(),
                region = house?.region.toString(),
                lord = house?.currentLord.toString(),
                heir = house?.heir.toString(),
                quote = house?.coatOfArms.toString()
            )
        }
        is DetailsState.Error -> {
            LaunchedEffect(snackbarHostState) {
                val snackbarResult = snackbarHostState.showSnackbar(
                    message = state.message,
                    actionLabel = "GO BACK",
                    duration = SnackbarDuration.Long,
                )
                when(snackbarResult) {
                    SnackbarResult.ActionPerformed -> {
                        onBackPressed()
                    }
                    else -> {}
                }
            }
        }
        else -> {}
    }
}