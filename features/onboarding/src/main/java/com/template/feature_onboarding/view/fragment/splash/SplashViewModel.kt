package com.template.feature_onboarding.view.fragment.splash

import com.template.core.utils.NavigationCommand
import com.template.core.viewmodel.base.BaseViewModel

/****
 * SplashViewModel
 * Author: Lajesh Dineshkumar
 * Company:
 * Created on: 2/3/21
 * Modified on: 2/3/21
 *****/
class SplashViewModel : BaseViewModel() {

    init {
        android.os.Handler().postDelayed({
            navigationCommands.value = NavigationCommand.To(SplashFragmentDirections.actionSplashToLogin())
        }, 5000)
    }
}