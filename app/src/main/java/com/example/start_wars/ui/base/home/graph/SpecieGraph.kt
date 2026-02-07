package com.example.start_wars.ui.base.home.graph

import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.start_wars.data.model.Specie
import com.example.start_wars.ui.base.components.btab.BaseTopAppBarState
import com.example.start_wars.ui.base.home.Routes
import com.example.start_wars.ui.base.screen.species.form.AddSpecieScreen
import com.example.start_wars.ui.base.screen.species.form.SpeciesFormViewModel
import com.example.start_wars.ui.base.screen.species.list.SpeciesListScreen
import com.example.start_wars.ui.base.screen.species.list.SpeciesListViewModel

/**
 * Función de extensión para construir el grafo de navegación de Especies.
 */
fun NavGraphBuilder.speciesGraph(
    navController: NavHostController,
    onShowSnackBar: (String) -> Unit,
    onConfigureTopAppBar: (BaseTopAppBarState) -> Unit,
    onOpenDrawer: () -> Unit
) {
    navigation(
        route = Routes.SPECIES_GRAPH,
        startDestination = Routes.SPECIE_LIST
    ) {

        // 1. Pantalla de Listado
        composable(Routes.SPECIE_LIST) {
            val viewModel = hiltViewModel<SpeciesListViewModel>()

            SpeciesListScreen(
                modifier = Modifier,
                viewModel = viewModel,
                onShowSnackBar = onShowSnackBar,
                onConfigureTopAppBar = onConfigureTopAppBar,
                onOpenDrawer = onOpenDrawer,
                onNavigateToAbout = {
                    navController.navigate(Routes.ABOUT) {
                        launchSingleTop = true
                    }
                },
                onNavigateToEdit = { specie ->
                    navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.set("specie", specie)

                    navController.navigate(Routes.SPECIE_ADD)
                }
            )
        }

        // 2. Pantalla de Creación
        composable(Routes.SPECIE_ADD) {
            val viewModel = hiltViewModel<SpeciesFormViewModel>()

            val previousEntry = navController.previousBackStackEntry
            val savedStateHandle = previousEntry?.savedStateHandle
            val specieToEdit = savedStateHandle?.get<Specie>("specie")

            if (specieToEdit != null) {
                savedStateHandle.remove<Specie>("specie")
            }

            AddSpecieScreen(
                viewModel = viewModel,
                specieToEdit = specieToEdit,
                onNavigateBack = { navController.popBackStack() },
                onConfigureTopAppBar = onConfigureTopAppBar
            )
        }
    }
}