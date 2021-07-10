package com.ridecell.maps.data.local.repo

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import com.ridecell.maps.data.remote.request.LoginRequestBody
import com.ridecell.maps.data.remote.request.SignupRequestBody
import com.ridecell.maps.data.remote.response.ProfileResponse
import com.ridecell.maps.data.remote.response.UserResponse
import com.ridecell.maps.utils.common.Constants
import com.ridecell.maps.utils.common.Resource
import com.ridecell.maps.utils.network.MapsService
import com.ridecell.maps.utils.network.NetworkHelper
import com.ridecell.maps.utils.network.NetworkResource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val networkService: MapsService,
    private val networkHelper: NetworkHelper,
    private val preferences: SharedPreferences
) {

    fun doSignup(requestBody: SignupRequestBody): LiveData<Resource<UserResponse>> {
        return object : NetworkResource<UserResponse>(){
            override fun createCall(): LiveData<Resource<UserResponse>> = networkService.doRegistration(requestBody)

        }.asLiveData()

    }

    fun doLogin(requestBody: LoginRequestBody): LiveData<Resource<UserResponse>> {
        return object : NetworkResource<UserResponse>(){
            override fun createCall(): LiveData<Resource<UserResponse>> = networkService.doLogin(requestBody)

        }.asLiveData()
    }

    fun saveUser(data: UserResponse?) {
        val userName : String = data?.person?.name ?: ""
        val userToken : String = data?.authToken ?: ""
        preferences.edit().putBoolean(Constants.LOGIN_STATUS, true).apply()
        preferences.edit().putString(Constants.USER_NAME, userName).apply()
        preferences.edit().putString(Constants.TOKEN, userToken).apply()
    }

    fun getUserProfile(): LiveData<Resource<ProfileResponse>> {
    return object : NetworkResource<ProfileResponse>(){
        override fun createCall(): LiveData<Resource<ProfileResponse>> = networkService.getUserProfile(getToken())
    }.asLiveData()
    }

    private fun getToken(): String? = preferences.getString(Constants.TOKEN, "")
}