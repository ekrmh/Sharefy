package com.sharefy.android.base.validator

import android.text.TextUtils
import com.sharefy.android.R
import com.sharefy.android.base.validator.base.BaseValidator
import com.sharefy.android.base.validator.base.ValidateResult

class EmailValidator(val email: String) : BaseValidator() {
    override fun validate(): ValidateResult {
        val isValid =
            !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email)
                .matches()
        return ValidateResult(
            isValid,
            if (isValid) R.string.validation_success else R.string.validation_error_email
        )
    }
}