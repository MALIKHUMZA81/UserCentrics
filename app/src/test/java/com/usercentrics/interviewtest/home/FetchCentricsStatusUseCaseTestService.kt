package com.usercentrics.interviewtest.home

import com.usercentrics.interviewtest.data.DataState
import com.usercentrics.interviewtest.data.repository.centrics.CentricsRepository
import com.usercentrics.interviewtest.data.usecases.FetchCentricsStatusUseCase
import com.usercentrics.interviewtest.utils.TestCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class FetchCentricsStatusUseCaseTestService {
    @MockK
    private lateinit var repository: CentricsRepository

    private lateinit var sut: FetchCentricsStatusUseCase

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule = TestCoroutineRule()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        sut = FetchCentricsStatusUseCase(repository)
    }

    @Test
    fun `test getUserCentricsStatus success`() =
        runBlocking {
            // Given
            val shouldCollectConsent = true

            // When
            coEvery { repository.getCentricsStatus() }
                .returns(flowOf(DataState.success(shouldCollectConsent)))

            // Invoke
            val centricsStatusFlow = sut()

            // Then
            MatcherAssert.assertThat(centricsStatusFlow, CoreMatchers.notNullValue())

            val centricsDataState = centricsStatusFlow.first()
            MatcherAssert.assertThat(centricsDataState, CoreMatchers.notNullValue())
            MatcherAssert.assertThat(
                centricsDataState,
                CoreMatchers.instanceOf(DataState.Success::class.java),
            )

            val userCentricsReadyStatus = (centricsDataState as DataState.Success).data
            MatcherAssert.assertThat(userCentricsReadyStatus, CoreMatchers.notNullValue())
        }
}
