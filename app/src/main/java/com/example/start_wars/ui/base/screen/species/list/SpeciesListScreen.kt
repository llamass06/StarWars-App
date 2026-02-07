package com.example.start_wars.ui.base.screen.species.list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.start_wars.data.model.Specie
import com.example.start_wars.ui.base.common.CardStyle
import com.example.start_wars.ui.base.common.LocalCardStyle
import com.example.start_wars.ui.base.components.btab.Action
import com.example.start_wars.ui.base.components.btab.BaseTopAppBarState
import androidx.compose.material.icons.filled.MoreVert


@Composable
fun SpeciesListScreen(
    modifier: Modifier = Modifier,
    viewModel: SpeciesListViewModel,
    onShowSnackBar: (String) -> Unit,
    onConfigureTopAppBar: (BaseTopAppBarState) -> Unit,
    onOpenDrawer: () -> Unit,
    onNavigateToEdit: (Specie) -> Unit,
    onNavigateToAbout: () -> Unit
) {
    val state = viewModel.state
    val currentOrder = viewModel.currentSortOrder
    val sortIcon = when (currentOrder) {
        SortOrder.ASC -> Icons.Default.ArrowUpward // Necesitarás importar Icons.Default.ArrowUpward
        SortOrder.DESC -> Icons.Default.ArrowDownward
        SortOrder.NONE -> Icons.Default.Sort
    }

    LaunchedEffect(currentOrder) {
        onConfigureTopAppBar(
            BaseTopAppBarState(
                title = "Especies",
                iconUpAction = Icons.Default.Menu,
                upAction = onOpenDrawer,
                actions = listOf(
                    Action.ActionImageVector(
                        name = "Ordenar",
                        icon = sortIcon,
                        contentDescription = "Ordenar lista",
                        onClick = {
                            val nextOrder = when (currentOrder) {
                                SortOrder.NONE -> SortOrder.ASC
                                SortOrder.ASC -> SortOrder.DESC
                                SortOrder.DESC -> SortOrder.NONE
                            }
                            viewModel.changeSortOrder(nextOrder)
                        }
                    ),
                    Action.ActionImageVector(
                        name = "About Us",
                        icon = Icons.Default.MoreVert,
                        contentDescription = "Ir a About Us",
                        onClick = { onNavigateToAbout() }
                    )
                )
            )
        )
    }
    CompositionLocalProvider(LocalCardStyle provides CardStyle) {

        Box(modifier = modifier.fillMaxSize()) {
            when (state) {
                is SpecieListState.Loading -> {
                    // Estado de Carga
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                is SpecieListState.NoData -> {
                    // Estado Sin Datos
                    Text(
                        text = "No hay especies disponibles.",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                is SpecieListState.Success -> {
                    SpeciesListContent(
                        speciesList = state.dataset,
                        onDeleteSpecie = { specie ->
                            viewModel.deleteSpecie(specie)
                            onShowSnackBar("Especie ${specie.name} eliminada correctamente")
                        },
                        onSpecieClick = { specie ->
                            onNavigateToEdit(specie)
                        },
                        onOrderByName = {
                            val nextOrder = when (currentOrder) {
                                SortOrder.ASC -> SortOrder.ASC
                                SortOrder.DESC -> SortOrder.DESC
                                SortOrder.NONE -> SortOrder.NONE
                            }
                            viewModel.changeSortOrder(nextOrder)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun SpeciesListContent(
    speciesList: List<Specie>,
    onDeleteSpecie: (Specie) -> Unit,
    onSpecieClick: (Specie) -> Unit,
    onOrderByName: () -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(speciesList) { specie ->
            SpecieItem(
                specie = specie,
                onDeleteClick = { onDeleteSpecie(specie) },
                onClick = { onSpecieClick(specie) }
            )
        }
    }
}

@Composable
fun SpecieItem(
    modifier: Modifier = Modifier,
    specie: Specie,
    onDeleteClick: () -> Unit,
    onClick: () -> Unit
) {
    val style = LocalCardStyle.current
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Eliminar Especie") },
            text = { Text("¿Estás seguro de que deseas eliminar a ${specie.name}?") },
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false
                    onDeleteClick()
                }) {
                    Text("Sí, borrar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = style.backgroundColor,
            contentColor = style.contentColor
        ),
        border = BorderStroke(style.borderWidth, style.borderColor),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val icon = if (specie.is_artificial) Icons.Default.Settings else Icons.Default.Face

            Icon(
                imageVector = icon,
                contentDescription = "Icono especie",
                modifier = Modifier.size(40.dp),
                tint = style.borderColor
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = specie.name, style = MaterialTheme.typography.titleMedium)
                Text(
                    text = "Planeta: ${specie.homeworld}",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            // Botón de eliminar
            IconButton(onClick = { showDialog = true }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar",
                    tint = Color.Red
                )
            }
        }
    }
}