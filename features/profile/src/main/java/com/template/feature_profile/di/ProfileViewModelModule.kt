package com.template.feature_profile.di

import com.template.feature_profile.edit.EditProfileViewModel
import com.template.feature_profile.profile.ProfileViewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

/****
 * File Description
 * Author: Lajesh Dineshkumar
 * Company:
 * Created on: 2/8/21
 * Modified on: 2/8/21
 *****/
object ProfileViewModelModule {
    fun load() {
        loadKoinModules(module {

            viewModel {
                ProfileViewModel()
            }

            viewModel {
                EditProfileViewModel()
            }

        })
    }
}