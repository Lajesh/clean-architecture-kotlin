package com.template.cleanarch.di.module

import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

/****
 * File Description
 * Author: Lajesh Dineshkumar
 * Company:
 * Created on: 2/2/21
 * Modified on: 2/2/21
 *****/
object ApplicationModule {
    fun load() {
        loadKoinModules(module {

        })
    }
}