package com.template.data.mapper.dtotoentity

import com.template.data.datasource.remote.dto.AuthDto
import com.template.domain.entity.response.auth.AuthEntity

/****
 * File Description
 * Author: Lajesh Dineshkumar
 * Company: Farabi Technologies
 * Created on: 4/25/21
 * Modified on: 4/25/21
 *****/
fun AuthDto.UserDetails.map() = AuthEntity.UserDetails(
    firstName = firstName,
    lastName = lastName
)

fun AuthDto.LoginResponse.map() = AuthEntity.LoginResponse(
    userDetails = userDetails?.map()
)