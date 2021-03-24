package com.template.core.utils

import android.content.Context
import android.content.SharedPreferences
import com.template.core.utils.PreferenceUtil.get
import com.template.core.utils.PreferenceUtil.set
import com.template.core.common.SharedPrefConstants
import com.template.core.common.SharedPrefConstants.DID_FAVORITE_OFFER
import com.template.core.common.SharedPrefConstants.WALLET_ONBOARDING_COMPLETED

/****
 * Keep all shared preference related methods here
 * Author: Lajesh Dineshkumar
 * Company:
 * Created on: 2020-03-02
 * Modified on: 2020-03-02
 *****/
class SharedPrefHelper constructor(context: Context) {

    private var sharedPreferences: SharedPreferences =
        PreferenceUtil.customPrefs(context, SharedPrefConstants.APP_PREFS)

    fun getToken(): String? {
        return sharedPreferences[SharedPrefConstants.TOKEN]
    }

    fun setToken(token: String?) {
        sharedPreferences[SharedPrefConstants.TOKEN] = token
    }

    fun saveFcmToken(token: String?) {
        sharedPreferences[SharedPrefConstants.PREF_FCM_TOKEN] = token
    }

    fun getFCMToken(): String? {
        return sharedPreferences[SharedPrefConstants.PREF_FCM_TOKEN]
    }

    fun isUserLogged(): Boolean {
        return getUserID() != 0
    }

    fun setWalletOnBoardingCompleted(completed: Boolean) {
        sharedPreferences[WALLET_ONBOARDING_COMPLETED] = completed
    }

    fun doFavoriteOffer() {
        sharedPreferences[DID_FAVORITE_OFFER] = true
    }

    fun didFavoriteOffer(): Boolean = sharedPreferences[DID_FAVORITE_OFFER] ?: false

    fun isWalletOnBoardingCompleted(): Boolean {
        return sharedPreferences[WALLET_ONBOARDING_COMPLETED] ?: false
    }

    private fun getUserID(): Int {
        return (sharedPreferences[SharedPrefConstants.USER_ID, "0"] ?: "0").toInt()
    }

}