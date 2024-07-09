package com.liceolapaz.jprf.kmpmovies.data.database

import androidx.room.Room
import androidx.room.RoomDatabase
import platform.Foundation.NSHomeDirectory

fun getDatabaseBuilder(): RoomDatabase.Builder<MovieDatabase> {
    val dbFilePath = NSHomeDirectory() + "/$DATABASE_NAME"

    return Room.databaseBuilder<MovieDatabase>(
        name = dbFilePath,
        factory = { MovieDatabase::class.instantiateImpl() }
    )
}