package com.usercentrics.interviewtest.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.usercentrics.interviewtest.data.DataState
import com.usercentrics.interviewtest.data.usecases.CalculateCentricsCostUseCase
import com.usercentrics.interviewtest.data.usecases.FetchCentricsStatusUseCase
import com.usercentrics.interviewtest.data.usecases.HandleCentricsBannerUseCase
import com.usercentrics.sdk.UsercentricsBanner
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.jetbrains.annotations.VisibleForTesting
import javax.inject.Inject

/**
 * The HomeViewModel.kt, viewModel to perform the actions of the home screens and to manipulate the fetched data
 */
@Suppress("ktlint:standard:backing-property-naming")
@HiltViewModel
class HomeViewModel
    @Inject
    constructor(
        private val fetchCentricsStatusUseCase: FetchCentricsStatusUseCase,
        private val centricsBannerUseCase: HandleCentricsBannerUseCase,
        private val calculateCentricsCostUseCase: CalculateCentricsCostUseCase,
    ) : ViewModel() {
        private var _uiState = MutableLiveData<HomeUiState>()
        var uiStateLiveData: LiveData<HomeUiState> = _uiState

        private var _shouldCollectConsent = MutableLiveData<Boolean>()
        var shouldCollectConsent: LiveData<Boolean> = _shouldCollectConsent

        private var _totalCost = MutableLiveData<Int>()
        var totalCostLiveData: LiveData<Int> = _totalCost

        fun getUserCentricsStatus() {
            _uiState.postValue(LoadingState)

            viewModelScope.launch {
                fetchCentricsStatusUseCase.invoke().collect { dataState ->
                    when (dataState) {
                        is DataState.Success -> {
                            _uiState.postValue(ContentState)

                            val shouldCollectConsent = dataState.data
                            if (shouldCollectConsent) {
                                _shouldCollectConsent.postValue(shouldCollectConsent)
                            } else {
                                calculateCost()
                            }
                        }

                        is DataState.Error -> {
                            _uiState.postValue(ErrorState(dataState.message))
                        }
                    }
                }
            }
        }

        fun showUserCentricsBanner(banner: UsercentricsBanner) {
            _uiState.postValue(LoadingState)

            viewModelScope.launch {
                centricsBannerUseCase.invoke(banner).collect { dataState ->
                    when (dataState) {
                        is DataState.Success -> {
                            _uiState.postValue(ContentState)
                            calculateCost()
                        }

                        is DataState.Error -> {
                            _uiState.postValue(ErrorState(dataState.message))
                        }
                    }
                }
            }
        }

        @VisibleForTesting
        fun calculateCost() {
            _uiState.postValue(LoadingState)

            viewModelScope.launch {
                calculateCentricsCostUseCase.invoke().collect { dataState ->
                    when (dataState) {
                        is DataState.Success -> {
                            _uiState.postValue(ContentState)
                            dataState.data.let {
                                _totalCost.postValue(it.toInt()) // since the Ui shows score as Integer
                            }
                        }

                        is DataState.Error -> {
                            _uiState.postValue(ErrorState(dataState.message))
                        }
                    }
                }
            }
        }
    }
