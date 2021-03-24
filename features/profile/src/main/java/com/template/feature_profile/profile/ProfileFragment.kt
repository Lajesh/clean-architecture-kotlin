package com.template.feature_profile.profile

import com.template.core.ui.base.BaseFragment
import com.template.feature_profile.BR
import com.template.feature_profile.R
import com.template.feature_profile.databinding.FragmentProfileBinding

/****
 * File Description
 * Author: Lajesh Dineshkumar
 * Company:
 * Created on: 2/4/21
 * Modified on: 2/4/21
 *****/
class ProfileFragment : BaseFragment<ProfileViewModel, FragmentProfileBinding>(ProfileViewModel::class) {
    override val layoutRes: Int
        get() = R.layout.fragment_profile
    override val bindingVariable: Int
        get() = BR.viewModel
}