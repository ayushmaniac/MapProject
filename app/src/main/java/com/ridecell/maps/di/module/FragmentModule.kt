package com.ridecell.maps.di.module

import androidx.lifecycle.ViewModelProvider
import com.ridecell.maps.data.local.repo.ApplicationRepository
import com.ridecell.maps.data.local.repo.UserRepository
import com.ridecell.maps.ui.base.BaseFragment
import com.ridecell.maps.ui.fragments.home.HomeViewModel
import com.ridecell.maps.ui.fragments.profile.ProfileViewModel
import com.ridecell.maps.ui.login.fragments.login.LoginViewModel
import com.ridecell.maps.ui.login.fragments.registration.RegistrationViewModel
import com.ridecell.maps.utils.viewmodel.ViewModelProviderFactory
import dagger.Module
import dagger.Provides

@Module
class FragmentModule(private val fragment: BaseFragment<*>) {

    @Provides
    fun provideLoginFragment(
        userRepository: UserRepository
    ): LoginViewModel = ViewModelProvider(
        fragment, ViewModelProviderFactory(LoginViewModel::class) {
            LoginViewModel(userRepository)
        }).get(LoginViewModel::class.java)

    @Provides
    fun provideRegistrationFragment(
        userRepository: UserRepository
    ): RegistrationViewModel = ViewModelProvider(
        fragment, ViewModelProviderFactory(RegistrationViewModel::class) {
            RegistrationViewModel(userRepository)
        }).get(RegistrationViewModel::class.java)

    @Provides
    fun provideHomeFragment(
        applicationRepository: ApplicationRepository
    ): HomeViewModel = ViewModelProvider(
        fragment, ViewModelProviderFactory(HomeViewModel::class) {
            HomeViewModel()
        }).get(HomeViewModel::class.java)

    @Provides
    fun provideProfileFragment(
        userRepository: UserRepository
    ): ProfileViewModel = ViewModelProvider(
        fragment, ViewModelProviderFactory(ProfileViewModel::class) {
            ProfileViewModel(userRepository)
        }).get(ProfileViewModel::class.java)
}