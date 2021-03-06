package com.sharefy.android.ui.fragment.my_adverts

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.sharefy.android.R
import com.sharefy.android.base.BaseFragment
import com.sharefy.android.base.NavigationCommand
import com.sharefy.android.component.popup.PopupModel
import com.sharefy.android.component.popup.PopupType
import com.sharefy.android.databinding.FragmentMyAdvertsBinding
import com.sharefy.android.model.Advert
import com.sharefy.android.ui.fragment.map.MapFragmentDirections
import com.sharefy.android.ui.fragment.my_adverts.adapter.MyAdvertsAdapter
import com.sharefy.android.ui.fragment.my_adverts.adapter.MyAdvertsClickListener
import com.sharefy.android.ui.fragment.my_adverts.dialog.PendingContributionBottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import observeNonNull
import showPopup

@AndroidEntryPoint
class MyAdvertsFragment : BaseFragment<FragmentMyAdvertsBinding, MyAdvertsViewModel>(),
    MyAdvertsClickListener {

    override val layoutId: Int = R.layout.fragment_my_adverts

    override val viewModel: MyAdvertsViewModel by viewModels()

    override fun onReady(savedInstanceState: Bundle?) {

        binding.advertsAdapter = MyAdvertsAdapter(listOf(), this)
        viewModel.fetchMyAdverts(viewModel.appSession.user.docId)

    }

    override fun onItemClicked(advert: Advert, position: Int) {
        handleNavigation(NavigationCommand.ToDirection(MyAdvertsFragmentDirections.actionMyAdvertsFragmentToContributeFragment(advert)))
    }

    override fun onFinishTaskClicked(advert: Advert, position: Int) {

    }

    override fun onPendingContributionClicked(advert: Advert, position: Int) {
        val shouldShowDialog = advert.necessaryMaterial.find { it.pendingContribution.isNotEmpty() }
        if (shouldShowDialog == null){
            baseActivity?.showPopup(PopupModel(popupType = PopupType.WARNING, message = getString(R.string.warning_empty), confirmButtonText = getString(R.string.ok)))
            return
        }
        PendingContributionBottomSheetDialog(
            advert
        ) {
            viewModel.fetchMyAdverts(viewModel.appSession.user.docId)
        }.show(
            parentFragmentManager, "TAG"
        )
    }

    fun goToContributeFragment(advert: Advert) {
    }

}