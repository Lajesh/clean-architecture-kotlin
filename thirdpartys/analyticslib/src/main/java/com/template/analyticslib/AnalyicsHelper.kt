package com.template.analyticslib

import android.app.Activity

/****
 * Abstraction of Analytics
 * Author: Lajesh Dineshkumar
 * Company:
 * Created on: 2/8/21
 * Modified on: 2/8/21
 *****/
interface AnalyicsHelper {
    fun sendScreenView(screenName: String, activity: Activity)
    fun logUiEvent(itemId: String, action: String)
    fun setUserSignedIn(isSignedIn: Boolean)
    fun setUserRegistered(isRegistered: Boolean)
}