package com.mobile.app.selectteam.util

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

fun AppCompatActivity.replaceFragmentInActivity(fragment: Fragment, frameId: Int) {
    supportFragmentManager.commit {
        replace(frameId, fragment)
    }
}

fun AppCompatActivity.replaceBackFragmentInActivity(fragment: Fragment, frameId: Int) {
    supportFragmentManager.commit {
        addToBackStack(null)
        replace(frameId, fragment)
    }
}
private inline fun FragmentManager.commit(action: FragmentTransaction.() -> Unit) {
    beginTransaction().apply {
        action()
    }.commit()
}