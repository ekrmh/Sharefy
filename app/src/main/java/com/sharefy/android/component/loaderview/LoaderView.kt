package com.sharefy.android.component.loaderview

import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.Window
import com.sharefy.android.databinding.ViewLoaderBinding

class LoaderView(private val context: Context) {

    private val dialog by lazy {
        Dialog(context, android.R.style.Theme_Black).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window?.apply {
                setBackgroundDrawableResource(android.R.color.transparent)
                val wh =
                    (Resources.getSystem().displayMetrics.widthPixels * WIDTH_PERCENTAGE).toInt()
                setLayout(wh, wh)
            }

            setContentView(view.root)
            setCancelable(false)
        }
    }

    private val view = ViewLoaderBinding.inflate(LayoutInflater.from(context))

    val isShowing get() = dialog.isShowing

    fun showProgress() {
        if (isShowing.not()) {
            dialog.show()
        }
    }

    fun dismissProgress() {
        if (isShowing) {
            dialog.dismiss()
        }
    }

    companion object {
        private const val WIDTH_PERCENTAGE = 0.15
    }
}

data class LoaderViewModel(val shouldShowProgress: Boolean)
