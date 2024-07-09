package com.liceolapaz.jprf.kmpmovies.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.liceolapaz.jprf.kmpmovies.data.MovieRepository
import com.liceolapaz.jprf.kmpmovies.data.MovieService
import com.liceolapaz.jprf.kmpmovies.data.database.MovieDAO
import com.liceolapaz.jprf.kmpmovies.ui.screens.detail.DetailScreen
import com.liceolapaz.jprf.kmpmovies.ui.screens.detail.DetailViewModel
import com.liceolapaz.jprf.kmpmovies.ui.screens.home.HomeScreen
import com.liceolapaz.jprf.kmpmovies.ui.screens.home.HomeViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kmpmovies.composeapp.generated.resources.Res
import kmpmovies.composeapp.generated.resources.tmdb_api_key
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.stringResource

@Composable
fun Navigation(movieDAO: MovieDAO) {
    val navController = rememberNavController()
    val movieRepository = rememberMovieRepository(movieDAO)

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                onMovieClick = { movie ->
                    navController.navigate("detail/${movie.id}")
                },
                vm = HomeViewModel(movieRepository)
            )
        }
        composable(
            route = "detail/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) { backStackEntry ->
            val movieId = checkNotNull(backStackEntry.arguments?.getInt("movieId"))
            DetailScreen(
                vm = viewModel { DetailViewModel(movieId, movieRepository) },
                onBack = { navController.popBackStack() }
            )
        }
    }
}

@Composable
private fun rememberMovieRepository(
    movieDAO: MovieDAO,
    apiKey: String = stringResource(Res.string.tmdb_api_key)
): MovieRepository = remember {
    val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
        install(DefaultRequest) {
            url {
                protocol = URLProtocol.HTTPS
                host = "api.themoviedb.org"
                parameters.append("api_key", apiKey)
            }
        }
    }
    MovieRepository(MovieService(client), movieDAO)
}