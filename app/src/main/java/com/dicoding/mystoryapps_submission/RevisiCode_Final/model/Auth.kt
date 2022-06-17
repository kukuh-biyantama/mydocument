package com.dicoding.mystoryapps_submission.RevisiCode_Final.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Auth(
    val token: String, val isLogin: Boolean
) : Parcelable
