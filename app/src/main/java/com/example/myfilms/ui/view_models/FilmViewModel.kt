package com.example.myfilms.ui.view_models

import androidx.lifecycle.ViewModel
import com.example.myfilms.data.MainFilmUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FilmViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(MainFilmUiState())
    val uiState: StateFlow<MainFilmUiState> = _uiState.asStateFlow()

    fun getNickname(): String {
        return _uiState.value.nickname
    }

    fun getAvaterId(): Int {
        return _uiState.value.avatarId
    }

    fun setNickname(newNickname: String) {
        _uiState.update { currentState ->
            currentState.copy(
                nickname = newNickname
            )
        }
    }

    fun setImage(newImageId: Int) {
        _uiState.update{ currentState ->
            currentState.copy(avatarId = newImageId)
        }
    }



}
