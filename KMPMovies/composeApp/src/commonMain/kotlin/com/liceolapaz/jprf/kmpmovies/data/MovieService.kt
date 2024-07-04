package com.liceolapaz.jprf.kmpmovies.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class MovieService(
    private val client: HttpClient
) {
    suspend fun getPopularMovies(): RemoteResult {
        return client
            .get("/3/discover/movie?sort_by=popularity.desc")
            .body<RemoteResult>()
    }

    suspend fun getMovieById(movieId: Int): RemoteMovie {
        return client
            .get("/3/movie/$movieId")
            .body<RemoteMovie>()
    }
}