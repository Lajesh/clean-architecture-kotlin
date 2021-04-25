package com.template.data.repository

import com.template.data.datasource.local.dao.BranchDao
import com.template.data.datasource.remote.api.IAuthApi
import com.template.data.mapper.dtotoentity.map
import com.template.domain.common.ResultState
import com.template.domain.entity.common.CommonEntity
import com.template.domain.entity.request.AuthRequest
import com.template.domain.entity.response.auth.AuthEntity
import com.template.domain.repository.IAuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/****
 * AuthRepository Implementation
 * Author: Lajesh Dineshkumar
 * Company:
 * Created on: 2/1/21
 * Modified on: 2/1/21
 *****/
class AuthRepositoryImpl(private val authApi: IAuthApi, private val branchDao: BranchDao) : BaseRepositoryImpl(), IAuthRepository {
    override fun signIn(
        signinRequest: AuthRequest.SigninRequest
    ): Flow<ResultState<AuthEntity.LoginResponse>> = flow {
        emit(apiCall { authApi.signIn(signinRequest).map() })
    }.flowOn(Dispatchers.IO)

    override fun signUp(
        signupRequest: AuthRequest.SignupRequest
    ): Flow<ResultState<CommonEntity.CommonResponse<String>>>  = flow {
           emit(apiCall { authApi.signUp(signupRequest).map()})
    }.flowOn(Dispatchers.IO)
}