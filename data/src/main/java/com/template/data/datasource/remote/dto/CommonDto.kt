package com.template.data.datasource.remote.dto

import com.google.gson.annotations.SerializedName

/****
 * File Description
 * Author: Lajesh Dineshkumar
 * Company:
 * Created on: 2/1/21
 * Modified on: 2/1/21
 *****/
sealed class CommonDto {

    data class CommonResponse<T>(val response: Any?, val data: T?) : CommonDto()

    data class Error(
        @SerializedName("code")val errorCode: String,
        @SerializedName("message")val errorMessage: String
    ) : CommonDto()

    data class ServerDate(
        @SerializedName("serverDateTime")val dateTime: String? = ""
    ) : CommonDto()

    data class Location(
        @SerializedName("latitude") val latitude: Double,
        @SerializedName("longitude") val longitude: Double
    )
}