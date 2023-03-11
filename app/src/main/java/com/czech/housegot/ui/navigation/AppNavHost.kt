package com.czech.housegot.ui.navigation

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.czech.housegot.ui.screens.detail.DetailsScreen
import com.czech.housegot.ui.screens.detail.DetailsViewModel
import com.czech.housegot.ui.screens.houses.HousesScreen
import com.czech.housegot.ui.screens.houses.HousesViewModel

@SuppressLint("UnrememberedGetBackStackEntry")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screens.HousesScreen.route
    ) {
        fun onBackPressed() {
            if (navController.previousBackStackEntry != null) navController.navigateUp()
        }

        composable(
            route = Screens.HousesScreen.route
        ) {
            val viewModel = hiltViewModel<HousesViewModel>()
            HousesScreen(
                viewModel = viewModel,
                onHouseClicked = { house_id, color_int ->
                    navController.navigate(Screens.DetailsScreen.route + "/$house_id" + "/$color_int")
                }
            )
        }
        composable(
            route = Screens.DetailsScreen.route + "/{house_id}" + "/{color_int}",
            arguments = listOf(
                navArgument("house_id") {
                    type = NavType.IntType
                    nullable = false
                },
                navArgument("color_int") {
                    type = NavType.IntType
                    nullable = false
                }
            )
        ) { backStackEntry ->

            val housesEntry = remember(backStackEntry) {
                navController.getBackStackEntry(Screens.DetailsScreen.route + "/{house_id}" + "/{color_int}")
            }

            val viewModel = hiltViewModel<DetailsViewModel>(housesEntry)

            DetailsScreen(
                onBackPressed = { onBackPressed() },
                viewModel = viewModel
            )
        }
    }
}