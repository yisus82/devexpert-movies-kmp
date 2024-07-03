package com.liceolapaz.jprf.kmpmovies.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liceolapaz.jprf.kmpmovies.Movie
import com.liceolapaz.jprf.kmpmovies.movies
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {
    var state by mutableStateOf(UIState())
        private set

    init {
        viewModelScope.launch {
            state = UIState(isLoading = true)
            delay(1000)
            state = UIState(isLoading = false, movies = movies)
        }
    }

    data class UIState(
        val isLoading: Boolean = false,
        val movies: List<Movie> = emptyList()
    )
}