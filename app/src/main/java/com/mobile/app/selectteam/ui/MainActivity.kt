package com.mobile.app.selectteam.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.mobile.app.selectteam.R
import com.mobile.app.selectteam.data.local.AppDatabase
import com.mobile.app.selectteam.data.local.UserData
import com.mobile.app.selectteam.ui.insert.InsertDialog
import com.mobile.app.selectteam.ui.main.MainFragment
import com.mobile.app.selectteam.ui.main.MainViewModel
import com.mobile.app.selectteam.ui.team.TeamFragment
import com.mobile.app.selectteam.util.InjectorUtils
import com.mobile.app.selectteam.util.replaceBackFragmentInActivity
import com.mobile.app.selectteam.util.replaceFragmentInActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),LifecycleOwner {

    lateinit var viewModel : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(savedInstanceState == null){
            AppDatabase.getInstance(this)
            replaceFragmentInActivity(
                MainFragment.newInstance(),
                R.id.contentFrame
            )
        }

        val factory = InjectorUtils.provideMainViewModelFactory(this)
        viewModel = ViewModelProviders.of(this,factory).get(MainViewModel::class.java)
        subscribeUi()
        setupListener()
    }
    private fun setupListener(){
        fab?.setOnClickListener {
            when(supportFragmentManager.findFragmentById(R.id.contentFrame)){
                is MainFragment ->{
                    replaceBackFragmentInActivity(
                        TeamFragment.newInstance(),
                        R.id.contentFrame
                    )
                }
                is TeamFragment ->{
                    InsertDialog.newInstance().apply {
                        dialogListener = object : InsertDialog.DialogListener {
                            override fun onPositive(text: String) {
                                viewModel.addUser(UserData(0, text, 0, 0,false))
                            }
                        }
                    }.show(supportFragmentManager,null)
                }
            }
        }
    }
    private fun subscribeUi(){
        viewModel.message.observe(this, Observer {
            Log.i("hsik","message = $it")
//            Snackbar.make(findViewById<ConstraintLayout>(R.id.root),it, Snackbar.LENGTH_SHORT).show()
        })
    }
}
