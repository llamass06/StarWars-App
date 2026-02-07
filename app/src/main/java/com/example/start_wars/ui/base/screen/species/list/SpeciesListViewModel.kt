package com.example.start_wars.ui.base.screen.species.list

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
import javax.inject.Inject

enum class SortOrder{
    NONE,
    ASC,
    DESC
}

@HiltViewModel
class SpeciesListViewModel @Inject constructor(
    private val repository: SpeciesRepository
) : ViewModel() {

    var state: SpecieListState by mutableStateOf(SpecieListState.Loading)

    var currentSortOrder by mutableStateOf(SortOrder.NONE)

    init {
       getData()
    }

    fun getData(){
        state = SpecieListState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllSpecies(currentSortOrder).collect { species ->
                state = if (species.isEmpty())
                    SpecieListState.NoData
                else
                    SpecieListState.Success(species)
            }
        }
    }

    fun changeSortOrder(newOrder: SortOrder){
        currentSortOrder = newOrder
        getData()
    }

    fun deleteSpecie(specie: Specie) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteSpecies(specie)
        }
    }


}