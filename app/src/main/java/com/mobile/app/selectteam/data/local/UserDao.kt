package com.mobile.app.selectteam.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Query("SELECT * FROM team_table ORDER BY idx")
    fun getUsers() : LiveData<List<UserData>>

    @Insert
    fun addUser(user : UserData) : Long

    @Delete
    fun removeUser(user : UserData)
}