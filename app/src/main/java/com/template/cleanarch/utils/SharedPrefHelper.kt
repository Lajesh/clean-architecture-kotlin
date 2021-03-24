package com.template.cleanarch.utils

import android.content.SharedPreferences
import com.template.cleanarch.CleanApp
import com.template.cleanarch.utils.PreferenceUtil.get
import com.template.cleanarch.utils.PreferenceUtil.set
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
class SharedPrefHelper {

    private var sharedPreferences: SharedPreferences =
        PreferenceUtil.customPrefs(CleanApp.applicationContext(), SharedPrefConstants.APP_PREFS)

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

    fun getCurrentLocale(): LocaleManager.LocaleInfo? {
        return LocaleManager.getCurrentLocaleInfo(CleanApp.localeContext())
    }
}