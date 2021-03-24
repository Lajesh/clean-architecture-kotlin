package com.template.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.template.data.datasource.local.entity.Branch

/****
 * DAO for bank branches
 * Author: Lajesh Dineshkumar
 * Company:
 * Created on: 3/14/21
 * Modified on: 3/14/21
 *****/
@Dao
interface BranchDao : BaseDao<Branch> {

    @Query("SELECT * FROM branches")
    suspend fun getBranches(): List<Branch>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun insert(obj: Branch): Long

    @Query("DELETE FROM branches")
    override suspend fun delete(): Int
}