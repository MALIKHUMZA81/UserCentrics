package com.usercentrics.interviewtest.data.usecases

import com.usercentrics.interviewtest.data.repository.centrics.CentricsRepository
import com.usercentrics.sdk.UsercentricsBanner
import javax.inject.Inject

/**
 * A use-case to load the status from Repository.
 */

class FetchCentricsStatusUseCase @Inject constructor(
    private val repository: CentricsRepository,
) {
    suspend operator fun invoke() = repository.getCentricsStatus()
}
