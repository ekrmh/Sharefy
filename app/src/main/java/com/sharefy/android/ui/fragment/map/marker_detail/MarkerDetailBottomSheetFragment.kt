package com.sharefy.android.ui.fragment.map.marker_detail

import android.accessibilityservice.AccessibilityButtonController
import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.viewModels
import com.sharefy.android.R
import com.sharefy.android.base.BaseBottomSheetDialogFragment
import com.sharefy.android.databinding.BottomSheetAdvertDetailBinding
import com.sharefy.android.model.Advert
import com.sharefy.android.ui.fragment.map.MapViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MarkerDetailBottomSheetFragment(
    private val advertInf: Advert,
    private val closeCallback : () -> Unit,
    private val buttonCallback : (Advert) -> Unit
) : BaseBottomSheetDialogFragment<BottomSheetAdvertDetailBinding, MapViewModel>() {

    override val layoutId: Int = R.layout.bottom_sheet_advert_detail

    override val viewModel: MapViewModel by viewModels()

    override fun init(savedInstanceState: Bundle?) {
        binding.advert = advertInf
        initProgressStatus()

        binding.buttonGoToDetail.setOnClickListener {
            buttonCallback.invoke(advertInf)
            dismiss()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initProgressStatus() {
        var totalNumber =  advertInf.necessaryMaterial.sumOf { it.count }
        var completedNumber =  advertInf.necessaryMaterial.sumOf { it.approvedContribution.sumOf { it.count } }

        val percentage = ((completedNumber.toFloat() / totalNumber.toFloat())*100).toInt()
        binding.progressAdvert.progress = percentage
        binding.textViewProgressNumber.text = "%$percentage"
    }

    override fun onCancel(dialog: DialogInterface) {
        closeCallback.invoke()
        super.onCancel(dialog)
    }
}