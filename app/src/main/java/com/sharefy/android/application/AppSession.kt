package com.sharefy.android.application

import com.sharefy.android.model.Category
import com.sharefy.android.model.User


class AppSession {
    var categories: List<Category>? = null
    var user: User? = null
    var lastUpdatedTime: Long = 0L
    var notification : Boolean = false

}