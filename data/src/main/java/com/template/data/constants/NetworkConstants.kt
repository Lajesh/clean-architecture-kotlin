package com.template.data.constants

import androidx.annotation.IntDef
import androidx.annotation.StringDef

/**
 * Keep all the network related constants here
 * Created by Lajesh Dineshkumar on 2020-03-09.
 * Company: 
 * Email: lajeshds2007@gmail.com
 */
object NetworkConstants {
    @IntDef(
        NETWORK_ERROR_CODES.SERVICE_UNAVAILABLE, NETWORK_ERROR_CODES.MALFORMED_JSON,
        NETWORK_ERROR_CODES.NO_INTERNET, NETWORK_ERROR_CODES.UNEXPECTED_ERROR,
        NETWORK_ERROR_CODES.HTML_RESPONSE_ERROR
    )
    @Retention(AnnotationRetention.SOURCE)
    annotation class NETWORK_ERROR_CODES {
        companion object {
            const val SERVICE_UNAVAILABLE = 500
            const val MALFORMED_JSON = 501
            const val NO_INTERNET = 502
            const val UNEXPECTED_ERROR = 503
            const val HTML_RESPONSE_ERROR = 504
        }
    }

    @StringDef(
        NETWORK_ERROR_MESSAGES.SERVICE_UNAVAILABLE, NETWORK_ERROR_MESSAGES.MALFORMED_JSON,
        NETWORK_ERROR_MESSAGES.NO_INTERNET, NETWORK_ERROR_MESSAGES.UNEXPECTED_ERROR
    )
    @Retention(AnnotationRetention.SOURCE)
    annotation class NETWORK_ERROR_MESSAGES {
        companion object {
            const val SERVICE_UNAVAILABLE = "System temporarily unavailable, please try again later"
            const val MALFORMED_JSON = "Oops! We hit an error. Try again later."
            const val NO_INTERNET = "Oh! You are not connected to a wifi or cellular data network. Please connect and try again"
            const val UNEXPECTED_ERROR = "Something went wrong"
        }
    }
}
