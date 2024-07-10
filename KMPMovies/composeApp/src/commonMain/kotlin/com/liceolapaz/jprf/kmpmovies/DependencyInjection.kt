package com.liceolapaz.jprf.kmpmovies

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.liceolapaz.jprf.kmpmovies.data.MovieRepository
import com.liceolapaz.jprf.kmpmovies.data.MovieService
import com.liceolapaz.jprf.kmpmovies.data.database.MovieDAO
import com.liceolapaz.jprf.kmpmovies.data.database.MovieDatabase
import com.liceolapaz.jprf.kmpmovies.ui.screens.detail.DetailViewModel
import com.liceolapaz.jprf.kmpmovies.ui.screens.home.HomeViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.qualifier.named
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

val appModule = module {
    single<String>(named("TMDB_API_KEY")) { BuildConfig.TMDB_API_KEY }
    single<MovieDAO> {
        val dbBuilder = get<RoomDatabase.Builder<MovieDatabase>>()
        dbBuilder.setDriver(BundledSQLiteDriver()).build().movieDao()
    }
}

val dataModule = module {
    factoryOf(::MovieRepository)
    factoryOf(::MovieService)
    single<HttpClient> {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
            install(DefaultRequest) {
                url {
                    protocol = URLProtocol.HTTPS
                    host = "api.themoviedb.org"
                    parameters.append("api_key", BuildConfig.TMDB_API_KEY)
                }
            }
        }
    }
}

val viewModelsModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::DetailViewModel)
}

expect val platformModule: Module

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(appModule, dataModule, viewModelsModule, platformModule)
    }
}