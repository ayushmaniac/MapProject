package com.ridecell.maps.data.remote.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class PasswordRequirementsResponse (
    @SerializedName("require_lowercase")
    @Expose
    var requireLowercase: Boolean? = null,

    @SerializedName("last_x_passwords")
    @Expose
    var lastXPasswords: Int? = null,

    @SerializedName("require_number")
    @Expose
    var requireNumber: Boolean? = null,

    @SerializedName("require_special")
    @Expose
    var requireSpecial: Boolean? = null,

    @SerializedName("max_chars")
    @Expose
    var maxChars: Int? = null,

    @SerializedName("require_uppercase")
    @Expose
    var requireUppercase: Boolean? = null,

    @SerializedName("min_chars")
    @Expose
    var minChars: Int? = null
)