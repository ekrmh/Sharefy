package com.sharefy.android.component.popup

import android.content.Context
import android.os.Bundle
import com.sharefy.android.R
import com.sharefy.android.base.BaseDialog
import com.sharefy.android.databinding.ViewPopupBinding

class Popup(
    context: Context,
    private val popupModel: PopupModel
) : BaseDialog<ViewPopupBinding>(context) {

    override val layoutId: Int = R.layout.view_popup

    override fun onReady(savedInstanceState: Bundle?) {
        binding.popUpModel = popupModel

        binding.btnCancel.setOnClickListener {
            popupModel.onCancelClick?.invoke()
            dismiss()
        }

        binding.btnOk.setOnClickListener {
            popupModel.onConfirmClick?.invoke()
            dismiss()
        }
    }

}