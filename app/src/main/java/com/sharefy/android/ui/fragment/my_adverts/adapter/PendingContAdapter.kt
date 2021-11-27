package com.sharefy.android.ui.fragment.my_adverts.adapter

import com.sharefy.android.R
import com.sharefy.android.base.BaseAdapter
import com.sharefy.android.databinding.ItemContributionUserBinding
import com.sharefy.android.model.ContributeAdvert

class PendingContAdapter(
    val data: List<ContributeAdvert>,
    private val pendingContClickListener: PendingContClickListener,
) : BaseAdapter<ItemContributionUserBinding, ContributeAdvert>(data) {

    override val layoutId: Int = R.layout.item_contribution_user

    override fun bind(binding: ItemContributionUserBinding, item: ContributeAdvert, position: Int) {

        binding.buttonContribute.setOnClickListener {
            pendingContClickListener.onApproveContributionClicked(item, position)
        }
        binding.apply {
            contAd = item
            textViewMaterialCount.text = item.count.toString()
            executePendingBindings()
        }
    }
}

interface PendingContClickListener {
    fun onApproveContributionClicked(item: ContributeAdvert, position: Int)
}