package com.template.feature_profile.profile

import com.template.core.utils.NavigationCommand
import com.template.core.viewmodel.base.BaseViewModel

/****
 * File Description
 * Author: Lajesh Dineshkumar
 * Company:
 * Created on: 2/4/21
 * Modified on: 2/4/21
 *****/
class ProfileViewModel : BaseViewModel() {

    fun details(){
        navigationCommands.value = NavigationCommand.To(ProfileFragmentDirections.actionProfileToEditProfile())
    }
}