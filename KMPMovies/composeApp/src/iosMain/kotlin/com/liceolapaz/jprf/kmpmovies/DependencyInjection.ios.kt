package com.liceolapaz.jprf.kmpmovies

import com.liceolapaz.jprf.kmpmovies.data.database.getDatabaseBuilder
import org.koin.dsl.module

actual val platformModule = module {
    single { getDatabaseBuilder() }
}