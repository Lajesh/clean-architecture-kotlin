package com.template.domain.di

import com.template.domain.usecases.auth.AuthUseCaseImpl
import com.template.domain.usecases.auth.IAuthUseCase
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

/****
 * Module which provides the factory instance of Usecase
 * Author: Lajesh Dineshkumar
 * Company:
 * Created on: 2/1/21
 * Modified on: 2/1/21
 *****/
object UseCaseModule {
    fun load(){
        loadKoinModules(authUSeCaseModules)
    }

    val authUSeCaseModules = module {
        factory<IAuthUseCase> { AuthUseCaseImpl(authRepository = get()) }
    }
}