package com.liceolapaz.jprf.kmpmovies.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class MovieService(
    private val client: HttpClient
) {
    suspend fun getPopularMovies(region: String): RemoteResult {
        return client
            .get("/3/discover/movie") {
                parameter("sort_by", "popularity.desc")
                parameter("region", region)
            }
            .body<RemoteResult>()
    }

    suspend fun getMovieById(movieId: Int): RemoteMovie {
        return client
            .get("/3/movie/$movieId")
            .body<RemoteMovie>()
    }
}