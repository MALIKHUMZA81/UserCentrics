package com.usercentrics.interviewtest.home

import com.usercentrics.interviewtest.data.DataState
import com.usercentrics.interviewtest.data.repository.centrics.CentricsRepository
import com.usercentrics.interviewtest.data.usecases.HandleCentricsBannerUseCase
import com.usercentrics.interviewtest.utils.TestCoroutineRule
import com.usercentrics.sdk.UsercentricsBanner
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
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
class HandleCentricsBannerUseCaseTestService {
    @MockK
    private lateinit var repository: CentricsRepository

    @MockK
    private lateinit var banner: UsercentricsBanner

    private lateinit var sut: HandleCentricsBannerUseCase

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule = TestCoroutineRule()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        sut = HandleCentricsBannerUseCase(repository)
    }

    @Test
    fun `test handleUserCentricsBanner success`() =
        runBlocking {
            // Given
            val data = mockk<Unit>()

            // When
            coEvery { repository.handleUserCentricsBanner(banner) }
                .returns(flowOf(DataState.success(data)))

            // Invoke
            val dataUserResponseFlow = sut(banner)

            // Then
            MatcherAssert.assertThat(dataUserResponseFlow, CoreMatchers.notNullValue())

            val centricsDataState = dataUserResponseFlow.first()
            MatcherAssert.assertThat(centricsDataState, CoreMatchers.notNullValue())
            MatcherAssert.assertThat(
                centricsDataState,
                CoreMatchers.instanceOf(DataState.Success::class.java),
            )

            val userCentricsReadyStatus = (centricsDataState as DataState.Success).data
            MatcherAssert.assertThat(userCentricsReadyStatus, CoreMatchers.notNullValue())
        }
}
