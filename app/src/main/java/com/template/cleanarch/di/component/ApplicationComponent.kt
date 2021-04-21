package com.template.cleanarch.di.component

import com.template.cleanarch.di.module.DbModule
import com.template.cleanarch.di.module.ViewModelModule
import com.template.core.di.module.application.RetrofitModule
import com.template.cleanarch.di.module.ApplicationModule
import com.template.core.di.module.application.OkhttpModule
import com.template.data.di.ApiModule
import com.template.data.di.RepositoryModule
import com.template.domain.di.UseCaseModule
import com.template.feature_onboarding.di.OnboardingViewModelModule
import com.template.feature_profile.di.ProfileViewModelModule

/****
 * Application component which loads all the Koin Modules
 * Author: Lajesh Dineshkumar
 * Company:
 * Created on: 2/2/21
 * Modified on: 2/2/21
 *****/
object ApplicationComponent {
    fun loadAllModules(){
        RetrofitModule.load()
        OkhttpModule.load()
        RepositoryModule.load()
        UseCaseModule.load()
        ApiModule.load()
        ApplicationModule.load()
        ViewModelModule.load()
        OnboardingViewModelModule.load()
        ProfileViewModelModule.load()
        DbModule.load()

    }
}