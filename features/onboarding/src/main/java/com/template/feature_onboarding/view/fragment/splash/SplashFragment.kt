package com.template.feature_onboarding.view.fragment.splash

import com.template.core.ui.base.BaseFragment
import com.template.feature_onboarding.R
import com.template.feature_onboarding.BR
import com.template.feature_onboarding.databinding.FragmentSplashBinding

/****
 * SplashFragment
 * Author: Lajesh Dineshkumar
 * Company:
 * Created on: 2/3/21
 * Modified on: 2/3/21
 *****/
class SplashFragment : BaseFragment<SplashViewModel, FragmentSplashBinding>(SplashViewModel::class){
    override val layoutRes: Int
        get() = R.layout.fragment_splash
    override val bindingVariable: Int
        get() = BR.viewModel
}