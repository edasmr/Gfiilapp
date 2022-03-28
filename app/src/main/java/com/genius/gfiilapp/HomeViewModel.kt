package com.genius.gfiilapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel() : ViewModel() {

    private val _homeState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState.Idle)
    val homeState: StateFlow<HomeUiState> = _homeState


    sealed class HomeUiState {
      // data class Success(val homeEntity: HomeEntity) : HomeUiState()

        data class Error(val error: String?) : HomeUiState()
       // data class PageSuccess(val recipeList: List<Receipe>) : HomeUiState()
        object Idle : HomeUiState()
        object Loading : HomeUiState()
    }
}