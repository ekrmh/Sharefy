package com.sharefy.android.base.validator

import com.sharefy.android.R
import com.sharefy.android.base.validator.base.BaseValidator
import com.sharefy.android.base.validator.base.ValidateResult
import com.sharefy.android.model.NecessaryMaterials

class NecessaryMaterialsListValidator(val input: List<NecessaryMaterials>) : BaseValidator() {
    override fun validate(): ValidateResult {
        return if (input.isNullOrEmpty())
            ValidateResult(false, R.string.validation_error_necessary_items_empty)
        else
            ValidateResult(true, R.string.validation_success)
    }

}