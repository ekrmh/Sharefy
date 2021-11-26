package com.sharefy.android.base.validator

import com.sharefy.android.R
import com.sharefy.android.base.validator.base.BaseValidator
import com.sharefy.android.base.validator.base.ValidateResult

class NecessaryMaterialsValidator(val input: String) : BaseValidator() {
    override fun validate(): ValidateResult {
        val strArr = input.split(" ")

        return when {
            strArr[0].toIntOrNull() == null -> ValidateResult(false, R.string.validation_error_necessary_items_count_missing)
            strArr.size < 2 -> ValidateResult(false, R.string.validation_error_necessary_items_count_missing)
            else -> ValidateResult(true, R.string.validation_success)
        }

    }
}