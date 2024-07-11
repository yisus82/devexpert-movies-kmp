package com.liceolapaz.jprf.kmpmovies

import android.location.Geocoder
import com.google.android.gms.location.LocationServices
import com.liceolapaz.jprf.kmpmovies.data.AndroidRegionDataSource
import com.liceolapaz.jprf.kmpmovies.data.RegionDataSource
import com.liceolapaz.jprf.kmpmovies.data.database.getDatabaseBuilder
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val platformModule = module {
    single { getDatabaseBuilder(get()) }
    factoryOf(::AndroidRegionDataSource) bind RegionDataSource::class
    factory { Geocoder(get()) }
    factory { LocationServices.getFusedLocationProviderClient(androidContext()) }

}