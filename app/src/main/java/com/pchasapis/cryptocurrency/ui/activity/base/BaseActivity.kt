package com.pchasapis.cryptocurrency.ui.activity.base

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.afollestad.materialdialogs.DialogCallback
import com.afollestad.materialdialogs.MaterialDialog
import com.pchasapis.cryptocurrency.R

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

    private var materialDialog: MaterialDialog? = null


    override fun onDestroy() {
        super.onDestroy()
        materialDialog?.dismiss()
        materialDialog = null
    }

    fun showErrorDialog(error: String = "", errorDescription: String = "", closeListener: DialogCallback) {
        materialDialog = MaterialDialog(this).show {
            title(text = error)
            message(text = errorDescription)
            positiveButton(text = getString(R.string.common_ok), click = closeListener)
        }
    }
}