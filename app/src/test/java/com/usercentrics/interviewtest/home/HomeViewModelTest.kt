package com.usercentrics.interviewtest.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.usercentrics.interviewtest.data.DataState
import com.usercentrics.interviewtest.data.TestDataSource.ERROR_MESSAGE
import com.usercentrics.interviewtest.data.usecases.CalculateCentricsCostUseCase
import com.usercentrics.interviewtest.data.usecases.FetchCentricsStatusUseCase
import com.usercentrics.interviewtest.data.usecases.HandleCentricsBannerUseCase
import com.usercentrics.interviewtest.ui.home.ContentState
import com.usercentrics.interviewtest.ui.home.ErrorState
import com.usercentrics.interviewtest.ui.home.HomeUiState
import com.usercentrics.interviewtest.ui.home.HomeViewModel
import com.usercentrics.interviewtest.utils.TestCoroutineRule
import io.mockk.Called
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
@ExperimentalCoroutinesApi
class HomeViewModelTest {
    private lateinit var sut: HomeViewModel

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesRule = TestCoroutineRule()

    @MockK
    lateinit var fetchCentricsStatusUseCase: FetchCentricsStatusUseCase

    @MockK
    lateinit var centricsBannerUseCase: HandleCentricsBannerUseCase

    @MockK
    lateinit var calculateCentricsCostUseCase: CalculateCentricsCostUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `test when getUserCentricsStatus is called, the getUserCentricsReadyStatus is fetched`() =
        runBlocking {
            // Given
            val shouldCollectConsent = true
            val uiObserver = mockk<Observer<HomeUiState>>(relaxed = true)
            val booleanObserver = mockk<Observer<Boolean>>(relaxed = true)

            // When
            coEvery { fetchCentricsStatusUseCase.invoke() }
                .returns(flowOf(DataState.success(shouldCollectConsent)))

            // Invoke
            sut =
                HomeViewModel(
                    fetchCentricsStatusUseCase = fetchCentricsStatusUseCase,
                    centricsBannerUseCase = centricsBannerUseCase,
                    calculateCentricsCostUseCase = calculateCentricsCostUseCase,
                )

            sut.uiStateLiveData.observeForever(uiObserver)
            sut.shouldCollectConsent.observeForever(booleanObserver)

            // Then
            sut.getUserCentricsStatus()
            coVerify(exactly = 1) { fetchCentricsStatusUseCase.invoke() }
            verify { uiObserver.onChanged(match { it == ContentState }) }
            verify { booleanObserver.onChanged(match { it == shouldCollectConsent }) }
        }

    @Test
    fun `test when getUserCentricsStatus is called for error, then return type is error`() =
        runBlocking {
            // Given
            val givenCentricsReadyStatusError = ERROR_MESSAGE
            val uiObserver = mockk<Observer<HomeUiState>>(relaxed = true)
            val booleanObserver = mockk<Observer<Boolean>>(relaxed = true)

            // When
            coEvery { fetchCentricsStatusUseCase.invoke() }
                .returns(
                    flowOf(
                        DataState.Error<Boolean>(
                            givenCentricsReadyStatusError,
                        ),
                    ),
                )

            // Invoke
            sut =
                HomeViewModel(
                    fetchCentricsStatusUseCase = fetchCentricsStatusUseCase,
                    centricsBannerUseCase = centricsBannerUseCase,
                    calculateCentricsCostUseCase = calculateCentricsCostUseCase,
                )

            sut.uiStateLiveData.observeForever(uiObserver)
            sut.shouldCollectConsent.observeForever(booleanObserver)

            // Then
            sut.getUserCentricsStatus()

            coVerify(exactly = 1) { fetchCentricsStatusUseCase.invoke() }
            verify { uiObserver.onChanged(match { it is ErrorState && it.message == givenCentricsReadyStatusError }) }
            verify { booleanObserver wasNot Called }
        }

    @Test
    fun `test when calculateCost is called, the consent cost score is fetched`() =
        runBlocking {
            // Given
            val costScore = 30.0
            val uiObserver = mockk<Observer<HomeUiState>>(relaxed = true)
            val intObserver = mockk<Observer<Int>>(relaxed = true)

            // When
            coEvery { calculateCentricsCostUseCase.invoke() }
                .returns(flowOf(DataState.success(costScore)))

            // Invoke
            sut =
                HomeViewModel(
                    fetchCentricsStatusUseCase = fetchCentricsStatusUseCase,
                    centricsBannerUseCase = centricsBannerUseCase,
                    calculateCentricsCostUseCase = calculateCentricsCostUseCase,
                )

            sut.uiStateLiveData.observeForever(uiObserver)
            sut.totalCostLiveData.observeForever(intObserver)

            // Then
            sut.calculateCost()
            coVerify(exactly = 1) { calculateCentricsCostUseCase.invoke() }
            verify { uiObserver.onChanged(match { it == ContentState }) }
            verify { intObserver.onChanged(match { it == costScore.toInt() }) }
        }

    @Test
    fun `test when calculateCost is invoked for error, then return type is error`() =
        runBlocking {
            // Given
            val givenCentricsReadyStatusError = ERROR_MESSAGE
            val uiObserver = mockk<Observer<HomeUiState>>(relaxed = true)
            val intObserver = mockk<Observer<Int>>(relaxed = true)

            // When
            coEvery { calculateCentricsCostUseCase.invoke() }
                .returns(
                    flowOf(DataState.Error<Double>(givenCentricsReadyStatusError)),
                )

            // Invoke
            sut =
                HomeViewModel(
                    fetchCentricsStatusUseCase = fetchCentricsStatusUseCase,
                    centricsBannerUseCase = centricsBannerUseCase,
                    calculateCentricsCostUseCase = calculateCentricsCostUseCase,
                )

            sut.uiStateLiveData.observeForever(uiObserver)
            sut.totalCostLiveData.observeForever(intObserver)

            // Then
            sut.calculateCost()

            coVerify(exactly = 1) { calculateCentricsCostUseCase.invoke() }
            verify { uiObserver.onChanged(match { it is ErrorState && it.message == givenCentricsReadyStatusError }) }
            verify { intObserver wasNot Called }
        }
}
