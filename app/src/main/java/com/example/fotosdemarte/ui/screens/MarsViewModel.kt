package com.example.fotosdemarte.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fotosdemarte.model.MarsPhoto
import com.example.fotosdemarte.network.MarsApi
import kotlinx.coroutines.launch

sealed interface MarsUiState {
    data class Success(val photos: List<MarsPhoto>) : MarsUiState
    object Error : MarsUiState
    object Loading : MarsUiState
}

class MarsViewModel : ViewModel() {
    var marsUiState: MarsUiState by mutableStateOf(MarsUiState.Loading)
        private set

    init {
        getMarsPhotos()
    }

    fun getMarsPhotos() {
        viewModelScope.launch {
            marsUiState = MarsUiState.Loading
            marsUiState = try {
                val response = MarsApi.retrofitService.getPhotos()
                MarsUiState.Success(response.photos)
            } catch (e: Exception) {
                MarsUiState.Error
            }

        }
    }
}