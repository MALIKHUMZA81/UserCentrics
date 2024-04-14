package com.usercentrics.interviewtest.utils

import com.usercentrics.interviewtest.App
import com.usercentrics.interviewtest.R


/**
 * The StringExtension.kt
 */

fun somethingWentWrong() = App.getAppContext().getString(R.string.message_something_went_wrong_str)

fun nothingFound() = App.getAppContext().getString(R.string.nothing_found)