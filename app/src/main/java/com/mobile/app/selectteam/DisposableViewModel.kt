package com.mobile.app.selectteam

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class DisposableViewModel : ViewModel(){
    val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
    fun addDisposable(disposable : Disposable?){
        compositeDisposable.add(disposable?:return)
    }
}