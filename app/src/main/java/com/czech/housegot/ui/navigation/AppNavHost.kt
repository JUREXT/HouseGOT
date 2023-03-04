package com.czech.housegot.ui.navigation

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.czech.housegot.ui.screens.DetailsScreen
import com.czech.housegot.ui.screens.HousesScreen
import com.czech.housegot.ui.screens.HousesViewModel

@SuppressLint("UnrememberedGetBackStackEntry")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavHost(
    navController: NavHostController
) {

    androidx.navigation.compose.NavHost(
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
                onHouseClicked = { house_id ->
                    navController.navigate(Screens.DetailsScreen.route + "/$house_id")
                }
            )
        }
        composable(
            route = Screens.DetailsScreen.route + "/{house_id}",
            arguments = listOf(navArgument("house_id") {
                type = NavType.IntType
            })
        ) { backStackEntry ->

            val housesEntry = remember(backStackEntry) {
                navController.getBackStackEntry(Screens.DetailsScreen.route + "/{house_id}")
            }

            val viewModel = hiltViewModel<HousesViewModel>(housesEntry)

            DetailsScreen(
                onBackPressed = { onBackPressed() },
                viewModel = viewModel
            )
        }
    }
}