package com.czech.housegot.ui.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.czech.housegot.models.Houses
import com.czech.housegot.ui.components.HousesList

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HousesScreen(
    onHouseClicked: (Int) -> Unit,
    viewModel: HousesViewModel
) {

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            Text(
                text = "G.O.T Houses",
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 24.sp,
                fontWeight = FontWeight.W700,
                fontFamily = FontFamily.SansSerif,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 14.dp, start = 16.dp)
            )
        }
    ) {

        val houses = viewModel.getPagedHouses().collectAsLazyPagingItems()

        HousesList(
            list = houses,
            observeLoadStates = {
                ObserveLoadStates(
                    loadState = houses.loadState.mediator,
                    houses = houses,
                    snackbarHostState = snackbarHostState
                )
            },
            onHouseClicked = {
                onHouseClicked(it)
            },
            modifier = Modifier
        )


    }
}

@Composable
fun ObserveLoadStates(
    loadState: LoadStates?,
    houses: LazyPagingItems<Houses>,
    snackbarHostState: SnackbarHostState
) {

    houses.apply {
        when {
            loadState?.refresh is LoadState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier
                    )
                }
            }
            loadState?.append is LoadState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
            loadState?.refresh is LoadState.Error -> {

                val error = (loadState.refresh as LoadState.Error).error

                LaunchedEffect(snackbarHostState) {
                    val snackbarResult = snackbarHostState.showSnackbar(
                        message = error.message.toString(),
                        actionLabel = "REFRESH",
                        duration = SnackbarDuration.Long,
                    )
                    when(snackbarResult) {
                        SnackbarResult.ActionPerformed -> {
                            houses.refresh()
                        }
                        else -> {}
                    }
                }
            }
            loadState?.append is LoadState.Error -> {

                val error = (loadState.append as LoadState.Error).error

                LaunchedEffect(snackbarHostState) {
                    val snackbarResult = snackbarHostState.showSnackbar(
                        message = error.message.toString(),
                        actionLabel = "REFRESH",
                        duration = SnackbarDuration.Long,
                    )
                    when(snackbarResult) {
                        SnackbarResult.ActionPerformed -> {
                            houses.refresh()
                        }
                        else -> {}
                    }
                }
            }
        }
    }
}