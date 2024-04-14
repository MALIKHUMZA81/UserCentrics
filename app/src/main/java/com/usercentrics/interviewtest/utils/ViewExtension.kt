package com.usercentrics.interviewtest.utils

import android.widget.Toast
import com.usercentrics.interviewtest.App


/**
 * Extension function to show toast message
 */
fun showToastMsg(message: String) {
    Toast.makeText(App.getAppContext(), message, Toast.LENGTH_SHORT).show()
}