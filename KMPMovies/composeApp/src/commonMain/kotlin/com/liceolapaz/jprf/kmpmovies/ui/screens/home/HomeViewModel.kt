package com.liceolapaz.jprf.kmpmovies.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liceolapaz.jprf.kmpmovies.data.Movie
import com.liceolapaz.jprf.kmpmovies.data.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val movieRepository: MovieRepository
): ViewModel() {
    private val _state = MutableStateFlow(UIState())
    var state: StateFlow<UIState> = _state.asStateFlow()

    fun onUIReady() {
        viewModelScope.launch {
            _state.value = UIState(isLoading = true)
            movieRepository.movies.collect { movies ->
                if (movies.isNotEmpty()) {
                    _state.value = UIState(
                        isLoading = false,
                        movies = movies
                    )
                }
            }
        }
    }

    data class UIState(
        val isLoading: Boolean = false,
        val movies: List<Movie> = emptyList()
    )
}
