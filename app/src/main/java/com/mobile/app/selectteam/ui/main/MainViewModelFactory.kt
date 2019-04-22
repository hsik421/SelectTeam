package com.mobile.app.selectteam.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mobile.app.selectteam.data.UserRespository

class MainViewModelFactory(private val repository : UserRespository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        with(modelClass){
            return when{
                isAssignableFrom(MainViewModel::class.java) -> MainViewModel(
                    repository
                ) as T
                else-> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        }
    }
}