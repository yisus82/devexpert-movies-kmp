package com.liceolapaz.jprf.kmpmovies.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liceolapaz.jprf.kmpmovies.data.Movie
import com.liceolapaz.jprf.kmpmovies.data.MovieService
import com.liceolapaz.jprf.kmpmovies.data.RemoteMovie
import kotlinx.coroutines.launch

class HomeViewModel(
    private val movieService: MovieService
): ViewModel() {
    var state by mutableStateOf(UIState())
        private set

    init {
        viewModelScope.launch {
            state = UIState(isLoading = true)
            state = UIState(
                isLoading = false,
                movies = movieService.getPopularMovies().results.map { it.toDomainMovie() }
            )
        }
    }

    data class UIState(
        val isLoading: Boolean = false,
        val movies: List<Movie> = emptyList()
    )
}

private fun RemoteMovie.toDomainMovie(): Movie {
    return Movie(
        id = id,
        title = title,
        poster = "https://image.tmdb.org/t/p/w500$posterPath"
    )
}