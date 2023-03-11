package com.czech.housegot.ui.screens.detail

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.czech.housegot.R
import androidx.compose.ui.res.painterResource
import com.czech.housegot.models.DetailCharacters
import com.czech.housegot.ui.components.HouseDetails
import com.czech.housegot.utils.states.CharacterState
import com.czech.housegot.utils.states.DetailsState
import com.czech.housegot.utils.extractInt

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

            var characters by remember {
                mutableStateOf<DetailCharacters?>(null)
            }
            when (val charState = viewModel.characterState.collectAsState().value) {
                is CharacterState.Success -> {
                    characters = charState.data
                }
                is CharacterState.Error -> {
                    LaunchedEffect(snackbarHostState) {
                        snackbarHostState.showSnackbar(
                            message = charState.message,
                            actionLabel = "ERROR",
                            duration = SnackbarDuration.Short,
                        )
                    }
                }
                else -> {}
            }

            HouseDetails(
                house = house?.name.toString(),
                founder = characters?.founder ?: "",
                founded = house?.founded.toString(),
                region = house?.region.toString(),
                lord = characters?.lord ?: "",
                heir = characters?.heir ?: "",
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