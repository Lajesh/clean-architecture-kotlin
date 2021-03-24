package com.template.feature_profile.edit

import com.template.core.ui.base.BaseFragment
import com.template.feature_profile.BR
import com.template.feature_profile.R
import com.template.feature_profile.databinding.FragmentEditProfileBinding

/****
 * File Description
 * Author: Lajesh Dineshkumar
 * Company:
 * Created on: 2/4/21
 * Modified on: 2/4/21
 *****/
class EditProfileFragment : BaseFragment<EditProfileViewModel, FragmentEditProfileBinding>(EditProfileViewModel::class) {
    override val layoutRes: Int
        get() = R.layout.fragment_edit_profile
    override val bindingVariable: Int
        get() = BR.viewModel
}