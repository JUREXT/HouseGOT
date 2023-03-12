package com.czech.housegot.ui.screens.houses

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.czech.housegot.models.Houses
import com.czech.housegot.ui.components.HousesList
import com.czech.housegot.ui.theme.*
import kotlinx.coroutines.flow.flowOf

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HousesScreen(
    onHouseClicked: (Int, Int) -> Unit,
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

        val houses = remember {
            viewModel.getPagedHouses()
        }

        val pagedHouses = houses.collectAsLazyPagingItems()

        HousesList(
            list = pagedHouses,
            observeLoadStates = {
                ObserveLoadStates(
                    loadState = pagedHouses.loadState.mediator,
                    houses = pagedHouses,
                    snackbarHostState = snackbarHostState
                )
            },
            onHouseClicked = { id, color ->
                onHouseClicked(id, color)
            },
            listState = rememberLazyListState(),
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


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
fun HousesScreenPreview() {
    Scaffold(
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
        val houses = listOf(
            Houses(
                id = 1,
                name = "House Arryn of the Eyrie",
                founder = "68 BC",
                page = 1
            ),
            Houses(
                id = 2,
                name = "House Arryn of the Eyrie",
                founder = "68 BC",
                page = 1
            ),
            Houses(
                id = 3,
                name = "House Arryn of the Eyrie",
                founder = "68 BC",
                page = 1
            ),
            Houses(
                id = 4,
                name = "House Arryn of the Eyrie",
                founder = "68 BC",
                page = 1
            ),
            Houses(
                id = 5,
                name = "House Arryn of the Eyrie",
                founder = "68 BC",
                page = 1
            ),
            Houses(
                id = 6,
                name = "House Arryn of the Eyrie",
                founder = "68 BC",
                page = 1
            ),
            Houses(
                id = 7,
                name = "House Arryn of the Eyrie",
                founder = "68 BC",
                page = 1
            ),
            Houses(
                id = 8,
                name = "House Arryn of the Eyrie",
                founder = "68 BC",
                page = 1
            ),
            Houses(
                id = 9,
                name = "House Arryn of the Eyrie",
                founder = "68 BC",
                page = 1
            ),
            Houses(
                id = 10,
                name = "House Arryn of the Eyrie",
                founder = "68 BC",
                page = 1
            )

        )

        val paged = flowOf(PagingData.from(houses)).collectAsLazyPagingItems()

        HousesList(
            list = paged,
            observeLoadStates = {},
            onHouseClicked = { _, _ -> },
            listState = rememberLazyListState(),
            modifier = Modifier
        )
    }
}