package com.ridecell.maps.data.remote.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GeneralResponse(

    @SerializedName("message")
    @Expose
    var message : String
)