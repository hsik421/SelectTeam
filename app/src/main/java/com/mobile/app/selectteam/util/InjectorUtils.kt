package com.mobile.app.selectteam.util

import android.content.Context
import com.mobile.app.selectteam.data.local.AppDatabase
import com.mobile.app.selectteam.ui.main.MainViewModelFactory
import com.mobile.app.selectteam.data.UserRespository

object InjectorUtils {
    private fun getUserRepository(context: Context): UserRespository {
        return UserRespository.getInstance(
            AppDatabase.getInstance(
                context
            ).userDao()
        )
    }
    fun provideMainViewModelFactory(context : Context) : MainViewModelFactory {
        val repository = getUserRepository(context)
        return MainViewModelFactory(repository)
    }
}