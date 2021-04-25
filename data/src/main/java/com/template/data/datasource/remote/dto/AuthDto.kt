package com.template.data.datasource.remote.dto

import com.google.gson.annotations.SerializedName

/****
 * Keep all the AUTH related DTOs here
 * Author: Lajesh Dineshkumar
 * Company: Farabi Technologies
 * Created on: 4/25/21
 * Modified on: 4/25/21
 *****/
sealed class AuthDto {

    data class LoginResponse(
        @SerializedName("userDetails") val userDetails: UserDetails? = null
    ) : AuthDto()

    data class UserDetails(
        @SerializedName("firstName") val firstName: String? = "",
        @SerializedName("lastName") val lastName: String? = "",
        @SerializedName("phoneNumber") val phoneNumber: String?= "",
        @SerializedName("countryCode") val countryCode: String? = ""
    ) : AuthDto()
}