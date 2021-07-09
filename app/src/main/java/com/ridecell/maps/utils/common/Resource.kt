package com.ridecell.maps.utils.common

import androidx.annotation.NonNull
import com.ridecell.maps.utils.network.NetworkError

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
</T> */
data class Resource<ResultType>(
    var status: Status,
    var data: ResultType? = null,
    var networkError: NetworkError? = null
) {



    companion object {
        /**
         * Creates [Resource] object with `SUCCESS` status and [data].
         * Returning object of Resource(Status.SUCCESS, data, null)
         * last value is null so passing it optionally
         *
         */
        fun <ResultType> success(data: ResultType): Resource<ResultType> =
            Resource(Status.SUCCESS, data, null)

        /**
         * Creates [Resource] object with `LOADING` status to notify
         * the UI to showing loading.
         * Returning object of Resource(Status.SUCCESS, null, null)
         * last two values are null so passing them optionally
         */
        fun <ResultType> loading(): Resource<ResultType> = Resource(Status.LOADING, null)

        /**
         * Creates [Resource] object with `ERROR` status and [message].
         * Returning object of Resource(Status.ERROR, errorMessage = message)
         */
        fun <ResultType> error(message: String?, networkError: NetworkError?): Resource<ResultType> =
            Resource(Status.ERROR,networkError = networkError)

    }
}