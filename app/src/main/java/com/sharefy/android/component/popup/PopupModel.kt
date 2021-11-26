package com.sharefy.android.component.popup

data class PopupModel(
    var title: String? = null,
    var message: String? = null,
    var cancelable: Boolean = false,
    var iconRes: Int? = null,
    val addCancelButton: Boolean = false,
    var cancelButtonText: String? = null,
    var confirmButtonText: String? = null,
    val popupType: PopupType = PopupType.WARNING,
    val onConfirmClick: (() -> Unit)? = null,
    val onCancelClick: (() -> Unit)? = null,
)


