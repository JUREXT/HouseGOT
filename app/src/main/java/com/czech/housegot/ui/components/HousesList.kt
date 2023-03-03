package com.czech.housegot.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@Composable
fun HousesList(
    list: LazyPagingItems<Houses>,
    observeLoadStates: @Composable () -> Unit,
    modifier: Modifier,
) {

//    val data = listOf("House of valor", "House of valor", "House of valor", "House of valor", "House of valor", "House of valor", "House of valor", "House of valor", "House of valor", "House of valor", "House of valor", "House of valor", "House of valor", "House of valor", "House of valor", "House of valor", "House of valor", "House of valor")

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 4.dp, end = 4.dp, top = 4.dp)
    ) {
        itemsIndexed(
            items = list,
        ) { index, house ->
            var color: Color = blue
            when {
                index % 11 == 0 -> color = sage
                index % 11 == 1 -> color = apricot
                index % 11 == 2 -> color = rock_blue
                index % 11 == 3 -> color = scarpa
                index % 11 == 4 -> color = purple
                index % 11 == 5 -> color = melrose
                index % 11 == 6 -> color = yellow
                index % 11 == 7 -> color = orange
                index % 11 == 8 -> color = emerald
                index % 11 == 9 -> color = minsk
            }
            HousesListItem(
                house = house?.name.toString(),
                colors = color,
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
    colors: Color
) {
    Card(
        modifier = modifier
            .padding(4.dp)
            .height(150.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = colors
        )
    ) {

        val constrains = ConstraintSet {
            val arrow = createRefFor("arrow")
            val houseRow = createRefFor("houseRow")

            constrain(arrow) {
                top.linkTo(parent.top, margin = 11.dp)
                end.linkTo(parent.end, margin = 11.dp)
            }
            constrain(houseRow) {
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
        }
    }
}