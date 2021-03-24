package com.template.data.datasource.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/****
 * Branch model
 * Author: Lajesh Dineshkumar
 * Company:
 * Created on: 3/14/21
 * Modified on: 3/14/21
 *****/
@Entity(tableName = "branches")
data class Branch(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val branchName: String? = "",

    val branchAddress: String? = ""
)
