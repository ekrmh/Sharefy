package com.sharefy.android.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.sharefy.android.application.AppSession
import com.sharefy.android.component.loaderview.LoaderViewModel
import com.sharefy.android.component.popup.PopupModel
import com.sharefy.android.component.popup.PopupType
import com.sharefy.android.utils.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext
import javax.inject.Inject

abstract class BaseViewModel : ViewModel() {

    @Inject
    lateinit var appSession: AppSession

    private val _navigation = MutableLiveData<Event<NavigationCommand>>()
    val navigation: LiveData<Event<NavigationCommand>> = _navigation

    private val _loaderViewState = MutableLiveData<Event<LoaderViewModel>>()
    val loaderViewState: LiveData<Event<LoaderViewModel>> = _loaderViewState

    private val _popupState = MutableLiveData<Event<PopupModel>>()
    val popupState: LiveData<Event<PopupModel>> = _popupState

    private val viewModelJob = SupervisorJob()

    protected val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    protected val bgScope = CoroutineScope(Dispatchers.Default + viewModelJob)

    protected open fun handleFailure(ex: Exception) {
        val message = ex.message ?: ex.toString()
        showPopup(
            PopupModel(
                message = message,
                popupType = PopupType.ERROR
            )
        )
    }

    suspend fun <T> Flow<T>.run(
        showLoaderView: Boolean = true,
        exception: (Exception) -> Unit = ::handleFailure,
        data: (T?) -> Unit
    ) {
        this.onStart {
            if (showLoaderView) {
                showLoaderView()
            }
        }.catch { err ->
            dismissLoaderView()
            exception.invoke(err as Exception)
        }.collect { variable: T? ->
            dismissLoaderView()
            data.invoke(variable)
        }
    }

    protected suspend fun onUIThread(block: suspend CoroutineScope.() -> Unit) {
        withContext(uiScope.coroutineContext) {
            block.invoke(this)
        }
    }

    protected suspend fun <T> onBackgroundThread(block: suspend CoroutineScope.() -> T): T {
        return withContext(bgScope.coroutineContext) {
            block.invoke(this)
        }
    }

    fun navigate(direction: NavDirections) {
        _navigation.value = Event(NavigationCommand.ToDirection(direction))
    }

    fun navigateBack() {
        _navigation.value = Event(NavigationCommand.Back)
    }

    fun showPopup(popupModel: PopupModel) {
        _popupState.postValue(Event(popupModel))
    }

    fun showLoaderView() {
        _loaderViewState.postValue(Event(LoaderViewModel(true)))
    }

    fun dismissLoaderView() {
        _loaderViewState.postValue(Event(LoaderViewModel(false)))
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}



