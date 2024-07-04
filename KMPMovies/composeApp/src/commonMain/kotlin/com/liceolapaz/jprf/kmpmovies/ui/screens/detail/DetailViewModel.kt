package com.liceolapaz.jprf.kmpmovies.ui.screens.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liceolapaz.jprf.kmpmovies.data.Movie
import com.liceolapaz.jprf.kmpmovies.data.MovieRepository
import kotlinx.coroutines.launch

class DetailViewModel(
    private val movieId: Int,
    private val movieRepository: MovieRepository
) : ViewModel() {
    var state by mutableStateOf(UiState())
        private set

    init {
        viewModelScope.launch {
            state = UiState(isLoading = true)
            state = UiState(
                isLoading = false,
                movie = movieRepository.getMovieById(movieId)
            )
        }
    }

    data class UiState(
        val isLoading: Boolean = false,
        val movie: Movie? = null
    )
}