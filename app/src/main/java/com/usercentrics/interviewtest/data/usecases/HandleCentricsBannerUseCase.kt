package com.usercentrics.interviewtest.data.usecases

import com.usercentrics.interviewtest.data.repository.centrics.CentricsRepository
import com.usercentrics.sdk.UsercentricsBanner
import javax.inject.Inject

/**
 * A use-case to handle user-centrics banner.
 */

class HandleCentricsBannerUseCase
    @Inject
    constructor(
        private val repository: CentricsRepository,
    ) {
        suspend operator fun invoke(banner: UsercentricsBanner) = repository.handleUserCentricsBanner(banner)
    }
