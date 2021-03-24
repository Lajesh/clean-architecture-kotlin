package com.template.feature_profile.edit

import com.template.core.utils.NavigationCommand
import com.template.core.viewmodel.base.BaseViewModel
import com.template.feature_profile.R

/****
 * Edit profile viewmodel
 * Author: Lajesh Dineshkumar
 * Company:
 * Created on: 2/4/21
 * Modified on: 2/4/21
 *****/
class EditProfileViewModel : BaseViewModel() {

    fun backToLogin(){
        navigationCommands.value = NavigationCommand.BackTo(R.id.loginFragment)
    }
}