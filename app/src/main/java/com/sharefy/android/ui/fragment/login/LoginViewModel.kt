package com.sharefy.android.ui.fragment.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sharefy.android.application.AppSession
import com.sharefy.android.base.BaseViewModel
import com.sharefy.android.repository.CategoryRepository
import com.sharefy.android.repository.UserRepository
import com.sharefy.android.utils.AppPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val userRepository: UserRepository, private val categoryRepository: CategoryRepository): BaseViewModel() {

    private val _goToMain = MutableLiveData<Boolean>()
    val goToMain: LiveData<Boolean> get() = _goToMain


    fun goToRegister(){
        navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
    }

    fun login(email: String, password: String, rememberMe: Boolean) {
        bgScope.launch {
            userRepository.signInWithEmailAndPassword(email, password).run {
                getUserData(email)
                if (rememberMe){
                    saveUserToLocal(email, password)
                }
            }
        }
    }

    fun getCategories(){
        bgScope.launch {
            categoryRepository.getCategories().run(showLoaderView = false) {
                appSession.categories = it
            }
        }
    }

    private fun saveUserToLocal(email: String, password: String) {
        AppPreferences.email = email
        AppPreferences.password = password
    }

    private fun getUserData(email: String) {
        bgScope.launch {
            userRepository.getUserData(email).run {
                appSession.user = it
                _goToMain.postValue(true)
            }
        }
    }

}