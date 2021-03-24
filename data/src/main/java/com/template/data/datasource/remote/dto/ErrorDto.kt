package com.template.data.datasource.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * ERROR DTO Class
 * Created by Lajesh Dineshkumar on 2020-03-09.
 * Company: 
 * Email: lajeshds2007@gmail.com
 */
sealed class ErrorDto {

    data class ErrorResponse(@SerializedName("error") val error: Error? = null)

    data class Error(
        @SerializedName("type") val type: String? = "",
        @SerializedName("code") val code: Any?,
        @SerializedName("error_user_msg") var errorUserMsg: String? = "",
        @SerializedName("field_errors") val fieldErrors: List<FieldErrors>? = emptyList()
    )

    data class FieldErrors(
        @SerializedName("code") val code: String? = "",
        @SerializedName("message") val message: String? = ""
    )
}
