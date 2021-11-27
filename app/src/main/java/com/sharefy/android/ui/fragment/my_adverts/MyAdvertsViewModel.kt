package com.sharefy.android.ui.fragment.my_adverts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sharefy.android.base.BaseViewModel
import com.sharefy.android.model.Advert
import com.sharefy.android.repository.AdvertRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyAdvertsViewModel @Inject constructor(
    private val advertRepository: AdvertRepository,
) : BaseViewModel() {

    private val _myAdvertsList = MutableLiveData<List<Advert>>()
    val myAdvertList: LiveData<List<Advert>> = _myAdvertsList

    fun fetchMyAdverts(userId : String) {
        viewModelScope.launch {
            advertRepository.getMyAdverts(userId).run { list ->
                list?.let { _myAdvertsList.value = it }
            }
        }
    }
}