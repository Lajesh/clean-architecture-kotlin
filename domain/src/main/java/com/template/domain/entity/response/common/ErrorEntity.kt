package com.template.domain.entity.common

/****
 * Keep all the error related model class here
 * Author: Lajesh Dineshkumar
 * Company:
 * Created on: 1/31/21
 * Modified on: 1/31/21
 *****/
sealed class ErrorEntity {
    data class Error(val errorCode: Any?, val errorMessage: String? = "") : ErrorEntity()
}