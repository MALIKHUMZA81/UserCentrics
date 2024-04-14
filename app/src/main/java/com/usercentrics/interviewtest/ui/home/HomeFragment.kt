package com.usercentrics.interviewtest.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.usercentrics.interviewtest.R
import com.usercentrics.interviewtest.base.BaseFragment
import com.usercentrics.interviewtest.databinding.FragmentHomeBinding
import com.usercentrics.interviewtest.utils.showToastMsg
import com.usercentrics.sdk.UsercentricsBanner
import dagger.hilt.android.AndroidEntryPoint

/**
 * The HomeFragment.kt
 */

@AndroidEntryPoint
class HomeFragment : BaseFragment(R.layout.fragment_home) {
    private val viewModel: HomeViewModel by activityViewModels()
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        initObservations()

        binding.btnCalculateCentricsScore.setOnClickListener {
            viewModel.getUserCentricsStatus()
        }
    }

    private fun initObservations() {
        viewModel.uiStateLiveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is LoadingState -> {
                    showProgress()
                }

                is ContentState -> {
                    hideProgress()
                }

                is ErrorState -> {
                    hideProgress()
                    showToastMsg(state.message)
                }
            }
        }

        viewModel.shouldCollectConsent.observe(viewLifecycleOwner) { shouldCollectConsent ->
            if (shouldCollectConsent) {
                viewModel.showUserCentricsBanner(banner = UsercentricsBanner(requireContext()))
            }
        }

        viewModel.totalCostLiveData.observe(viewLifecycleOwner) { totalCost ->
            binding.tvCalculatedResult.text = totalCost.toString()
        }
    }
}
