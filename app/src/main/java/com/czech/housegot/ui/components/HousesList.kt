package com.czech.housegot.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.czech.housegot.R
import com.czech.housegot.models.Houses
import com.czech.housegot.ui.theme.*
import com.czech.housegot.utils.extractInt

@Composable
fun HousesList(
    list: LazyPagingItems<Houses>,
    observeLoadStates: @Composable () -> Unit,
    onHouseClicked: (Int, Int) -> Unit,
    modifier: Modifier,
    listState: LazyListState
) {

    LazyColumn(
        state = listState,
        modifier = modifier
            .fillMaxSize()
            .padding(start = 4.dp, end = 4.dp, top = 4.dp)
    ) {
        itemsIndexed(
            items = list,
            key = { _, item ->
                item.id
            }
        ) { index, house ->
            val color = remember {
                when {
                    index % 11 == 0 -> sage
                    index % 11 == 1 -> apricot
                    index % 11 == 2 -> rock_blue
                    index % 11 == 3 -> scarpa
                    index % 11 == 4 -> purple
                    index % 11 == 5 -> melrose
                    index % 11 == 6 -> yellow
                    index % 11 == 7 -> orange
                    index % 11 == 8 -> emerald
                    index % 11 == 9 -> minsk
                    else -> blue
                }
            }

            val colorInt = remember {
                when (color) {
                    sage -> 0
                    apricot -> 1
                    rock_blue -> 2
                    scarpa -> 3
                    purple -> 4
                    melrose -> 5
                    yellow -> 6
                    orange -> 7
                    emerald -> 8
                    minsk -> 9
                    else -> 10
                }
            }
            HousesListItem(
                house = house?.name.toString(),
                date = house?.founded.toString(),
                colors = color,
                onClick = {
                    extractInt(house?.url.toString())?.let { onHouseClicked(it, colorInt) }
                },
                modifier = Modifier
            )
        }
        item {
            observeLoadStates()
        }
    }
}

@Composable
fun HousesListItem(
    modifier: Modifier,
    house: String,
    date: String,
    colors: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .padding(4.dp)
            .height(150.dp)
            .clickable(
                enabled = true,
                onClick = {
                    onClick()
                }
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = colors
        )
    ) {

        val constrains = ConstraintSet {
            val arrow = createRefFor("arrow")
            val houseRow = createRefFor("houseRow")
            val dateText = createRefFor("date")

            constrain(arrow) {
                top.linkTo(parent.top, margin = 11.dp)
                end.linkTo(parent.end, margin = 11.dp)
            }
            constrain(houseRow) {
                bottom.linkTo(dateText.top)
                start.linkTo(dateText.start)
            }
            constrain(dateText) {
                bottom.linkTo(parent.bottom, margin = 10.dp)
                start.linkTo(parent.start, margin = 11.dp)
            }
        }

        ConstraintLayout(
            constraintSet = constrains,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                modifier = Modifier
                    .layoutId("arrow"),
                painter = painterResource(id = R.drawable.arrow),
                contentDescription = "arrow icon",
            )
            Row(
                modifier = Modifier
                    .layoutId("houseRow"),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(id = R.drawable.house),
                    contentDescription = "house icon",
                )
                Spacer(
                    modifier = Modifier.width(4.dp)
                )
                Text(
                    text = house,
                    color = black,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W500,
                    fontFamily = FontFamily.SansSerif,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(bottom = 1.dp)
                )
            }
            if (date.isNotEmpty()) {
                Text(
                    text = "Since $date",
                    color = black,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W500,
                    fontFamily = FontFamily.SansSerif,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .layoutId("date")
                )
            }

        }
    }
}