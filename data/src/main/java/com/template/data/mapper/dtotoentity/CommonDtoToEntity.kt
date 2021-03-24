package com.template.data.mapper.dtotoentity

import com.template.data.datasource.remote.dto.CommonDto
import com.template.domain.entity.common.CommonEntity

/**
 * Keep all the DTO to Entity Mapping here
 * Created by Lajesh Dineshkumar on 2020-03-09.
 * Company: 
 * Email: lajeshds2007@gmail.com
 */

fun <T> CommonDto.CommonResponse<T>.map() = CommonEntity.CommonResponse(
    response = response,
    data = data
)

fun CommonDto.ServerDate.map() = CommonEntity.ServerDate(
    dateTime = dateTime
)

fun CommonDto.Location.map() = CommonEntity.Location(latitude, longitude)
