package com.template.analyticslib

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

/****
 * Implementation of Firebase Analytics
 * Author: Lajesh Dineshkumar
 * Company:
 * Created on: 2/8/21
 * Modified on: 2/8/21
 *****/
class FirebaseAnalyticsHelper constructor(application: Application) : AnalyicsHelper {
    companion object {
        private const val UPROP_USER_SIGNED_IN = "user_signed_in"
        private const val UPROP_USER_REGISTERED = "user_registered"
        private const val CONTENT_TYPE_SCREEN_VIEW = "screen"
        private const val KEY_UI_ACTION = "ui_action"
        private const val CONTENT_TYPE_UI_EVENT = "ui event"
    }

    private var firebaseAnalytics: FirebaseAnalytics =
        FirebaseAnalytics.getInstance(application.applicationContext)

    override fun sendScreenView(screenName: String, activity: Activity) {
        val params = Bundle().apply {
            putString(FirebaseAnalytics.Param.ITEM_ID, screenName)
            putString(
                FirebaseAnalytics.Param.CONTENT_TYPE,
                CONTENT_TYPE_SCREEN_VIEW
            )
        }
        firebaseAnalytics.run {
            setCurrentScreen(activity, screenName, null)
            logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, params)
        }
    }

    override fun logUiEvent(itemId: String, action: String) {
        val params = Bundle().apply {
            putString(FirebaseAnalytics.Param.ITEM_ID, itemId)
            putString(
                FirebaseAnalytics.Param.CONTENT_TYPE,
                CONTENT_TYPE_UI_EVENT
            )
            putString(KEY_UI_ACTION, action)
        }
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, params)
    }

    override fun setUserSignedIn(isSignedIn: Boolean) {
        firebaseAnalytics.setUserProperty(UPROP_USER_SIGNED_IN, isSignedIn.toString())
    }

    override fun setUserRegistered(isRegistered: Boolean) {
        firebaseAnalytics.setUserProperty(UPROP_USER_REGISTERED, isRegistered.toString())
    }
}