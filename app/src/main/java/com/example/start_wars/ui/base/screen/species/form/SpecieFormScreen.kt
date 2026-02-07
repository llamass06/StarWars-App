package com.example.start_wars.ui.base.screen.species.form

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.start_wars.R
import com.example.start_wars.data.model.Specie
import com.example.start_wars.ui.base.components.ad.AlertDialogOK
import com.example.start_wars.ui.base.components.btab.BaseTopAppBarState
import com.example.start_wars.ui.notificationHandler
import com.example.start_wars.ui.permissions.AppPermissions
import com.example.start_wars.ui.rememberPermissionsLauncher

data class SpecieFormEvents(
    val onNameChange: (String) -> Unit,
    val onClassificationChange: (String) -> Unit,
    val onDesignationChange: (String) -> Unit,
    val onAverageHeightChange: (String) -> Unit,
    val onAverageLifespanChange: (String) -> Unit,
    val onEyeColorsChange: (String) -> Unit,
    val onHairColorsChange: (String) -> Unit,
    val onSkinColorsChange: (String) -> Unit,
    val onLanguageChange: (String) -> Unit,
    val onHomeworldChange: (String) -> Unit,
    val onPeopleChange: (String) -> Unit,
    val onFilmsChange: (String) -> Unit,
    val onDiscoveryDateChange: (String) -> Unit,
    val onIsArtificialChange: (Boolean) -> Unit,
    val onUrlChange: (String) -> Unit,
    val onCreatedChange: (String) -> Unit,
    val onEditedChange: (String) -> Unit,
    val onSave: () -> Unit
)

@Composable
fun AddSpecieScreen(
    modifier: Modifier = Modifier,
    viewModel: SpeciesFormViewModel,
    onNavigateBack: () -> Unit,
    onConfigureTopAppBar: (BaseTopAppBarState) -> Unit,
    specieToEdit: Specie?,
) {
    val isEditing = specieToEdit != null
    val title = if(isEditing) "Editar Specie" else "Añadir Especie"
    val state = viewModel.state
    val context = LocalContext.current

    val myNotificationHandler = remember { notificationHandler(context) }

    val requestNotificationPermissionThenNotify = rememberPermissionsLauncher(
        permissions = listOf(AppPermissions.Notifications),
        onAllGranted = {
            myNotificationHandler.showSimpleNotification(
                contentTitle = "Especie creada",
                contentText = "Se ha dado de alta ${state.name}"
            )
            onNavigateBack()
        },
        onDenied = {
            onNavigateBack()
        }
    )

    LaunchedEffect(Unit) {
        onConfigureTopAppBar(
            BaseTopAppBarState(
                title = title,
                iconUpAction = Icons.Default.ArrowBack,
                upAction = onNavigateBack
            )
        )
        if(specieToEdit != null){
            viewModel.iniData(specieToEdit)
        }
    }

    LaunchedEffect(viewModel.isSavedSuccessfully) {
        if (viewModel.isSavedSuccessfully) {
            requestNotificationPermissionThenNotify()
            viewModel.isSavedSuccessfully = false
        }
    }

    if (viewModel.showDuplicateError) {
        AlertDialogOK(
            text = "No se puede crear la especie '${state.name}' porque ya existe.",
            onDismiss = { viewModel.dismissErrorDialog() }
        )
    }

    val actions = SpecieFormEvents(
        onNameChange = { viewModel.onNameChange(it) },
        onClassificationChange = { viewModel.onClassificationChange(it) },
        onDesignationChange = { viewModel.onDesignationChange(it) },
        onAverageHeightChange = { viewModel.onAverageHeightChange(it) },
        onAverageLifespanChange = { viewModel.onAverageLifespanChange(it) },
        onEyeColorsChange = { viewModel.onEyeColorsChange(it) },
        onHairColorsChange = { viewModel.onHairColorsChange(it) },
        onSkinColorsChange = { viewModel.onSkinColorsChange(it) },
        onLanguageChange = { viewModel.onLanguageChange(it) },
        onHomeworldChange = { viewModel.onHomeworldChange(it) },
        onPeopleChange = { viewModel.onPeopleChange(it) },
        onFilmsChange = { viewModel.onFilmsChange(it) },
        onDiscoveryDateChange = { viewModel.onDiscoveryDateChange(it) },
        onIsArtificialChange = { viewModel.onIsArtificialChange(it) },
        onUrlChange = { viewModel.onUrlChange(it) },
        onCreatedChange = { viewModel.onCreatedChange(it) },
        onEditedChange = { viewModel.onEditedChange(it) },
        onSave = {
            if(viewModel.validateName()){
                viewModel.saveSpecie()
            }else{
                Toast.makeText(context, "El nombre es OBLIGATORIO", Toast.LENGTH_SHORT).show()
            }
        }
    )

    CreacionEspecieContent(
        modifier = modifier,
        state = state,
        actions = actions
    )
}

