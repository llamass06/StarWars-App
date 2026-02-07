package com.example.start_wars.ui.base.components.btab

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import kotlin.collections.filter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseTopAppBar(state: BaseTopAppBarState) {
    val visible = state.actions.filter { it.isVisible }
    val notVisible = state.actions.filter { !it.isVisible }
    val starWarsYellow = Color(0xFFFFE81F)
    TopAppBar(
        title = {
            Text(
                text = state.title, modifier = Modifier.fillMaxWidth(),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = starWarsYellow,
            )
        },
        navigationIcon = {
            IconButton(onClick = state.upAction) {
                Icon(
                    imageVector = state.iconUpAction,
                    contentDescription = "",
                )
            }
        },
        actions = {
            if (visible.isNotEmpty()) visible.forEach {
                IconButton(onClick = it.onClick) {
                    when (it) {
                        is Action.ActionImageVector -> {
                            Icon(
                                imageVector = it.icon!!,
                                contentDescription = it.contentDescription,
                            )
                        }

                        is Action.ActionPainter -> {
                            Icon(
                                painter = it.icon!!,
                                contentDescription = it.contentDescription,
                            )
                        }
                    }
                }
            }
            if (notVisible.isNotEmpty()) TopAppBarDropDownMenu(state.actions.filter { !it.isVisible })
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Black, // Fondo Negro
            titleContentColor = starWarsYellow, // Título Amarillo
            navigationIconContentColor = starWarsYellow, // Icono de Hamburguesa Amarillo
            actionIconContentColor = starWarsYellow // Iconos de acción (Añadir/Listar) Amarillos
        )
    )
}
