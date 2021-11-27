package com.sharefy.android.ui.fragment.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.sharefy.android.base.BaseViewModel
import com.sharefy.android.model.Advert
import com.sharefy.android.repository.AdvertRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MapViewModel @Inject constructor(
    private val advertRepository: AdvertRepository,
) : BaseViewModel() {

    private val _advertList = MutableLiveData<List<Advert>>()
    val advertList: LiveData<List<Advert>> get() = _advertList

    fun fetchAllAdvertData() {
        viewModelScope.launch {
            advertRepository.getAdverts()
                .run { list: List<Advert>? ->
                    list?.let {
                        _advertList.value = it.filter { advert ->
                            calculateProgressStatus(advert) != 100
                        }
                    }
                }
        }
    }


    fun goToAddNewAdvertFragment(latLng: LatLng) {
        navigate(MapFragmentDirections.actionMapFragmentToAddNewAdvertFragment(latLng))
    }

    fun goToContributeFragment(advert: Advert) {
        navigate(MapFragmentDirections.actionMapFragmentToContributeFragment(advert))
    }

    private fun calculateProgressStatus(advert: Advert): Int {
        val totalNumber = advert.necessaryMaterial.sumOf { it.count }
        val completedNumber =
            advert.necessaryMaterial.sumOf { it.approvedContribution.sumOf { it.count } }

        return ((completedNumber.toFloat() / totalNumber.toFloat()) * 100).toInt()
    }


}