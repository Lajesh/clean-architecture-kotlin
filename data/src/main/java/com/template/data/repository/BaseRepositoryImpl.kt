package com.template.data.repository

import android.util.MalformedJsonException
import com.google.gson.GsonBuilder
import com.template.data.constants.NetworkConstants
import com.template.data.datasource.remote.dto.ErrorDto
import com.template.domain.common.ResultState
import com.template.domain.entity.response.common.ErrorEntity
import kotlinx.coroutines.delay
import okhttp3.ResponseBody
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import java.io.InterruptedIOException
import java.net.SocketException
import java.net.SocketTimeoutException

/****
 * File Description
 * Author: Lajesh Dineshkumar
 * Company:
 * Created on: 2/1/21
 * Modified on: 2/1/21
 *****/
abstract class BaseRepositoryImpl {
    private val logFormatter: String = "%s | %s"

    protected suspend fun <T : Any> apiCall(call: suspend () -> T): ResultState<T> {
        return try {
            val response = call()
            ResultState.Success(response)
        } catch (ex: Throwable) {
            ResultState.Error(handleError(ex))
        }
    }

    protected suspend fun <T : Any> apiCallRetry(
        times: Int = 3,
        call: suspend () -> T
    ): ResultState<T> {
        return try {
            val response = apiCallIO(timesValue = times, block = call)
            ResultState.Success(response)
        } catch (ex: Throwable) {
            ResultState.Error(handleError(ex))
        }
    }

    private suspend fun <T> apiCallIO(
        timesValue: Int = 4,
        initialDelay: Long = 100, // 0.1 second
        maxDelay: Long = 1000,    // 1 second
        factor: Double = 2.0,
        block: suspend () -> T
    ): T {
        var currentDelay = initialDelay
        repeat(timesValue - 1) {
            try {
                return block()
            } catch (e: IOException) {
                // you can log an error here and/or make a more finer-grained
                // analysis of the cause to see if retry is needed
            }
            delay(currentDelay)
            currentDelay = (currentDelay * factor).toLong().coerceAtMost(maxDelay)
        }
        return block() // last attempt
    }

    private fun handleError(throwable: Throwable): ErrorEntity.Error {
        return when (throwable) {
            is SocketTimeoutException, is SocketException, is InterruptedIOException -> {
                Timber.e(
                    logFormatter,
                    throwable.message.toString(),
                    NetworkConstants.NETWORK_ERROR_MESSAGES.SERVICE_UNAVAILABLE
                )

                ErrorEntity.Error(
                    NetworkConstants.NETWORK_ERROR_CODES.SERVICE_UNAVAILABLE,
                    NetworkConstants.NETWORK_ERROR_MESSAGES.SERVICE_UNAVAILABLE
                )
            }

            is MalformedJsonException -> {
                Timber.e(
                    logFormatter,
                    throwable.message.toString(),
                    NetworkConstants.NETWORK_ERROR_MESSAGES.MALFORMED_JSON
                )

                ErrorEntity.Error(
                    NetworkConstants.NETWORK_ERROR_CODES.MALFORMED_JSON,
                    NetworkConstants.NETWORK_ERROR_MESSAGES.MALFORMED_JSON
                )
            }
            is IOException -> {
                Timber.e(
                    logFormatter,
                    throwable.message.toString(),
                    NetworkConstants.NETWORK_ERROR_MESSAGES.NO_INTERNET
                )

                ErrorEntity.Error(
                    NetworkConstants.NETWORK_ERROR_CODES.NO_INTERNET,
                    NetworkConstants.NETWORK_ERROR_MESSAGES.NO_INTERNET
                )
            }

            is HttpException -> {

                Timber.e(
                    logFormatter,
                    throwable.response()?.toString() ?: throwable.message().toString(),
                    ""
                )
                val errorResponse: ErrorDto.ErrorResponse? =
                    getError(throwable.response()?.errorBody())
                if (errorResponse?.error == null) {

                    ErrorEntity.Error(
                        NetworkConstants.NETWORK_ERROR_CODES.UNEXPECTED_ERROR,
                        NetworkConstants.NETWORK_ERROR_MESSAGES.UNEXPECTED_ERROR
                    )
                } else {

                    if (errorResponse.error.code == "ValidationError" && errorResponse.error.fieldErrors?.isNotEmpty() == true) {
                        errorResponse.error.errorUserMsg =
                            errorResponse.error.fieldErrors[0].message
                    }
                    Timber.e(
                        logFormatter,
                        errorResponse.error.code,
                        errorResponse.error.errorUserMsg.toString()
                    )

                    ErrorEntity.Error(
                        errorResponse.error.code,
                        errorResponse.error.errorUserMsg.toString()
                    )
                }
            }
            else -> {
                Timber.e(
                    logFormatter, NetworkConstants.NETWORK_ERROR_MESSAGES.UNEXPECTED_ERROR,
                    NetworkConstants.NETWORK_ERROR_MESSAGES.UNEXPECTED_ERROR
                )

                ErrorEntity.Error(
                    NetworkConstants.NETWORK_ERROR_CODES.UNEXPECTED_ERROR,
                    NetworkConstants.NETWORK_ERROR_MESSAGES.UNEXPECTED_ERROR
                )
            }
        }
    }

    /**
     * This method serializes the errorbody to ErrorDto class
     */
    private fun getError(responseBody: ResponseBody?): ErrorDto.ErrorResponse? {
        return try {
            val response = GsonBuilder().create()
                .fromJson(responseBody?.string(), ErrorDto.ErrorResponse::class.java)
            Timber.e("API Error Object: %s", response?.toString())
            response
        } catch (ex: Exception) {
            ErrorDto.ErrorResponse(
                ErrorDto.Error(
                    "", NetworkConstants.NETWORK_ERROR_CODES.UNEXPECTED_ERROR,
                    NetworkConstants.NETWORK_ERROR_MESSAGES.UNEXPECTED_ERROR, null
                )
            )
        }
    }
}