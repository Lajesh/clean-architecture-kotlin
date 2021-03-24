package com.template.feature_onboarding.di

import com.template.feature_onboarding.view.fragment.signin.LoginViewModel
import com.template.feature_onboarding.view.fragment.splash.SplashViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

/****
 * File Description
 * Author: Lajesh Dineshkumar
 * Company:
 * Created on: 2/8/21
 * Modified on: 2/8/21
 *****/
object OnboardingViewModelModule {
    fun load() {
        loadKoinModules(module {

            viewModel {
                LoginViewModel(get())
            }


            viewModel {
                SplashViewModel()
            }

        })
    }
}