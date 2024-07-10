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
            movieRepository.getMovieById(movieId).collect { movie ->
                if (movie != null) {
                    state = UiState(
                        isLoading = false,
                        movie = movie
                    )
                }
            }
        }
    }

    data class UiState(
        val isLoading: Boolean = false,
        val movie: Movie? = null
    )

    fun onFavoriteClick() {
        state.movie?.let { movie ->
            viewModelScope.launch {
                movieRepository.toggleFavorite(movie)
            }
        }
    }
}