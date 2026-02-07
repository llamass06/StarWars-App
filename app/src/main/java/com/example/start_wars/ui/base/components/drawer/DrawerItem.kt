package com.example.start_wars.ui.base.components.drawer

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class DrawerItem(
    @StringRes val name: Int,
    @DrawableRes val icon: Int,
    val ruta: String
)

