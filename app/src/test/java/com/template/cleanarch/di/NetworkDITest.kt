package com.template.cleanarch.di

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.template.data.datasource.remote.api.IAuthApi
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/****
 * Network module test configuration with mockserver url
 * Author: Lajesh Dineshkumar
 * Company:
 * Created on: 2/22/21
 * Modified on: 2/22/21
 *****/
fun configureNetworkModuleForTest(baseApi: String) = module {
    single {
        Retrofit.Builder()
            .baseUrl(baseApi)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

}