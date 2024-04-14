package com.usercentrics.interviewtest.base

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.usercentrics.interviewtest.ui.MainActivity

abstract class BaseFragment : Fragment {

    constructor() : super()
    constructor(@LayoutRes contentLayoutId: Int) : super(contentLayoutId)

    protected fun getRootActivity(): MainActivity = activity as MainActivity

    protected fun showProgress(){
        getRootActivity().showHideProgress()
    }

    protected fun hideProgress(){
        getRootActivity().showHideProgress(false)
    }

}
