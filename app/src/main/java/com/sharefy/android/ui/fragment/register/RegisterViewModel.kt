package com.sharefy.android.ui.fragment.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.sharefy.android.base.BaseViewModel
import com.sharefy.android.model.User
import com.sharefy.android.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val repository: UserRepository): BaseViewModel() {

    private val _goToMain = MutableLiveData<Boolean>()
    val goToMain: LiveData<Boolean> get() = _goToMain

    fun register(username: String, email: String, password: String) {
        val user = User(username, email, password)

        bgScope.launch {
            repository.createUserWithEmailAndPassword(user).run { result ->
                result?.user?.let {
                    saveUserData(it, user)
                }
            }
        }
    }

    private fun saveUserData(firebaseUser: FirebaseUser, user: User) {
        bgScope.launch {
            user.docId = firebaseUser.uid
            repository.saveUserData(user).run {
                _goToMain.postValue(true)
            }
        }
    }


}