package com.template.core.config

import com.template.core.BuildConfig

/****
 * Keep all the common configurations here
 * Author: Lajesh Dineshkumar
 * Company:
 * Created on: 2020-03-29
 * Modified on: 2020-03-29
 *****/
object Configuration {
    // Deployment Types
    private const val DEV = "dev"
    private const val SIT = "sit"
    private const val UAT = "uat"
    private const val PROD = "prod"
    private const val PILOT = "pilot"

    // Host URLs
    private const val DEV_URL = "http://demo4597733.mockable.io/"
    private const val SIT_URL = "http://demo4597733.mockable.io/sit/"
    private const val UAT_URL = "http://demo4597733.mockable.io/uat/"
    private const val PROD_URL = "http://demo4597733.mockable.io/prod/"
    private const val PILOT_URL = "http://demo4597733.mockable.io/pilot/"

    const val CONNECT_TIMEOUT: Long = 60
    const val READ_TIMEOUT: Long = 60
    const val CALL_TIMEOUT: Long = 60


    val baseURL: String
        get() {

            return when (BuildConfig.FLAVOR) {

                DEV -> DEV_URL

                SIT -> SIT_URL

                UAT -> UAT_URL

                PROD -> PROD_URL

                PILOT -> PILOT_URL

                else -> DEV_URL
            }
        }
}
