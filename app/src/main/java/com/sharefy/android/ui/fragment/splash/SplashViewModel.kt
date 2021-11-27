package com.sharefy.android.ui.fragment.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sharefy.android.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : BaseViewModel() {

    fun getUserData() {
        viewModelScope.launch {
            delay(2000)
            navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
        }
    }
}