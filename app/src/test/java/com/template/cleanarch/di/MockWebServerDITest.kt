package com.template.cleanarch.di

import okhttp3.mockwebserver.MockWebServer
import org.koin.dsl.module

/****
 * Creates Mockwebserver instance for testing
 * Author: Lajesh Dineshkumar
 * Company:
 * Created on: 2/22/21
 * Modified on: 2/22/21
 *****/

val mockwebserverDITest = module {
    factory {
        MockWebServer()
    }
}