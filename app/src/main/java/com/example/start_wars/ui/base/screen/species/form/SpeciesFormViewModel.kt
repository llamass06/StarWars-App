package com.example.start_wars.ui.base.screen.species.form

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.start_wars.data.model.Specie
import com.example.start_wars.data.repository.SpeciesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SpeciesFormViewModel @Inject constructor(
    private val repository: SpeciesRepository
) : ViewModel() {

    var state: SpecieFormState by mutableStateOf(SpecieFormState())
        private set

    var currentId: Int = 0
        private set

    var showDuplicateError by mutableStateOf(false)
    var isSavedSuccessfully by mutableStateOf(false)

    fun validateName():Boolean{
        return state.name.isNotBlank()
    }
    fun iniData(specie: Specie){
        currentId = specie.id
        state = state.copy(
            name = specie.name,
            classification = specie.classification,
            designation = specie.designation,
            average_height = specie.average_height,
            average_lifespan = specie.average_lifespan,
            eye_colors = specie.eye_colors,
            hair_colors = specie.hair_colors,
            skin_colors = specie.skin_colors,
            language = specie.language,
            homeworld = specie.homeworld,
            //people = specie.people.split(",").map { it.trim() }.filter { it.isNotEmpty() },
            //films = filmsList,
            discovery_date = specie.discovery_date,
            is_artificial = specie.is_artificial,
            url = specie.url,
            created = specie.created,
            edited = specie.edited
        )
    }

    fun onNameChange(value: String) {
        state = state.copy(name = value, isNameError = false)
    }
    fun onClassificationChange(value: String) { state = state.copy(classification = value) }
    fun onDesignationChange(value: String) { state = state.copy(designation = value) }
    fun onAverageHeightChange(value: String) { state = state.copy(average_height = value) }
    fun onAverageLifespanChange(value: String) { state = state.copy(average_lifespan = value) }
    fun onEyeColorsChange(value: String) { state = state.copy(eye_colors = value) }
    fun onHairColorsChange(value: String) { state = state.copy(hair_colors = value) }
    fun onSkinColorsChange(value: String) { state = state.copy(skin_colors = value) }
    fun onLanguageChange(value: String) { state = state.copy(language = value) }
    fun onHomeworldChange(value: String) { state = state.copy(homeworld = value) }
    fun onPeopleChange(value: String) { state = state.copy(people = value) }
    fun onFilmsChange(value: String) { state = state.copy(films = value) }
    fun onDiscoveryDateChange(value: String) { state = state.copy(discovery_date = value) }
    fun onIsArtificialChange(value: Boolean) { state = state.copy(is_artificial = value) }
    fun onUrlChange(value: String) { state = state.copy(url = value) }
    fun onCreatedChange(value: String) { state = state.copy(created = value) }
    fun onEditedChange(value: String) { state = state.copy(edited = value) }

    fun dismissErrorDialog() {
        showDuplicateError = false
    }

    fun saveSpecie() {
        val peopleList = state.people.split(",").map { it.trim() }.filter { it.isNotEmpty() }
        val filmsList = state.films.split(",").map { it.trim() }.filter { it.isNotEmpty() }

        if(state.name.isBlank()){
            state=state.copy(isNameError = true)
            return
        }

        val specieToSave = Specie(
            id = currentId,
            name = state.name,
            classification = state.classification,
            designation = state.designation,
            average_height = state.average_height,
            average_lifespan = state.average_lifespan,
            eye_colors = state.eye_colors,
            hair_colors = state.hair_colors,
            skin_colors = state.skin_colors,
            language = state.language,
            homeworld = state.homeworld,
            people = peopleList,
            films = filmsList,
            discovery_date = state.discovery_date,
            is_artificial = state.is_artificial,
            url = state.url,
            created = state.created,
            edited = state.edited
        )

        viewModelScope.launch(Dispatchers.IO) {
            if(currentId == 0){
                val added = repository.addSpecies(specieToSave)
                withContext(Dispatchers.Main) {
                    if (added) {
                        isSavedSuccessfully = true
                    } else {
                        showDuplicateError = true
                    }
                }
            } else{
                repository.updateSpecies(specieToSave)
                withContext(Dispatchers.Main) {
                    isSavedSuccessfully = true
                }
            }
        }
    }
}
