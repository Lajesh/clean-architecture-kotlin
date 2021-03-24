package com.template.data.datasource.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.template.data.datasource.local.dao.BranchDao
import com.template.data.datasource.local.entity.Branch

/****
 * Application Database
 * Author: Lajesh Dineshkumar
 * Company:
 * Created on: 3/14/21
 * Modified on: 3/14/21
 *****/
@Database(
    entities = [Branch::class],
    version = 1,
    exportSchema = false
)
abstract class AppDb : RoomDatabase() {
    abstract fun branchDao() : BranchDao
}