package com.template.domain.usecases.auth

import com.template.domain.common.ResultState
import com.template.domain.entity.common.CommonEntity
import com.template.domain.entity.request.AuthRequest
import com.template.domain.repository.IAuthRepository
import kotlinx.coroutines.flow.Flow

/****
 * Implementation of auth usecase goes here
 * Author: Lajesh Dineshkumar
 * Company:
 * Created on: 1/31/21
 * Modified on: 1/31/21
 *****/
class AuthUseCaseImpl(private val authRepository: IAuthRepository) : IAuthUseCase {
    override fun signIn(
       signinRequest: AuthRequest.SigninRequest
    ): Flow<ResultState<CommonEntity.CommonResponse<Void>>> = authRepository.signIn(
        signinRequest
    )

    override fun signUp(
       signupRequest: AuthRequest.SignupRequest
    ): Flow<ResultState<CommonEntity.CommonResponse<Void>>> = authRepository.signUp(
       signupRequest
    )
}