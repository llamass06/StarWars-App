package com.example.start_wars.ui.base.screen.species.list

import com.example.start_wars.data.model.Specie

sealed class SpecieListState() {
    data object Loading : SpecieListState()
    data object NoData : SpecieListState()
    data class Success(val dataset: List<Specie>): SpecieListState()
}