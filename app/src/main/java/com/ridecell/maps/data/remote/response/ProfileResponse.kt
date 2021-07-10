package com.ridecell.maps.data.remote.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.ridecell.maps.data.local.model.Role

data class ProfileResponse(

    @SerializedName("created_at")
    @Expose
    var createdAt: String,
    @SerializedName("display_name")
    @Expose
    var name: String,
    @SerializedName("email")
    @Expose
    var email: String,
    @SerializedName("key")
    @Expose
    var key: String,
    @SerializedName("role")
    @Expose
    var role: Role,
    @SerializedName("updated_at")
    @Expose
    var updatedAt: String
)