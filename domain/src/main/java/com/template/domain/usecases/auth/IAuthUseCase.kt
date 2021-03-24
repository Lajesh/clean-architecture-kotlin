package com.template.domain.usecases.auth

import com.template.domain.common.ResultState
import com.template.domain.entity.common.CommonEntity
import com.template.domain.entity.request.AuthRequest
import com.template.domain.usecases.base.BaseUseCase
import kotlinx.coroutines.flow.Flow

/****
 * Keep all the authentication related business use cases here
 * Author: Lajesh Dineshkumar
 * Company:
 * Created on: 1/31/21
 * Modified on: 1/31/21
 *****/
interface IAuthUseCase : BaseUseCase {

    fun signIn(signinRequest: AuthRequest.SigninRequest) : Flow<ResultState<CommonEntity.CommonResponse<Void>>>

    fun signUp(signupRequest: AuthRequest.SignupRequest) : Flow<ResultState<CommonEntity.CommonResponse<Void>>>

}