package com.template.feature_onboarding.view.fragment.signin

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.template.core.utils.NavigationCommand
import com.template.core.viewmodel.base.BaseViewModel
import com.template.domain.common.ResultState
import com.template.domain.entity.request.AuthRequest
import com.template.domain.entity.response.auth.AuthEntity
import com.template.domain.usecases.auth.IAuthUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/****
 * File Description
 * Author: Lajesh Dineshkumar
 * Company:
 * Created on: 2/3/21
 * Modified on: 2/3/21
 *****/
class LoginViewModel constructor(
    private val authUseCase: IAuthUseCase
) : BaseViewModel() {

    val data = MutableLiveData(false)
    val userDetails = MutableLiveData<AuthEntity.UserDetails>()

    private var likeCount = 0

    fun getLikeCount() = likeCount

    fun addLikeCount() {
        viewModelScope.launch {
            likeCount += 1
            likeCount
        }
    }

    fun signup() {
        showLoading(true)
        viewModelScope.launch {
            authUseCase.signUp(AuthRequest.SignupRequest("", "dasdasd", true))
                .collect { state ->
                    when (state) {
                        is ResultState.Success -> {
                          login()
                        }

                        is ResultState.Error -> {
                            setError(error = state.error)
                            showLoading(false)
                        }
                    }

                }

        }
    }

    fun login() {
        showLoading(true)
        viewModelScope.launch {
            authUseCase.signIn(AuthRequest.SigninRequest("test@test.com", "dasdasd", 0, false))
                .collect { state ->
                    when (state) {
                        is ResultState.Success -> {
                            userDetails.value =  state.data.userDetails
                            data.value = true
                            showLoading(false)
                            navigationCommands.value =
                                NavigationCommand.To(LoginFragmentDirections.actionLoginToProfile())
                        }

                        is ResultState.Error -> {
                            setError(error = state.error)
                            showLoading(false)
                        }
                    }

                }

        }
    }

    fun ediProfile() {
        navigationCommands.value = NavigationCommand.ToByDeepLink(Uri.parse("dpl://editprofile"))
    }
}