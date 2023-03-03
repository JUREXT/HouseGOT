package com.czech.housegot.ui.screens

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadStates
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.czech.housegot.models.Houses
import com.czech.housegot.ui.components.HousesList
import com.czech.housegot.ui.theme.black
import com.czech.housegot.ui.theme.white

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HousesScreen(
    viewModel: HousesViewModel
) {
    Scaffold(
        topBar = {
            Text(
                text = "G.O.T Houses",
                color = if (isSystemInDarkTheme()) white else black,
                fontSize = 24.sp,
                fontWeight = FontWeight.W700,
                fontFamily = FontFamily.SansSerif,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 14.dp, start = 16.dp)
            )
        }
    ) { paddingValues ->

        val houses = viewModel.getPagedHouses().collectAsLazyPagingItems()

        HousesList(
            list = houses,
            observeLoadStates = {
                ObserveLoadStates(
                    loadState = houses.loadState.mediator,
                    houses = houses
                )
            },
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        )
    }
}

@Composable
fun ObserveLoadStates(
    loadState: LoadStates?,
    houses: LazyPagingItems<Houses>
) {

}