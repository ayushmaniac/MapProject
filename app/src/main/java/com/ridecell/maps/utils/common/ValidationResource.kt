package com.ridecell.maps.utils.common

data class ValidationResource<out T> private constructor(val status: ValidationStatus, val data: T?) {

    companion object {
        fun <T> success(data: T? = null): ValidationResource<T> = ValidationResource(ValidationStatus.SUCCESS, data)

        fun <T> error(data: T? = null): ValidationResource<T> = ValidationResource(ValidationStatus.ERROR, data)

        fun <T> loading(data: T? = null): ValidationResource<T> = ValidationResource(ValidationStatus.LOADING, data)

        fun <T> unknown(data: T? = null): ValidationResource<T> = ValidationResource(ValidationStatus.UNKNOWN, data)
    }
}


enum class ValidationStatus {
    SUCCESS,
    ERROR,
    LOADING,
    UNKNOWN
}