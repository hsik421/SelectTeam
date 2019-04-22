package com.mobile.app.selectteam.data

import com.mobile.app.selectteam.data.local.UserDao
import com.mobile.app.selectteam.data.local.UserData
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers
class UserRespository constructor(private val userDao : UserDao){
    fun getUsers() = userDao.getUsers()

    fun addUser(user : UserData) = Single.fromCallable { userDao.addUser(user) }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    fun removeUser(user : UserData) = Single.fromCallable { userDao.removeUser(user) }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    companion object {
        @Volatile private var instance : UserRespository? = null

        fun getInstance(userDao : UserDao) = instance ?: synchronized(this){
            instance
                ?: UserRespository(userDao).also { instance = it }
        }
    }
}