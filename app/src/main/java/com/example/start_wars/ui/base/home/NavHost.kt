package com.example.start_wars.ui.base.home

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.start_wars.ui.base.common.AboutUsScreen
import com.example.start_wars.ui.base.components.btab.BaseTopAppBarState
import com.example.start_wars.ui.base.home.graph.speciesGraph

object Routes {
    // Especies
    const val SPECIES_GRAPH = "species_graph"
    const val SPECIE_LIST = "list"
    const val SPECIE_ADD = "add"

    // Otros
    const val ABOUT = "about"
}

@Composable
fun NavHostScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    onShowSnackBar: (String) -> Unit,
    onConfigureTopAppBar: (BaseTopAppBarState) -> Unit,
    onOpenDrawer: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Routes.SPECIES_GRAPH,
        modifier = modifier,
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(700)
            )
        },
        exitTransition = {
            fadeOut(animationSpec = tween(700))
        }
    ) {

        speciesGraph(
            navController = navController,
            onShowSnackBar = onShowSnackBar,
            onConfigureTopAppBar = onConfigureTopAppBar,
            onOpenDrawer = onOpenDrawer
        )
        // About Us
        composable(Routes.ABOUT) {
            AboutUsScreen()
        }
    }
}
