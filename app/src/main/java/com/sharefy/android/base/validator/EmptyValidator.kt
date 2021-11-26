package com.sharefy.android.base.validator

import com.sharefy.android.R
import com.sharefy.android.base.validator.base.BaseValidator
import com.sharefy.android.base.validator.base.ValidateResult


class EmptyValidator(val text: String): BaseValidator() {
    override fun validate(): ValidateResult {
        val isValid = text.isNotEmpty()
        return ValidateResult(isValid, if (isValid) R.string.validation_success else R.string.validation_error_empty)

    }
}