package com.usercentrics.interviewtest.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.usercentrics.interviewtest.data.model.CentricsService
import com.usercentrics.interviewtest.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    val centrics = arrayListOf<CentricsService>()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun showHideProgress(isVisible: Boolean = true) {
        binding.layoutPB.progressLayout.isVisible = isVisible
    }
}
