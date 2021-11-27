package com.sharefy.android.ui.fragment.chat_lobby

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.sharefy.android.R
import com.sharefy.android.base.BaseFragment
import com.sharefy.android.base.NavigationCommand
import com.sharefy.android.databinding.FragmentChatLobbyBinding
import com.sharefy.android.ui.fragment.add_new_advert.NewAdvertViewModel
import com.sharefy.android.ui.fragment.chat_lobby.adapter.ChatLobbyAdapter
import dagger.hilt.android.AndroidEntryPoint
import observeNonNull
import toast

@AndroidEntryPoint
class ChatLobbyFragment : BaseFragment<FragmentChatLobbyBinding, ChatLobbyViewModel>() {

    override val layoutId = R.layout.fragment_chat_lobby
    override val viewModel: ChatLobbyViewModel by viewModels()

    override fun onReady(savedInstanceState: Bundle?) {
        viewModel.getAllChats()

        viewModel.chatList.observeNonNull(this){
            binding.recyclerView.adapter = ChatLobbyAdapter(viewModel.appSession.user!!, it){
                handleNavigation(NavigationCommand.ToDirection(ChatLobbyFragmentDirections.actionChatLobbyFragmentToChatFragment(it.docId)))
            }
        }
    }
}