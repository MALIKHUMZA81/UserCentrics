package com.usercentrics.interviewtest.ui.home

sealed class HomeUiState

object LoadingState : HomeUiState()
object ContentState : HomeUiState()
class ErrorState(val message: String) : HomeUiState()
