package com.liceolapaz.jprf.kmpmovies.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.liceolapaz.jprf.kmpmovies.data.Movie

const val DATABASE_NAME = "movies.db"

interface DB {
    fun clearAllTables()
}

@Database(entities = [Movie::class], version = 1)
abstract class MovieDatabase : RoomDatabase(), DB {
    abstract fun movieDao(): MovieDAO

    override fun clearAllTables() {}
}

fun getRoomDatabase(
    builder: RoomDatabase.Builder<MovieDatabase>
): MovieDatabase {
    return builder
        .setDriver(BundledSQLiteDriver())
        .build()
}