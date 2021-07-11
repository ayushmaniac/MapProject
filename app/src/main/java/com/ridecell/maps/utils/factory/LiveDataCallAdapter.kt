package com.ridecell.maps.utils.factory

import android.util.Log
import androidx.lifecycle.LiveData
import com.ridecell.maps.utils.common.Resource
import com.ridecell.maps.utils.network.NetworkError
import org.json.JSONObject
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean


class LiveDataCallAdapter<R>(private val responseType: Type) :
    CallAdapter<R, LiveData<Resource<R>>> {

    override fun responseType(): Type {
        return responseType
    }

    override fun adapt(call: Call<R>): LiveData<Resource<R>> {
        return object : LiveData<Resource<R>>() {
            var started = AtomicBoolean(false)

            override fun onActive() {
                super.onActive()
                if (started.compareAndSet(false, true)) {
                    call.enqueue(object : Callback<R> {
                        override fun onResponse(call: Call<R>, response: Response<R>) {
                            if (response.isSuccessful) {
                                postValue(response.toResource())
                            }
                            else {
                                try {
                                    val jsonObject = JSONObject(response.errorBody()!!.string())
                                    val message =  jsonObject.getString("message")
                                    val code = jsonObject.getInt("error_code")
                                    val networkError = NetworkError()
                                    networkError.message =  message
                                    networkError.statusCode = code
                                    networkError.status = false
                                    postValue(Resource.error(message, networkError))
                                } catch (e: Exception) {
                                    Log.e("GSON PARSING ERROR ", e.printStackTrace().toString())
                                }
                            }
                        }

                        override fun onFailure(call: Call<R>, throwable: Throwable) {
                            postValue(Resource.error(throwable.message, null))
                        }
                    })
                }
            }
        }
    }
}