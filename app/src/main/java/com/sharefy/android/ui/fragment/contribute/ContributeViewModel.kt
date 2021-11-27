package com.sharefy.android.ui.fragment.contribute

import androidx.lifecycle.viewModelScope
import com.sharefy.android.base.BaseViewModel
import com.sharefy.android.model.Advert
import com.sharefy.android.model.Chat
import com.sharefy.android.model.ChatLobby
import com.sharefy.android.repository.AdvertRepository
import com.sharefy.android.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContributeViewModel @Inject constructor(
    private val advertRepository: AdvertRepository,
    private val chatRepository: ChatRepository
) : BaseViewModel() {


    fun updateAdvertData(docId: String, map: Map<String, Any>){
        viewModelScope.launch {
            advertRepository.updateAdvertMaterial(
                docId, map
            ).run {

            }
        }
    }

    fun openMessageScreen(advert: Advert) {
        val user = appSession.user ?: return
        val lobby = ChatLobby(
            personIds = listOf(user.docId, advert.userId),
            personEmails = listOf(user.email, advert.contact),
            messages = mutableListOf()
        )
        viewModelScope.launch {
            chatRepository.createMessageRoom(lobby).run {
                navigate(ContributeFragmentDirections.actionContributeFragmentToChatLobbyFragment())
            }
        }
    }
}