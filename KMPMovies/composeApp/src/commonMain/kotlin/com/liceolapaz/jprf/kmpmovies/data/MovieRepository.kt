package com.liceolapaz.jprf.kmpmovies.data

class MovieRepository(private val movieService: MovieService) {
    suspend fun getPopularMovies(): List<Movie> {
        return movieService.getPopularMovies().results.map { it.toDomainMovie() }
    }

    suspend fun getMovieById(movieId: Int): Movie {
        return movieService.getMovieById(movieId).toDomainMovie()
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