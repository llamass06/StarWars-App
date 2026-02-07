package com.example.start_wars.ui.base.components.drawer

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun DrawerContent(
    items: List<DrawerItem>,
    currentRoute: String?, // Necesario para saber cual marcar en negrita/color
    onNavigate: (String) -> Unit // Callback que devuelve la ruta a la que ir
) {
    ModalDrawerSheet {
        Text(
            text = "Star Wars",
            modifier = Modifier.padding(28.dp)
        )

        items.forEach { item ->
            NavigationDrawerItem(
                label = { Text(text = stringResource(id = item.name)) },
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = stringResource(id = item.name),
                        modifier = Modifier.size(24.dp)
                    )
                },
                // Compara si la ruta del item es igual a la ruta actual para marcarlo
                selected = currentRoute == item.ruta,
                onClick = {
                    // Devuelve la ruta seleccionada al padre (MainActivity)
                    onNavigate(item.ruta)
                },
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .width(170.dp) // Mantenemos tu ancho personalizado
            )
        }
    }
}