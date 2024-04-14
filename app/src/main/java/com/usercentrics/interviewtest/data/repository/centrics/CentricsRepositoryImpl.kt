package com.usercentrics.interviewtest.data.repository.centrics

import androidx.annotation.WorkerThread
import com.usercentrics.interviewtest.data.DataState
import com.usercentrics.interviewtest.data.mappers.ServiceToTotalCostMapper
import com.usercentrics.interviewtest.utils.nothingFound
import com.usercentrics.sdk.Usercentrics
import com.usercentrics.sdk.UsercentricsBanner
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * This is an implementation of [CentricsRepository]
 * We can easily handle all the response/error case here
 */

class CentricsRepositoryImpl
    @Inject
    constructor(
        private val ioDispatcher: CoroutineContext,
    ) : CentricsRepository {
        @WorkerThread
        override suspend fun getCentricsStatus(): Flow<DataState<Boolean>> {
            return callbackFlow {
                Usercentrics.isReady({ status ->
                    trySend(DataState.success(status.shouldCollectConsent))
                }, { error ->
                    trySend(DataState.error<Boolean>(error.localizedMessage))
                })

                awaitClose()
            }
        }

        override suspend fun handleUserCentricsBanner(banner: UsercentricsBanner): Flow<DataState<Unit>> {
            return callbackFlow {
                banner.showSecondLayer { userResponse ->
                    userResponse?.let { trySend(DataState.success(Unit)) } ?: run {
                        trySend(DataState.error<Unit>(nothingFound()))
                    }
                }
                awaitClose()
            }
        }

        override suspend fun calculateCentricsCost(): Flow<DataState<Double>> {
            return callbackFlow {
                val services = Usercentrics.instance.getCMPData().services
                ServiceToTotalCostMapper.serviceToCost(services) {
                    trySend(DataState.success(it))
                }
                awaitClose()
            }
        }
    }
