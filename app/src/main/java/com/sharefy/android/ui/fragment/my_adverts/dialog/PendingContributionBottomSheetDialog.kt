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
    private val allList = mutableListOf<ContributeAdvert>()

    override fun init(savedInstanceState: Bundle?) {
        binding.recyclerViewPending.adapter = adapter

        advert.necessaryMaterial.map { it.pendingContribution }.forEach {
            allList.addAll(it)
        }

        adapter.updateData(allList)
    }

    override fun onApproveContributionClicked(item: ContributeAdvert, position: Int) {
        advert.necessaryMaterial.forEachIndexed { x, necessaryMaterials ->
            necessaryMaterials.pendingContribution.forEachIndexed { y, contributeAdvert ->
                if (contributeAdvert.count == item.count && contributeAdvert.userId == item.userId && contributeAdvert.id == item.id){
                    advert.necessaryMaterial[x].pendingContribution.removeAt(y)
                    advert.necessaryMaterial[x].approvedContribution.add(contributeAdvert)

                    advert.necessaryMaterial[x].updatable = true

                    viewModel.updateAdvertData(advert.docId, mapOf(Pair("necessaryMaterial", advert.necessaryMaterial)))
                    allList.removeAt(position)
                    adapter.updateData(allList)

                    if (allList.isEmpty()){
                        dismiss()
                    }
                }
            }
        }

    }
}