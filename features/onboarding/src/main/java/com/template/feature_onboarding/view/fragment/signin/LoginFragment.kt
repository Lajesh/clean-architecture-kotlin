package com.template.feature_onboarding.view.fragment.signin

import com.template.core.ui.base.BaseFragment
import com.template.feature_onboarding.R
import com.template.feature_onboarding.BR
import com.template.feature_onboarding.databinding.FragmentLoginBinding

/****
 * File Description
 * Author: Lajesh Dineshkumar
 * Company:
 * Created on: 2/3/21
 * Modified on: 2/3/21
 *****/
class LoginFragment : BaseFragment<LoginViewModel, FragmentLoginBinding>(LoginViewModel::class) {
    override val layoutRes: Int
        get() = R.layout.fragment_login
    override val bindingVariable: Int
        get() = BR.viewModel
}