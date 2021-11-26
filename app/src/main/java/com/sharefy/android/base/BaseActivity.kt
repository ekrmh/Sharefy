package com.sharefy.android.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.sharefy.android.BR
import com.sharefy.android.component.loaderview.LoaderView
import com.sharefy.android.component.loaderview.LoaderViewModel
import observeNonNull
import showPopup

abstract class BaseActivity<BINDING : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity() {

    @get:LayoutRes
    protected abstract val layoutId: Int

    protected lateinit var binding: BINDING

    protected abstract val viewModel: VM

    protected abstract fun onReady(savedInstanceState: Bundle?)

    private val loaderView: LoaderView by lazy { LoaderView(this) }

    abstract val fragmentContainerId: Int

    protected val navController: NavController by lazy {
        findNavController(fragmentContainerId)
    }

    lateinit var appBarConfiguration: AppBarConfiguration

    protected fun withToolbar(container: FragmentContainerView, toolbar: androidx.appcompat.widget.Toolbar) {
        container.post {
            if (::appBarConfiguration.isInitialized.not())
                appBarConfiguration = AppBarConfiguration(navController.graph)

            toolbar.setupWithNavController(navController, appBarConfiguration)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
                || super.onSupportNavigateUp()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.apply {
            lifecycleOwner = this@BaseActivity
            setVariable(BR.viewModel, this@BaseActivity.viewModel)
        }

        startObservers()
        onReady(savedInstanceState)
    }

    private fun startObservers() {
        observeLoaderViewState()
        observePopupState()
    }

    private fun observeLoaderViewState() {
        viewModel.loaderViewState.observeNonNull(this) {
            it.getContentIfNotHandled()?.let { model -> handleLoaderViewState(model) }
        }
    }

    fun handleLoaderViewState(loaderViewModel: LoaderViewModel) {
        if (loaderViewModel.shouldShowProgress)
            showLoaderView()
        else
            dismissLoaderView()
    }

    fun showLoaderView() {
        loaderView.showProgress()
    }

    fun dismissLoaderView() {
        loaderView.dismissProgress()
    }

    private fun observePopupState() {
        viewModel.popupState.observeNonNull(this) {
            it.getContentIfNotHandled()?.let { popupModel ->
                showPopup(popupModel)
            }
        }
    }
}