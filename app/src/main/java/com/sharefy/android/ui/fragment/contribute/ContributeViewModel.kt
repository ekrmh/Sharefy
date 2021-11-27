package com.sharefy.android.ui.fragment.contribute

import androidx.lifecycle.viewModelScope
import com.sharefy.android.base.BaseViewModel
import com.sharefy.android.repository.AdvertRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContributeViewModel @Inject constructor(
    private val advertRepository: AdvertRepository
) : BaseViewModel() {


    fun updateAdvertData(docId: String, map: Map<String, Any>){
        viewModelScope.launch {
            advertRepository.updateAdvertMaterial(
                docId, map
            ).run {

            }
        }
    }
}