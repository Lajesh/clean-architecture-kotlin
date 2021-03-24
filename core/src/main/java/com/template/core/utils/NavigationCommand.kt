package com.template.core.utils

import android.net.Uri
import androidx.navigation.NavDirections

/****
 * The app will be using command pattern to handle navigation within the app
 * Author: Lajesh Dineshkumar
 * Company:
 * Created on: 2/3/21
 * Modified on: 2/3/21
 *****/
sealed class NavigationCommand {
    data class To(val directions: NavDirections): NavigationCommand()
    object Back: NavigationCommand()
    data class BackTo(val destinationId: Int): NavigationCommand()
    data class ToByDeepLink(val deepLink: Uri): NavigationCommand()
}