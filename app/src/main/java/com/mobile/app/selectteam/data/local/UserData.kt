package com.mobile.app.selectteam.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "team_table")
data class UserData(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idx")
    val idx : Int,
    @ColumnInfo(name = "name")
    val name : String,
    @ColumnInfo(name = "level")
    val level : Int,
    val num : Int,
    var select : Boolean
)