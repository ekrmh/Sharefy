package com.sharefy.android.ui.fragment.contribute.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.SpannableStringBuilder
import com.sharefy.android.R
import com.sharefy.android.base.BaseDialog
import com.sharefy.android.base.validator.EmptyValidator
import com.sharefy.android.databinding.ViewContributeDialogBinding
import com.sharefy.android.model.ContributeAdvert
import com.sharefy.android.model.NecessaryMaterials
import toast

class ContributeDialog(
    context: Context,
    val necessaryMaterials: NecessaryMaterials,
    val userId: String,
    val buttonCallback: (NecessaryMaterials) -> Unit,
) : BaseDialog<ViewContributeDialogBinding>(context) {

    override val layoutId: Int = R.layout.view_contribute_dialog

    @SuppressLint("SetTextI18n")
    override fun onReady(savedInstanceState: Bundle?) {
        binding.textviewSubTitle2.text =
            "Adet girin: (Gerekli adet sayisi: ${necessaryMaterials.count})"

        binding.inputEditTextTextFieldSecond.hint = SpannableStringBuilder(
            "Bagislanan malzeme adeti: ${necessaryMaterials.completedNumber}"
        )

        binding.buttonAdd.setOnClickListener {
            val input = binding.inputEditTextTextFieldSecond.text.toString()

            val inputValidator = EmptyValidator(input).validate()

            if (!inputValidator.isSuccess) {
                binding.inputLayoutTextFieldSecond.error = context.getString(inputValidator.message)
                return@setOnClickListener
            } else
                binding.inputLayoutTextFieldSecond.error = null

            val addedNumber = necessaryMaterials.completedNumber + input.toIntOrNull()!!

            if (addedNumber > necessaryMaterials.count) {
                context.toast("Lutfen gerekli malzeme adedini asmayin!")
                return@setOnClickListener
            }


            necessaryMaterials.pendingContribution.add(
                ContributeAdvert(
                    userId = this@ContributeDialog.userId,
                    count = input.toIntOrNull()!!
                )
            )

            buttonCallback.invoke(
                NecessaryMaterials(
                    count = necessaryMaterials.count,
                    information = necessaryMaterials.information,
                    completedNumber = addedNumber,
                    pendingContribution = necessaryMaterials.pendingContribution,
                    approvedContribution = necessaryMaterials.approvedContribution,
                    updatable = false
                )
            )
            dismiss()
        }
    }
}