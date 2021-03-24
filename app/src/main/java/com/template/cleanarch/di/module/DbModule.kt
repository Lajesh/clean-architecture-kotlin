package com.template.cleanarch.di.module

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.template.cleanarch.CleanApp
import com.template.data.datasource.local.database.AppDb
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

/****
 * Database Module
 * Author: Lajesh Dineshkumar
 * Company:
 * Created on: 3/14/21
 * Modified on: 3/14/21
 *****/
object DbModule {
    fun load() {
        loadKoinModules(dbModules )
    }

    val dbModules = module {

        single {
            Room.databaseBuilder(
                CleanApp.applicationContext(), AppDb::class.java, "cleanapp.db"
            ).allowMainThreadQueries()
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onDestructiveMigration(db: SupportSQLiteDatabase) {
                        // Clear data
                    }
                })
                // Clear DB while upgrade or downgrade
                .fallbackToDestructiveMigration()
                .build()
        }

        single {
            get<AppDb>().branchDao()
        }


    }
}