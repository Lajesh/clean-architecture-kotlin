package com.template.cleanarch.di.module

import com.template.cleanarch.presentation.view.MainViewModel
import com.template.core.viewmodel.SharedViewModel
import com.template.core.viewmodel.ToolbarPropertyViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

/****
 * DI module which provides all the viewmodel instances
 * Author: Lajesh Dineshkumar
 * Company:
 * Created on: 2/3/21
 * Modified on: 2/3/21
 *****/
object ViewModelModule {
    fun load() {
        loadKoinModules(module {
            viewModel {
                MainViewModel()
            }

            viewModel {
                ToolbarPropertyViewModel()
            }

            viewModel {
                SharedViewModel()
            }

        })
    }
}