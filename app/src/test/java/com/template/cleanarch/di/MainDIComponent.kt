package com.template.cleanarch.di

import com.template.core.di.module.application.ApiModule
import com.template.data.di.RepositoryModule
import com.template.domain.di.UseCaseModule

/****
 * Main Koin DI component which helps to configure
 * Mockwebserver, Usecase and repository
 * Author: Lajesh Dineshkumar
 * Company:
 * Created on: 2/22/21
 * Modified on: 2/22/21
 *****/

fun configureTestAppComponent(baseApi: String) = listOf(
    mockwebserverDITest,
    configureNetworkModuleForTest(baseApi),
    ApiModule.apiModules,
    UseCaseModule.authUSeCaseModules,
    RepositoryModule.repositoryModules
)