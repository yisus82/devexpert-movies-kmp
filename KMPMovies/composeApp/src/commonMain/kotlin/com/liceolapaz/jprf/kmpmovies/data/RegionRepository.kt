package com.liceolapaz.jprf.kmpmovies.data

interface RegionDataSource {
    suspend fun getRegion(): String
}

const val DEFAULT_REGION = "US"

class RegionRepository(
    private val regionDataSource: RegionDataSource
) {
    suspend fun getRegion(): String {
        return regionDataSource.getRegion()
    }
}