package com.template.core.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import com.template.core.R
import com.template.core.common.Constants
import com.template.core.common.Constants.LANG_CODE_ARABIC
import com.template.core.common.Constants.LANG_CODE_ENGLISH
import com.template.core.common.SharedPrefConstants
import com.template.core.utils.PreferenceUtil.get
import com.template.core.utils.PreferenceUtil.set
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump
import java.util.*

/****
 * Localization manager class which handles the locale change within the application
 * Author: Lajesh Dineshkumar
 * Company:
 * Created on: 2020-03-02
 * Modified on: 2020-03-02
 *****/
object LocaleManager {

    private var sharedPreference: SharedPreferences? = null

    /**
     * This method sets the application locale based on device display language.
     * If the device display language is arabic/french and the app is opening for the first time,
     * then this method will update the resources with the current device language
     */
    fun setLocale(context: Context?): Context? {
        val nonNullContext = context?.let { it } ?: return null
        val currentLanguage = getCurrentLanguage(nonNullContext)
        val deviceLanguage = currentLanguage ?: Locale.getDefault().language
        return if (arrayOf(
                LANG_CODE_ARABIC,
                LANG_CODE_ENGLISH
            ).contains(deviceLanguage)
        ) {
            updateResources(nonNullContext, deviceLanguage)
        } else {
            updateResources(nonNullContext, LANG_CODE_ENGLISH)
        }
    }

    /**
     * This method is used to set the new locale.
     * Say if you want to set the locale from UI, use this method
     * @param context : Activity Context
     * @param language: Language code of the selected language
     */
    fun setNewLocale(context: Context, language: String): Context? {
        return updateResources(context, language)
    }

    /**
     * This method will return the current language which is already set.
     * If not set, it will return english by default
     * @param context: Activity Context
     */
    fun getCurrentLanguage(context: Context?): String? {

        val currentLanguage: String?
        val nonNullContext = context?.let { it } ?: return null

        if (sharedPreference == null)
            sharedPreference =
                PreferenceUtil.customPrefs(nonNullContext, SharedPrefConstants.PREFERENCE_LANGUAGE)

        val nonNullSharedPref = sharedPreference?.let { it } ?: return null

        currentLanguage = nonNullSharedPref[SharedPrefConstants.SELECTED_LANGUAGE]

        return currentLanguage
    }


    /**
     * This method will saveClick the user selected language on shared preference
     * @param context : Activity Context
     * @param language: Language code
     */
    fun persistLanguagePreference(context: Context, language: String) {
        if (sharedPreference == null)
            sharedPreference =
                PreferenceUtil.customPrefs(context, SharedPrefConstants.PREFERENCE_LANGUAGE)
        val nonNullSharedPref = sharedPreference?.let { it } ?: return
        nonNullSharedPref[SharedPrefConstants.SELECTED_LANGUAGE] = language
    }

    /**
     * This method will update the resources based on language code passed in.
     * @param context : Context
     * @param language: Language code selected
     */
    private fun updateResources(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val configuration = Configuration(context.resources.configuration)
        configuration.setLocale(locale)
        return context.createConfigurationContext(configuration)
    }

    fun notifyLanguageChange(activity: Activity) {

        updateCalligraphyViewPump()

        val intent = activity.packageManager.getLaunchIntentForPackage(activity.packageName)
        intent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent?.putExtra(Constants.LANGUAGE_SWITCH, true)
        activity.finish()
        activity.startActivity(intent)
    }

    private fun updateCalligraphyViewPump() {
        ViewPump.init(
            ViewPump.builder()
                .addInterceptor(
                    CalligraphyInterceptor(
                        CalligraphyConfig.Builder()
                            .setDefaultFontPath("fonts/roboto_regular.ttf")
                            .setFontAttrId(R.attr.fontPath)
                            .build()
                    )
                )
                .build()
        )
    }

    fun getCurrentLocaleInfo(context: Context?): LocaleInfo {
        return getLanguageFullName(context, getCurrentLanguage(context) ?: "")
    }

    fun getLanguageFullName(context: Context?, shortName: String): LocaleInfo {
        return when {
            shortName.equals(LANG_CODE_ARABIC, true) -> LocaleInfo(
                getCurrentLanguage(context),
                context?.getString(R.string.lang_arabic)
            )
            shortName.equals(LANG_CODE_ENGLISH, true) -> LocaleInfo(
                getCurrentLanguage(context),
                context?.getString(R.string.lang_english)
            )
            else -> LocaleInfo(
                Locale.getDefault()?.language,
                if (Locale.getDefault()?.language == "ar") context?.getString(R.string.lang_arabic) else
                    context?.getString(R.string.lang_english)
            )
        }
    }

    fun getLanguageFromShortName(context: Context?, fullName: String): LocaleInfo {
        return when {
            fullName.equals(context?.getString(R.string.lang_english), true) -> LocaleInfo(
                LANG_CODE_ENGLISH, context?.getString(R.string.lang_english)

            )
            fullName.equals(context?.getString(R.string.lang_arabic), true) -> LocaleInfo(
                LANG_CODE_ARABIC, context?.getString(R.string.lang_arabic)

            )
            else -> LocaleInfo(LANG_CODE_ENGLISH, context?.getString(R.string.lang_english))
        }
    }

    data class LocaleInfo(val localeShortName: String?, val localeFullName: String?)
}
