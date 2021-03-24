package com.template.data.datasource.local.dao

import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/****
 * All the DAO should be extended from this base class.
 * Author: Lajesh Dineshkumar
 * Company:
 * Created on: 3/14/21
 * Modified on: 3/14/21
 *****/
interface BaseDao<T> {

    /**
     * Insert an object in the database.
     *
     * @param obj the object to be inserted.
     */
    @Insert
    suspend fun insert(obj: T): Long

    /**
     * Update an object from the database.
     *
     * @param obj the object to be updated
     */
    @Update
    suspend fun update(obj: T): Void

    /**
     * Delete an object from the database
     *
     * @param obj the object to be deleted
     */
    suspend fun delete(): Int

}