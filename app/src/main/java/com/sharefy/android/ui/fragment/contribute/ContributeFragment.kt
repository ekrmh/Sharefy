package com.sharefy.android.ui.fragment.contribute

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.sharefy.android.R
import com.sharefy.android.base.BaseFragment
import com.sharefy.android.databinding.FragmentContributeBinding
import com.sharefy.android.model.ContributeAdvert
import com.sharefy.android.model.NecessaryMaterials
import com.sharefy.android.ui.fragment.add_new_advert.adapter.NecessaryMaterialsAdapter
import com.sharefy.android.ui.fragment.add_new_advert.adapter.NecessaryMaterialsAdapterClickListener
import com.sharefy.android.ui.fragment.contribute.adapter.ContributionMaterialAdapter
import com.sharefy.android.ui.fragment.contribute.dialog.ContributeDialog
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ContributeFragment : BaseFragment<FragmentContributeBinding, ContributeViewModel>(),
    NecessaryMaterialsAdapterClickListener {

    override val layoutId: Int = R.layout.fragment_contribute

    override val viewModel: ContributeViewModel by viewModels()

    private val args by navArgs<ContributeFragmentArgs>()

    private val necessaryMaterialsAdapter = ContributionMaterialAdapter(listOf(), this)

    override fun onReady(savedInstanceState: Bundle?) {
        binding.apply {
            advert = args.advert
            recyclerViewMaterials.adapter = necessaryMaterialsAdapter
        }
        necessaryMaterialsAdapter.updateData(args.advert.necessaryMaterial)

    }

    override fun onMaterialItemClicked(item: NecessaryMaterials, position: Int) {
        ContributeDialog(requireContext(),
            userId = viewModel.appSession.user!!.docId,
            necessaryMaterials = item) { updatedMaterial ->

            val ind = args.advert.necessaryMaterial.indexOf(item)
            args.advert.necessaryMaterial[ind] = updatedMaterial


            viewModel.updateAdvertData(
                args.advert.docId,
                mapOf(
                    Pair("necessaryMaterial", args.advert.necessaryMaterial),
                )
            )

            necessaryMaterialsAdapter.updateData(args.advert.necessaryMaterial)
        }.show()
    }

}