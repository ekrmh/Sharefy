package com.sharefy.android.ui.fragment.my_adverts.dialog

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.sharefy.android.R
import com.sharefy.android.base.BaseBottomSheetDialogFragment
import com.sharefy.android.databinding.BottomSheetPendingBinding
import com.sharefy.android.model.Advert
import com.sharefy.android.model.ContributeAdvert
import com.sharefy.android.ui.fragment.my_adverts.MyAdvertsViewModel
import com.sharefy.android.ui.fragment.my_adverts.adapter.PendingContAdapter
import com.sharefy.android.ui.fragment.my_adverts.adapter.PendingContClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PendingContributionBottomSheetDialog(
    private val advert: Advert,
    private val ind: Int,
) : BaseBottomSheetDialogFragment<BottomSheetPendingBinding, MyAdvertsViewModel>(),
    PendingContClickListener {

    override val layoutId: Int = R.layout.bottom_sheet_pending

    override val viewModel: MyAdvertsViewModel by viewModels()

    private val adapter = PendingContAdapter(listOf(), this)

    override fun init(savedInstanceState: Bundle?) {
        binding.recyclerViewPending.adapter = adapter

        adapter.updateData(advert.necessaryMaterial[ind].pendingContribution)
    }

    override fun onApproveContributionClicked(item: ContributeAdvert, position: Int) {
        advert.necessaryMaterial[ind].pendingContribution.removeAt(position)
        advert.necessaryMaterial[ind].approvedContribution.add(item)

        advert.necessaryMaterial[ind].updatable = true

        viewModel.updateAdvertData(
            advert.docId, mapOf(
                Pair("necessaryMaterial", advert.necessaryMaterial)
            )
        )
        adapter.updateData(advert.necessaryMaterial[ind].pendingContribution)
    }
}