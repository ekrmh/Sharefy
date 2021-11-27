package com.sharefy.android.ui.fragment.chat

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sharefy.android.base.BaseViewModel
import com.sharefy.android.model.Chat
import com.sharefy.android.model.ChatLobby
import com.sharefy.android.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository
) : BaseViewModel() {

    lateinit var chatLobbyId: String

    private val _chat = MutableLiveData<ChatLobby>()
    val chat: LiveData<ChatLobby> get() = _chat

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

    fun getChat() {
        bgScope.launch {
            chatRepository.getChat(chatLobbyId).run {
                _chat.postValue(it)
            }
        }
    }


}