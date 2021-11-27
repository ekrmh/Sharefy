package com.sharefy.android.ui.fragment.chat

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.sharefy.android.R
import com.sharefy.android.base.BaseFragment
import com.sharefy.android.base.NavigationCommand
import com.sharefy.android.databinding.FragmentChatBinding
import com.sharefy.android.databinding.FragmentChatLobbyBinding
import com.sharefy.android.ui.fragment.add_new_advert.NewAdvertViewModel
import com.sharefy.android.ui.fragment.chat.adapter.ChatAdapter
import com.sharefy.android.ui.fragment.chat_lobby.adapter.ChatLobbyAdapter
import com.sharefy.android.ui.fragment.contribute.ContributeFragmentArgs
import dagger.hilt.android.AndroidEntryPoint
import observeNonNull
import toast

@AndroidEntryPoint
class ChatFragment : BaseFragment<FragmentChatBinding, ChatViewModel>() {

    override val layoutId = R.layout.fragment_chat
    override val viewModel: ChatViewModel by viewModels()
    private val args by navArgs<ChatFragmentArgs>()

    override fun onReady(savedInstanceState: Bundle?) {
        viewModel.chatLobbyId = args.chatLobbyId
        viewModel.getChat()

        viewModel.chat.observeNonNull(this){
            binding.recyclerView.adapter = ChatAdapter(viewModel.appSession.user ?: return@observeNonNull, it.messages)
        }

        binding.buttonSend.setOnClickListener {
            val text = binding.textField.editText?.text

            if (text.isNullOrEmpty().not()){
                viewModel.sendMessage(text.toString())
                binding.textField.editText?.setText("")
            }
        }
    }
}