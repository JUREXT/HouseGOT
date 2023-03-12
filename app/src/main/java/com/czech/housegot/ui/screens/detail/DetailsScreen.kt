package com.czech.housegot.ui.screens.detail

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.czech.housegot.R
import com.czech.housegot.ui.components.HouseDetails
import com.czech.housegot.utils.extractInt
import com.czech.housegot.utils.states.DetailsState

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
            viewModel = viewModel,
            snackbarHostState = snackbarHostState,
            onBackPressed = {onBackPressed()}
        )
    }
}

@Composable
fun Observe(
    viewModel: DetailsViewModel,
    snackbarHostState: SnackbarHostState,
    onBackPressed: () -> Unit
) {
    when (val state = viewModel.detailsState.collectAsState().value) {
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

            LaunchedEffect(key1 = Unit) {
                viewModel.getCharacters(
                    founderId = extractInt(house?.founder.toString()),
                    lordId = extractInt(house?.currentLord.toString()),
                    heirId = extractInt(house?.heir.toString())
                )
            }

            val characters = viewModel.characterState.collectAsState().value

            HouseDetails(
                house = house?.name.toString(),
                founder = characters.founder.toString(),
                founded = house?.founded.toString(),
                region = house?.region.toString(),
                lord = characters.lord.toString(),
                heir = characters.heir.toString(),
                quote = house?.coatOfArms.toString(),
                colorInt = viewModel.colorInt!!
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


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
fun DetailsScreenPreview() {
    Scaffold(
        topBar = {
            IconButton(
                onClick = { }
            ) {
                Icon(
                    painter = painterResource(id = if (isSystemInDarkTheme()) R.drawable.arrow_back_white else R.drawable.arrow_back_black),
                    contentDescription = "back_button"
                )
            }
        }
    ) {
        HouseDetails(
            house = "House Arryn of the Eyrie",
            founder = "Artys | Arryn",
            founded = "Coming of the Andals",
            region = "The Vale",
            lord = "Robert Arryn",
            heir = "Harrold Hardyng",
            quote = "A sky-blue falcon soaring against a white moon, on a sky-blue field(Bleu celeste, upon a plate a falcon volant of the field)",
            colorInt = 6
        )
    }
}