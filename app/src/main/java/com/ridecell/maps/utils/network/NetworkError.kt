package com.ridecell.maps.utils.network

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class NetworkError(

    var status: Boolean = false,
    @Expose
    @SerializedName("status_code")
    var statusCode: Int = -1,
    @Expose
    @SerializedName("message")
    var message: String = "Something went wrong"
)