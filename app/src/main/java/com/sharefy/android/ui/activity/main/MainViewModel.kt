package com.sharefy.android.ui.activity.main

import androidx.lifecycle.MutableLiveData
import com.sharefy.android.base.BaseViewModel
import com.sharefy.android.repository.ChatRepository
import com.sharefy.android.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val chatRepository: ChatRepository,
) : BaseViewModel() {


    val notificationShow = MutableLiveData<Boolean>()

    init {
        getChatsPeriodically()
    }

    fun disableNotification(){
        notificationShow.postValue(false)
    }

    private fun getChat() {
        bgScope.launch {
            chatRepository.getAllChats(appSession.user!!.docId)
                .run(showLoaderView = false) { list ->
                    val result = list?.filter {
                        it.lastUpdatedTime > appSession.lastUpdatedTime
                    }
                    val updatedTime = result?.maxByOrNull {
                        it.lastUpdatedTime
                    }
                    if (result != null) {
                        if (result.isNotEmpty()) {
                            notificationShow.postValue(true)
                            appSession.apply {
                                lastUpdatedTime = updatedTime?.lastUpdatedTime ?: 0L
                            }
                        }
                    }
                }
        }
    }

    private fun getChatsPeriodically(): Job {
        return bgScope.launch {
            while (isActive) {
                delay(5000)
                getChat()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        getChatsPeriodically().cancel()
    }
}
