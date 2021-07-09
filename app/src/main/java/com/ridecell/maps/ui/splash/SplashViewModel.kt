package com.ridecell.maps.ui.splash

import com.ridecell.maps.data.ApplicationRepository
import com.ridecell.maps.ui.base.BaseViewModel

class SplashViewModel(private val applicationRepository: ApplicationRepository) : BaseViewModel() {

    fun checkIfUserIsLoggedIn() = applicationRepository.getUserLoginStatus()


}