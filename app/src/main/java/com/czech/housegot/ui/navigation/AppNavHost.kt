package com.czech.housegot.ui.navigation

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
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

        composable(route = Screens.HousesScreen.route) {
            val viewModel = hiltViewModel<HousesViewModel>()
            HousesScreen(
                viewModel = viewModel
            )
        }
    }
}