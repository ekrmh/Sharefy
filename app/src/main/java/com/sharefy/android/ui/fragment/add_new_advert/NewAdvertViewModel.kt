package com.sharefy.android.ui.fragment.add_new_advert

import com.sharefy.android.base.BaseViewModel
import com.sharefy.android.model.Advert
import com.sharefy.android.repository.AdvertRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NewAdvertViewModel @Inject constructor(
    private val advertRepository: AdvertRepository,
) : BaseViewModel() {


    fun addNewAdvert(newAdvert: Advert) {
        bgScope.launch {
            advertRepository.postNewAdvert(newAdvert.apply {
                contact = appSession.user?.email ?: ""
            }).run { navigateBack() }
        }
    }
}