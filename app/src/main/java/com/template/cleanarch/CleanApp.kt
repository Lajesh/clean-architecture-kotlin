package com.template.cleanarch

import android.app.Application
import android.content.Context
import com.template.cleanarch.di.AppInjector
import com.template.cleanarch.di.component.ApplicationComponent.loadAllModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/****
 * Application class
 * Author: Lajesh Dineshkumar
 * Created on: 1/31/21
 * Modified on: 1/31/21
 *****/
class CleanApp : Application() {

    private var localeContext: Context? = null

    init {
        instance = this
    }

    companion object {
        private lateinit var instance: CleanApp

        private var isAppVisible: Boolean = false

        fun applicationContext(): Context {
            return instance.applicationContext
        }

        fun localeContext(): Context {
            return instance.localeContext ?: instance.applicationContext
        }

        fun getInstance(): CleanApp {
            return instance
        }

        fun setInstance(application: CleanApp) {
            instance = application
        }

        fun isApplicationVisible(): Boolean {
            return isAppVisible
        }

        fun setAppVisible(isVisible: Boolean) {
            isAppVisible = isVisible
        }
    }

    override fun onCreate() {
        super.onCreate()

        AppInjector.init(this)

        startKoin {
            androidContext(this@CleanApp)
        }

        loadAllModules()
    }

    fun setLocaleContext(context: Context) {
        this.localeContext = context
    }
}