@Composable
fun HeaderBoxSign() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.star_wars),
            contentDescription = stringResource(R.string.star_war_desc),
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .background(Color(0xAA000000))
                .matchParentSize()
        )
    }
}

@Composable
fun CreacionEspecieContent(
    modifier: Modifier = Modifier,
    state: SpecieFormState,
    actions: SpecieFormEvents
) {
    Column(modifier = modifier.fillMaxSize()) {
        HeaderBoxSign()
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxSize()
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = stringResource(R.string.creacion_especie),
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )

            Text(
                text = stringResource(R.string.subtexto_creacion_especie),
                style = MaterialTheme.typography.titleMedium
            )

            // Nombre
            SpeciesTextField(
                label = stringResource(R.string.nombre_especie),
                value = state.name,
                onValueChange = actions.onNameChange,
                isError = state.isNameError,
                errorMessage = "El nombre no puede estar vacio."
            )

            // Clasificacion
            SpeciesTextField(
                label = stringResource(R.string.classification),
                value = state.classification,
                onValueChange = actions.onClassificationChange
            )

            // Designacion
            SpeciesTextField(
                label = stringResource(R.string.designation),
                value = state.designation,
                onValueChange = actions.onDesignationChange
            )

            // Altura Media
            SpeciesTextField(
                label = stringResource(R.string.average_height),
                value = state.average_height,
                onValueChange = actions.onAverageHeightChange
            )

            // Esperanza de vida
            SpeciesTextField(
                label = stringResource(R.string.average_lifespan),
                value = state.average_lifespan,
                onValueChange = actions.onAverageLifespanChange
            )

            // Colores de ojos
            SpeciesTextField(
                label = stringResource(R.string.eye_colors),
                value = state.eye_colors,
                onValueChange = actions.onEyeColorsChange
            )

            // Colores de pelo
            SpeciesTextField(
                label = stringResource(R.string.hair_colors),
                value = state.hair_colors,
                onValueChange = actions.onHairColorsChange
            )

            // Colores de piel
            SpeciesTextField(
                label = stringResource(R.string.skin_color),
                value = state.skin_colors,
                onValueChange = actions.onSkinColorsChange
            )

            // Idioma
            SpeciesTextField(
                label = stringResource(R.string.language),
                value = state.language,
                onValueChange = actions.onLanguageChange
            )

            // Planeta
            SpeciesTextField(
                label = stringResource(R.string.homeworld),
                value = state.homeworld,
                onValueChange = actions.onHomeworldChange
            )

            // Personajes (Input de String separado por comas)
            Text(text = "Personajes", style = MaterialTheme.typography.bodySmall)
            OutlinedTextField(
                value = state.people,
                onValueChange = actions.onPeopleChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("URLs separadas por coma") }
            )

            // Peliculas (Input de String separado por comas)
            Text(text = "Peliculas", style = MaterialTheme.typography.bodySmall)
            OutlinedTextField(
                value = state.films,
                onValueChange = actions.onFilmsChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("URLs separadas por coma") }
            )

            // Fecha Descubrimiento
            SpeciesTextField(
                label = stringResource(R.string.discovery_date),
                value = state.discovery_date,
                onValueChange = actions.onDiscoveryDateChange
            )

            // Checkbox Artificial
            Text(
                text = stringResource(R.string.is_artificial),
                style = MaterialTheme.typography.bodySmall
            )
            Checkbox(
                checked = state.is_artificial,
                onCheckedChange = actions.onIsArtificialChange
            )

            // URL
            SpeciesTextField(
                label = "URL recurso",
                value = state.url,
                onValueChange = actions.onUrlChange
            )

            // Creado
            SpeciesTextField(
                label = "Creado",
                value = state.created,
                onValueChange = actions.onCreatedChange
            )

            // Editado
            SpeciesTextField(
                label = stringResource(R.string.editado),
                value = state.edited,
                onValueChange = actions.onEditedChange
            )

            Button(
                onClick = actions.onSave,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0A84FF),
                    contentColor = Color(0xFFFFD700)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp)
            ) {
                Text(stringResource(R.string.crear_especie))
            }

            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}

@Composable
fun SpeciesTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean = false,
    errorMessage: String? = null
) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(label) },
            isError = isError,
            supportingText = {
                if(isError && errorMessage != null){
                    Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
                }
            }
        )
    }
}