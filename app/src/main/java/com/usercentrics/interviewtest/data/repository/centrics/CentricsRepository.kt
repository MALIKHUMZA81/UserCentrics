package com.usercentrics.interviewtest.data.repository.centrics

import com.usercentrics.interviewtest.data.DataState
import com.usercentrics.sdk.UsercentricsBanner
import kotlinx.coroutines.flow.Flow

/**
 * CentricsRepository is an interface data layer to handle communication with any data source such as SDK, Server or local database.
 * @see [CentricsRepositoryImpl] for implementation of this interface.
 */
interface CentricsRepository {
    suspend fun getCentricsStatus(): Flow<DataState<Boolean>>

    suspend fun handleUserCentricsBanner(banner: UsercentricsBanner): Flow<DataState<Unit>>

    suspend fun calculateCentricsCost(): Flow<DataState<Double>>
}
