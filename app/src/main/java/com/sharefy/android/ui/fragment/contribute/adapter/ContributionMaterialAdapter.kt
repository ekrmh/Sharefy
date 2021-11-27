package com.sharefy.android.ui.fragment.contribute.adapter

import android.view.View
import com.sharefy.android.R
import com.sharefy.android.base.BaseAdapter
import com.sharefy.android.databinding.ItemAdvertContributeBinding
import com.sharefy.android.databinding.ItemNecessaryMaterialsBinding
import com.sharefy.android.model.NecessaryMaterials
import com.sharefy.android.model.User
import com.sharefy.android.ui.fragment.add_new_advert.adapter.NecessaryMaterialsAdapterClickListener

class ContributionMaterialAdapter(
""    private val data: List<NecessaryMaterials>,
    private val necessaryMaterialsAdapterClickListener: NecessaryMaterialsAdapterClickListener,
) : BaseAdapter<ItemAdvertContributeBinding, NecessaryMaterials>(data) {

    override val layoutId: Int = R.layout.item_advert_contribute

    override fun bind(
        binding: ItemAdvertContributeBinding,
        item: NecessaryMaterials,
        position: Int,
    ) {
        binding.textViewMaterialCount.text = item.count.toString()
        binding.item = item
        binding.buttonContribute.setOnClickListener {
            necessaryMaterialsAdapterClickListener.onMaterialItemClicked(
                item, position
            )
        }
    }
}