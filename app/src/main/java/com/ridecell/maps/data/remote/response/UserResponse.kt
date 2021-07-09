package com.ridecell.maps.data.remote.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.ridecell.maps.data.local.model.Person

class UserResponse(

    @SerializedName("authentication_token")
    @Expose
    var authToken: String,
    @SerializedName("person")
    @Expose
    var person: Person
)