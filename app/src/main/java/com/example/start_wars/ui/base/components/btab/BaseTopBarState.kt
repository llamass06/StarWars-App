package com.example.start_wars.ui.base.components.btab

import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Clase que representa el estado de la Top App Bar.
 * @property title         el título de la App Bar.
 * @property iconUpAction  el ícono utilizado para la navigationButton.
 * @property upAction      el evento de la navigationButton.
 * @property actions       la lista de acciones disponibles en la App Bar.
 */
data class BaseTopAppBarState(
    val title:String,
    val iconUpAction: ImageVector,
    val upAction:()->Unit = {},
    val actions:List<Action> =listOf()
)