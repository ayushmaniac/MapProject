package com.ridecell.maps.data.local.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Person(

    @SerializedName("key")
    @Expose
    var personKey: String,
    @SerializedName("display_name")
    @Expose
    var name: String,

    @SerializedName("role")
    @Expose
    var role : Role

    )

data class Role(

    @SerializedName("key")
    @Expose
    var roleKey: String,

    @SerializedName("rank")
    @Expose
    var personRank: String
)