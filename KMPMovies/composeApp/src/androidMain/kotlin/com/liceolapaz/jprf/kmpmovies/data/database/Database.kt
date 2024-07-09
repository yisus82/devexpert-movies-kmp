package com.liceolapaz.jprf.kmpmovies.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

fun getDatabaseBuilder(ctx: Context): RoomDatabase.Builder<MovieDatabase> {
    val appContext = ctx.applicationContext
    val dbFile = appContext.getDatabasePath(DATABASE_NAME)
    return Room.databaseBuilder<MovieDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}