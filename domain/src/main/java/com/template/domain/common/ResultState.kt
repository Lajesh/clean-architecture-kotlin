package com.template.domain.common

import com.template.domain.entity.common.ErrorEntity

/**
 * A wrapper for database and network states.
 * Created by Lajesh Dineshkumar on 2020-03-09.
 * Company: 
 * Email: lajeshds2007@gmail.com
 */
sealed class ResultState<T> {

    /**
     * A state that shows the [data] is the last known update.
     */
    data class Success<T>(val data: T) : ResultState<T>()

    /**
     * A state to show an error
     */
    data class Error<T>(val error: ErrorEntity.Error?) : ResultState<T>()
}
