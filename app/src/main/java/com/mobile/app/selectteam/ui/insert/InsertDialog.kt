package com.mobile.app.selectteam.ui.insert

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.DialogFragment
import com.mobile.app.selectteam.R
import kotlinx.android.synthetic.main.dialog_insert.*

class InsertDialog : DialogFragment() {
    companion object {
        fun newInstance() : InsertDialog =
            InsertDialog()
    }

    lateinit var dialogListener: DialogListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_insert,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        edit?.requestFocus()
        positiveBtn?.setOnClickListener {
            dialogListener.onPositive(edit?.text.toString())
            dismiss()
        }
        negativeBtn?.setOnClickListener { dismiss() }
    }
    interface DialogListener{
        fun onPositive(text : String)
    }
}