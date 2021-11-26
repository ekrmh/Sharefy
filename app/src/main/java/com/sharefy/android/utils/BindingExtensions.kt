package com.sharefy.android.utils

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.sharefy.android.base.BaseAdapter
import com.sharefy.android.base.ListAdapterItem

@BindingAdapter("lottieFile")
fun setLottieFile(view: LottieAnimationView, resource: String) {
    view.setAnimation(resource)
}

@BindingAdapter("hideIfNull")
fun setVisible(view: View, obj: Any?) {
    view.visibility = if (obj == null) {
        View.GONE
    } else if (obj is String && obj.isBlank()) {
        View.GONE
    } else {
        View.VISIBLE
    }
}

@BindingAdapter("visibleIf")
fun visibleIf(view: View, shouldVisible: Boolean) {
    view.visibility = if (shouldVisible) {
        View.VISIBLE
    } else {
        View.GONE
    }
}


@BindingAdapter("submitData")
fun listAdapterSubmitList(recyclerView: RecyclerView, list: List<ListAdapterItem>?) {
    val adapter = recyclerView.adapter as BaseAdapter<ViewDataBinding, ListAdapterItem>?
    adapter?.submitList(list)
}

@BindingAdapter("adapter")
fun listAdapterSetAdapter(
    recyclerView: RecyclerView,
    adapter: BaseAdapter<ViewDataBinding, ListAdapterItem>?,
) {
    adapter?.let {
        recyclerView.adapter = it
    }
}