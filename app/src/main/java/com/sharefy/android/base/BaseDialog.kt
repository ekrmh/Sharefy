package com.sharefy.android.base

import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseDialog<BINDING : ViewDataBinding>(context: Context) : Dialog(context) {

    @get:LayoutRes
    protected abstract val layoutId: Int

    protected lateinit var binding: BINDING

    protected abstract fun onReady(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            layoutId,
            null,
            false
        )

        setContentView(binding.root)

        window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val width =
                (Resources.getSystem().displayMetrics.widthPixels * WIDTH_PERCENTAGE).toInt()
            val height = WindowManager.LayoutParams.WRAP_CONTENT
            setLayout(width, height)
            setGravity(Gravity.CENTER)
        }

        onReady(savedInstanceState)

    }

    companion object {
        private const val WIDTH_PERCENTAGE = 0.90

    }
}