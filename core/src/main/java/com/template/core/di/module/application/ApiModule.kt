package com.template.core.di.module.application

import com.template.data.datasource.remote.api.IAuthApi
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import retrofit2.Retrofit

/****
 * API Module ehich provides the instance of API Endpoints
 * Author: Lajesh Dineshkumar
 * Company:
 * Created on: 2/2/21
 * Modified on: 2/2/21
 *****/
object ApiModule {
    fun load(){
        loadKoinModules(apiModules)
    }

    val apiModules = module {
        single {
            get<Retrofit>().create(IAuthApi::class.java)
        }
    }
}