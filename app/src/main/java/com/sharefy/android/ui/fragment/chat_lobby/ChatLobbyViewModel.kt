package com.sharefy.android.ui.fragment.chat_lobby

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sharefy.android.base.BaseViewModel
import com.sharefy.android.model.Advert
import com.sharefy.android.model.ChatLobby
import com.sharefy.android.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatLobbyViewModel @Inject constructor(
    private val chatRepository: ChatRepository
) : BaseViewModel() {

    private val _chatList = MutableLiveData<MutableList<ChatLobby>>()
    val chatList: LiveData<MutableList<ChatLobby>> get() = _chatList

    fun getAllChats() {
        bgScope.launch {
            chatRepository.getAllChats(appSession.user.docId).run { list ->
                _chatList.postValue(list)
            }
        }
    }
}