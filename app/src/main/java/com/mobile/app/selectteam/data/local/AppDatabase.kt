package com.mobile.app.selectteam.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [(UserData::class)],version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun userDao() : UserDao
    companion object {
        @Volatile private var instance : AppDatabase? = null
        fun getInstance(context : Context) : AppDatabase = instance
            ?: synchronized(this){
            instance ?: Room.databaseBuilder(context.applicationContext,
                AppDatabase::class.java,"team_db").build()
        }
    }
}