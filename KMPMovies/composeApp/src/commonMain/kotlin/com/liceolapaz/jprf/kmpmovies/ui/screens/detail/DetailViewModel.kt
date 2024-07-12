package com.liceolapaz.jprf.kmpmovies.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liceolapaz.jprf.kmpmovies.data.Movie
import com.liceolapaz.jprf.kmpmovies.data.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val movieId: Int,
    private val movieRepository: MovieRepository
) : ViewModel() {
    private val _state = MutableStateFlow(UIState())
    var state: StateFlow<UIState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.value = UIState(isLoading = true)
            movieRepository.getMovieById(movieId).collect { movie ->
                if (movie != null) {
                    _state.value = UIState(
                        isLoading = false,
                        movie = movie
                    )
                }
            }
        }
    }

    data class UIState(
        val isLoading: Boolean = false,
        val movie: Movie? = null
    )

    fun onFavoriteClick() {
        state.value.movie?.let { movie ->
            viewModelScope.launch {
                movieRepository.toggleFavorite(movie)
            }
        }
    }
}