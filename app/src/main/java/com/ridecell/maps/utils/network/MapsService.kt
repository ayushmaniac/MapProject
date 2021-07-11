package com.ridecell.maps.utils.network

import androidx.lifecycle.LiveData
import com.ridecell.maps.data.local.repo.UserRepository
import com.ridecell.maps.data.remote.endpoints.Endpoint
import com.ridecell.maps.data.remote.request.LoginRequestBody
import com.ridecell.maps.data.remote.request.ResetPasswordBody
import com.ridecell.maps.data.remote.request.SignupRequestBody
import com.ridecell.maps.data.remote.response.GeneralResponse
import com.ridecell.maps.data.remote.response.PasswordRequirementsResponse
import com.ridecell.maps.data.remote.response.ProfileResponse
import com.ridecell.maps.data.remote.response.UserResponse
import com.ridecell.maps.utils.common.Resource
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface MapsService {

    @POST(Endpoint.SIGNUP)
    fun doRegistration(
        @Body
        signupRequestBody: SignupRequestBody
    ): LiveData<Resource<UserResponse>>

    @POST(Endpoint.LOGIN)
    fun doLogin(
        @Body
        requestBody: LoginRequestBody
    ): LiveData<Resource<UserResponse>>


    @GET(Endpoint.ME)
    fun getUserProfile(
        @Header("Authorization") token : String?
    ) : LiveData<Resource<ProfileResponse>>

    @GET(Endpoint.PASSWORD_REQ)
    fun getPasswordRequirements() : LiveData<Resource<PasswordRequirementsResponse>>

    @POST(Endpoint.RESET_PASSWORD)
    fun doResetPassword(@Body resetPasswordBody: ResetPasswordBody) : LiveData<Resource<GeneralResponse>>
}