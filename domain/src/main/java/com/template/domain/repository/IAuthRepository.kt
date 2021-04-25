package com.template.domain.repository

import com.template.domain.common.ResultState
import com.template.domain.entity.common.CommonEntity
import com.template.domain.entity.request.AuthRequest
import com.template.domain.entity.response.auth.AuthEntity
import kotlinx.coroutines.flow.Flow

/****
 * The abstraction of Auth repositories goes here
 * Author: Lajesh Dineshkumar
 * Company:
 * Created on: 2/1/21
 * Modified on: 2/1/21
 *****/
interface IAuthRepository {

    fun signIn(signinRequest: AuthRequest.SigninRequest) : Flow<ResultState<AuthEntity.LoginResponse>>

    fun signUp(signupRequest: AuthRequest.SignupRequest) : Flow<ResultState<CommonEntity.CommonResponse<String>>>
}