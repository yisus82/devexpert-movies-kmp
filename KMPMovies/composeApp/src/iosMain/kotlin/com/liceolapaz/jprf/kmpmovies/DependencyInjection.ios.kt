package com.liceolapaz.jprf.kmpmovies

import com.liceolapaz.jprf.kmpmovies.data.IOSRegionDataSource
import com.liceolapaz.jprf.kmpmovies.data.RegionDataSource
import com.liceolapaz.jprf.kmpmovies.data.database.getDatabaseBuilder
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val platformModule = module {
    single { getDatabaseBuilder() }
    factoryOf(::IOSRegionDataSource) bind RegionDataSource::class
}