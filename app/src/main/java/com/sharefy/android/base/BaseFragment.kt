package com.sharefy.android.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.sharefy.android.BR
import observeNonNull
import showPopup

abstract class BaseFragment<BINDING : ViewDataBinding, VM : BaseViewModel> : Fragment() {

    @get:LayoutRes
    protected abstract val layoutId: Int

    protected lateinit var binding: BINDING

    protected abstract val viewModel: VM

    protected val navController: NavController by lazy { findNavController() }

    protected val baseActivity by lazy { requireActivity() as BaseActivity<*, *>? }

    protected abstract fun onReady(savedInstanceState: Bundle?)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater, layoutId, container, false)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            setVariable(BR.viewModel, this@BaseFragment.viewModel)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startObservers()
        onReady(savedInstanceState)
    }

    private fun startObservers() {
        observeNavigation()
        observeLoaderViewState()
        observePopupState()
    }

    private fun observeNavigation() {
        viewModel.navigation.observeNonNull(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { navigationCommand ->
                handleNavigation(navigationCommand)
            }
        }
    }

    fun handleNavigation(navCommand: NavigationCommand) {
        when (navCommand) {
            is NavigationCommand.ToDirection -> navController.navigate(navCommand.direction)
            is NavigationCommand.Back -> navController.navigateUp()
        }
    }

    private fun observeLoaderViewState() {
        viewModel.loaderViewState.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { loaderViewModel ->
                baseActivity?.handleLoaderViewState(loaderViewModel)
            }
        }
    }

    private fun observePopupState() {
        viewModel.popupState.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { popupModel ->
                requireContext().showPopup(popupModel)
            }
        }
    }
}