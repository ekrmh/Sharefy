package com.sharefy.android.ui.fragment.login

import com.sharefy.android.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(): BaseViewModel() {

    fun goToRegister(){
        navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
    }

}