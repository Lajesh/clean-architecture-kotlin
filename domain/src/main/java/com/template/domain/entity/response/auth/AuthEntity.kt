package com.template.domain.entity.response.auth

/****
 * Keep all the authentication use case related entities over here.
 * Entity model contains only the fields needed for UI
 * Author: Lajesh Dineshkumar
 * Company: Farabi Technologies
 * Created on: 4/25/21
 * Modified on: 4/25/21
 *****/
sealed class AuthEntity {

    data class LoginResponse(
        val userDetails: UserDetails? = null
    ) : AuthEntity()

    data class UserDetails(
        val firstName: String? = "",
        val lastName: String? = ""
    ) : AuthEntity()
}