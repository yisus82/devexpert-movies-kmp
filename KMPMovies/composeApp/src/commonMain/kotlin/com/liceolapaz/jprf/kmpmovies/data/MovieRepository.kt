package com.liceolapaz.jprf.kmpmovies.data

import com.liceolapaz.jprf.kmpmovies.data.database.MovieDAO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

class MovieRepository(
    private val movieService: MovieService,
    private val movieDAO: MovieDAO
) {
    val movies : Flow<List<Movie>> = movieDAO.getPopularMovies().onEach { movies ->
        if (movies.isEmpty()) {
            val popularMovies = movieService.getPopularMovies().results.map { it.toDomainMovie() }
            popularMovies.forEach { movie -> movieDAO.insertMovie(movie) }
        }
    }

    suspend fun getMovieById(movieId: Int): Flow<Movie?> = movieDAO.getMovieById(movieId).onEach  { movie ->
        if (movie == null) {
            val domainMovie = movieService.getMovieById(movieId).toDomainMovie()
            movieDAO.insertMovie(domainMovie)
        }
    }
}

private fun RemoteMovie.toDomainMovie(): Movie {
    return Movie(
        id = id,
        title = title,
        overview = overview,
        releaseDate = releaseDate,
        poster = "https://image.tmdb.org/t/p/w185$posterPath",
        backdrop = backdropPath?.let { "https://image.tmdb.org/t/p/w780$it" },
        originalTitle = originalTitle,
        originalLanguage = originalLanguage,
        popularity = popularity,
        voteAverage = voteAverage
    )
}