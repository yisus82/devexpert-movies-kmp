package com.liceolapaz.jprf.kmpmovies

import androidx.compose.ui.window.ComposeUIViewController
import com.liceolapaz.jprf.kmpmovies.data.database.getDatabaseBuilder
import com.liceolapaz.jprf.kmpmovies.data.database.getRoomDatabase

fun MainViewController() = ComposeUIViewController {
    val db = getRoomDatabase(getDatabaseBuilder())
    App(db.movieDao())
}