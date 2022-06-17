package com.dicoding.mystoryapps_submission.RevisiCode_Final.response

import com.google.gson.annotations.SerializedName

data class ApiResponse(

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)
