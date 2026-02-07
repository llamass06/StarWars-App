package com.example.start_wars

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.start_wars.ui.base.components.btab.BaseTopAppBar
import com.example.start_wars.ui.base.components.btab.BaseTopAppBarState
import com.example.start_wars.ui.base.components.drawer.DrawerContent
import com.example.start_wars.ui.base.components.drawer.DrawerItem
import com.example.start_wars.ui.base.home.NavHostScreen
import com.example.start_wars.ui.base.home.Routes
import com.example.start_wars.ui.theme.Start_warsTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // Items del menú lateral
    val DrawerItems = listOf(
        DrawerItem(name = R.string.specie, icon = R.drawable.adn, ruta = Routes.SPECIE_LIST)
    )

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Start_warsTheme {
                val navController = rememberNavController()
                val snackbarHostState = remember { SnackbarHostState() }
                val scope = rememberCoroutineScope()
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                // 1. ESTADO DE LA TOP APP BAR
                // Inicializamos con un estado por defecto. Las pantallas actualizarán esto al cargarse.
                var topAppBarState by remember {
                    mutableStateOf(
                        BaseTopAppBarState(
                            title = "Star Wars",
                            iconUpAction = Icons.Default.Menu, // Usamos ImageVector como definiste en tu data class
                            upAction = { scope.launch { drawerState.open() } },
                            actions = emptyList()
                        )
                    )
                }

                // 2. LÓGICA DEL FAB (Botón flotante)
                // Mantenemos esta lógica aquí para determinar a dónde navegar con el botón gigante "+",
                // aunque la TopBar ya no se controla aquí.
                var fabDestination: String? = null
                when (currentRoute) {
                    Routes.SPECIE_LIST -> fabDestination = Routes.SPECIE_ADD
                    else -> fabDestination = null
                }

                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        DrawerContent(
                            items = DrawerItems,
                            currentRoute = currentRoute,
                            onNavigate = { route ->
                                scope.launch { drawerState.close() }
                                if (currentRoute != route) {
                                    navController.navigate(route) {
                                        popUpTo(navController.graph.startDestinationId) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            }
                        )
                    }
                ) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
                        topBar = {
                            // 3. LA TOP BAR AHORA CONSUME EL ESTADO MUTABLE
                            BaseTopAppBar(state = topAppBarState)
                        },
                        floatingActionButton = {
                            // Mostramos el FAB solo si hay un destino válido calculado
                            if (fabDestination != null) {
                                FloatingActionButton(
                                    onClick = { navController.navigate(fabDestination) },
                                    containerColor = Color(0xFFFFE81F),
                                    contentColor = Color.Black
                                ) {
                                    Icon(Icons.Default.Add, contentDescription = "Añadir")
                                }
                            }
                        }
                    ) { innerPadding ->
                        NavHostScreen(
                            navController = navController,
                            modifier = Modifier.padding(innerPadding),
                            onShowSnackBar = { mensaje ->
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = mensaje,
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            },
                            // 4. PASAMOS EL CALLBACK PARA CONFIGURAR LA BARRA y ABRIR EL DRAWER
                            onConfigureTopAppBar = { newState ->
                                topAppBarState = newState
                            },
                            onOpenDrawer = {
                                scope.launch { drawerState.open() }
                            }
                        )
                    }
                }
            }
        }
    }
}