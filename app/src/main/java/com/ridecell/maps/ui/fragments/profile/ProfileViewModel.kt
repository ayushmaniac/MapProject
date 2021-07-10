package com.ridecell.maps.ui.fragments.profile

import com.ridecell.maps.data.local.repo.UserRepository
import com.ridecell.maps.ui.base.BaseViewModel

class ProfileViewModel(private val userRepository: UserRepository) : BaseViewModel() {

    fun getUser() = userRepository.getUserProfile()
}