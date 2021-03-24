package com.template.domain.entity.common

/****
 * Keep all the common entity class here
 * Author: Lajesh Dineshkumar
 * Company:
 * Created on: 1/31/21
 * Modified on: 1/31/21
 *****/
sealed class CommonEntity {

    data class CommonResponse<T>(
        val response: Any?,
        val data: T?
    ) : CommonEntity()

    data class ServerDate(
        val dateTime: String? = ""
    ) : CommonEntity()

    data class Location(
        val latitude: Double,
        val longitude: Double
    )
}
