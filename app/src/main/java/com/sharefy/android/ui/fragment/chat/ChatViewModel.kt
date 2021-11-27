package com.sharefy.android.ui.fragment.chat

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sharefy.android.base.BaseViewModel
import com.sharefy.android.model.Chat
import com.sharefy.android.model.ChatLobby
import com.sharefy.android.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository,
) : BaseViewModel() {

    lateinit var chatLobbyId: String

    private val _chat = MutableLiveData<ChatLobby>()
    val chat: LiveData<ChatLobby> get() = _chat

    init {

        getChatsPeriodically()
    }


    fun sendMessage(message: String) {
        bgScope.launch {
            val messages = mutableListOf<Chat>().apply {
                addAll(chat.value?.messages ?: listOf())
                add(Chat(appSession.user?.docId ?: "", message))
            }

            chatRepository.sendMessage(chatLobbyId, messages).run {
                getChat()
            }
        }
    }

    fun getChat(showLoader: Boolean = true) {
        bgScope.launch {
            chatRepository.getChat(chatLobbyId).run(showLoaderView = showLoader) {
                _chat.postValue(it)
            }
        }
    }

    private fun getChatsPeriodically(): Job {
        return bgScope.launch {
            while (isActive) {
                delay(5000)
                getChat(false)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        getChatsPeriodically().cancel()
    }
}