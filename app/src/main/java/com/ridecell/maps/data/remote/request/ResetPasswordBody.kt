package com.ridecell.maps.data.remote.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ResetPasswordBody(

    @SerializedName("email")
    @Expose
    var email : String
)