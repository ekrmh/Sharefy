package com.sharefy.android.ui.fragment.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sharefy.android.base.BaseViewModel
import com.sharefy.android.repository.UserRepository
import com.sharefy.android.utils.AppPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val userRepository: UserRepository) : BaseViewModel() {

    private val _goToBeforeLogin = MutableLiveData<Boolean>()
    val goToBeforeLogin: LiveData<Boolean> get() = _goToBeforeLogin


    fun signout(){
        bgScope.launch {
            userRepository.signOut().run {
                AppPreferences.email = ""
                AppPreferences.password = ""
                appSession.user = null
                _goToBeforeLogin.postValue(true)
            }
        }
    }
}