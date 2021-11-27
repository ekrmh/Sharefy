package com.sharefy.android.base

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import com.sharefy.android.BR
import com.sharefy.android.R
import showPopup

abstract class BaseBottomSheetDialogFragment<BINDING : ViewDataBinding, VM : BaseViewModel> :
    BottomSheetDialogFragment() {

    @get:LayoutRes
    abstract val layoutId: Int

    abstract val viewModel: VM

    protected lateinit var binding: BINDING

    abstract fun init(savedInstanceState: Bundle?)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            layoutId,
            container,
            false
        )
        binding.lifecycleOwner = viewLifecycleOwner
        binding.setVariable(BR.viewModel, this.viewModel)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLoaderViewState()
        observePopupState()
        init(savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)

        (dialog as BottomSheetDialog).behavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    //In the EXPANDED STATE apply a new MaterialShapeDrawable with rounded cornes
                    val newMaterialShapeDrawable = createMaterialShapeDrawable(bottomSheet)
                    ViewCompat.setBackground(bottomSheet, newMaterialShapeDrawable)
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })

        return dialog
    }

    private fun createMaterialShapeDrawable(bottomSheet: View): MaterialShapeDrawable {
        val shapeAppearanceModel =
            ShapeAppearanceModel.builder(context, 0, R.style.CustomShapeAppearanceBottomSheetDialog)
                .build()
        val currentMaterialShapeDrawable = bottomSheet.background as MaterialShapeDrawable
        val newMaterialShapeDrawable = MaterialShapeDrawable(shapeAppearanceModel)
        newMaterialShapeDrawable.apply {
            initializeElevationOverlay(context);
            fillColor = currentMaterialShapeDrawable.fillColor;
            tintList = currentMaterialShapeDrawable.tintList;
            elevation = currentMaterialShapeDrawable.elevation;
            strokeWidth = currentMaterialShapeDrawable.strokeWidth;
            strokeColor = currentMaterialShapeDrawable.strokeColor;
        }

        return newMaterialShapeDrawable
    }

    private fun observeLoaderViewState() {
        viewModel.loaderViewState.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { loaderViewModel ->
                (parentFragment?.activity as BaseActivity<*, *>?)?.handleLoaderViewState(
                    loaderViewModel)
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