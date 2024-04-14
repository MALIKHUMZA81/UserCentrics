package com.usercentrics.interviewtest.data.usecases

import com.usercentrics.interviewtest.data.repository.centrics.CentricsRepository
import javax.inject.Inject

/**
 * A use-case to handle user-centrics cost.
 */

class CalculateCentricsCostUseCase
    @Inject
    constructor(
        private val repository: CentricsRepository,
    ) {
        suspend operator fun invoke() = repository.calculateCentricsCost()
    }
