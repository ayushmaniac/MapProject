package com.ridecell.maps.data.local.repo

import android.content.SharedPreferences
import com.ridecell.maps.utils.common.Constants
import com.ridecell.maps.utils.network.MapsService
import com.ridecell.maps.utils.network.NetworkHelper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApplicationRepository @Inject constructor(
    private val networkService: MapsService,
    private val networkHelper: NetworkHelper,
    private val preferences: SharedPreferences
){
    fun getUserLoginStatus(): Boolean = preferences.getBoolean(Constants.LOGIN_STATUS, false)

}