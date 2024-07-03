package com.liceolapaz.jprf.kmpmovies.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class MovieService(
    private val apiKey: String,
    private val client: HttpClient
) {
    suspend fun getPopularMovies(): RemoteResult {
        return client
            .get("https://api.themoviedb.org/3/discover/movie?api_key=$apiKey&sort_by=popularity.desc")
            .body<RemoteResult>()
    }
}