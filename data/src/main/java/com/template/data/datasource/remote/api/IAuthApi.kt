package com.template.data.datasource.remote.api

import com.template.data.datasource.remote.dto.CommonDto
import com.template.domain.entity.request.AuthRequest
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/****
 * API endpoint of Authentication related service calls
 * Author: Lajesh Dineshkumar
 * Company:
 * Created on: 2/1/21
 * Modified on: 2/1/21
 *****/
interface IAuthApi {

    @POST("login")
    suspend fun signIn(@Body signinRequest: AuthRequest.SigninRequest) : CommonDto.CommonResponse<Void>

    @POST("signup")
    suspend fun signUp(@Body signupRequest: AuthRequest.SignupRequest) : CommonDto.CommonResponse<Void>
}